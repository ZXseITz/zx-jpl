package ch.zxseitz.j3de;

import ch.zxseitz.j3de.window.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL45.*
import org.lwjgl.system.MemoryStack.stackPop
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.NULL
import kotlin.collections.ArrayList

abstract class Application {
    companion object {
        fun <T : Application> launch(args: Array<String>, tClass: Class<T>) {
            try {
                // todo verify
                val app = tClass.getConstructor().newInstance() as Application

                app.setUp()
                app.run()
                app.terminate()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun getClassResource(path: String): Path {
            try {
                return Paths.get(
                    Objects.requireNonNull(Application::class.java.classLoader.getResource(path)).toURI()
                )
            } catch (e: java.lang.Exception) {
                throw ResourceNotFoundException(path)
            }
        }
    }

    private var window: Long = 0

    // todo custom keymap
    private val keyDownListeners: MutableList<IWindowKeyListener> = ArrayList()
    private val sizeChangedListeners: MutableList<IWindowSizeListener> = ArrayList()

    /**
     * Configures the application before init the game.
     *
     * @return application options.
     */
    protected open fun applicationInit(): ApplicationOptions {
        return ApplicationOptions("J3de Application", 450, 450)
    }

    /**
     * Configures the game: Setup shader programs and scene.
     *
     * @throws J3deException
     */
    protected abstract fun initGame()

    /**
     * Updates scene.
     *
     * @param delta elapsed time since the last frame was rendered.
     * @throws J3deException
     */
    protected abstract fun updateGame(delta: Double)

    /**
     * Set a flag to close this application.
     * This method does not interrupt the current frame
     */
    protected fun close() {
        glfwSetWindowShouldClose(window, true)
    }

    fun addKeyListener(keyListener: IWindowKeyListener) {
        keyDownListeners.add(keyListener)
    }

    fun removeKeyListener(keyListener: IWindowKeyListener) {
        keyDownListeners.remove(keyListener)
    }

    fun addSizeChangedListener(sizeListener: IWindowSizeListener) {
        sizeChangedListeners.add(sizeListener)
    }

    fun removeSizeChangedListener(sizeListener: IWindowSizeListener) {
        sizeChangedListeners.remove(sizeListener)
    }

    private fun setUp() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set()

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw IllegalStateException("Unable to initialize GLFW")

        // Configure GLFW
        glfwDefaultWindowHints() // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE) // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE) // the window will be resizable

        val applicationOptions = applicationInit()

        // Create the window
        window = glfwCreateWindow(
            applicationOptions.width, applicationOptions.height,
            applicationOptions.title, NULL, NULL
        )
        if (window == NULL) {
            throw WindowException("Cannot create window ${applicationOptions.title}", 0L)
        }

        // Get the thread stack and push a new frame
        stackPush().use { stack1 ->
            val pWidth = stack1.mallocInt(1) // int*
            val pHeight = stack1.mallocInt(1) // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight)

            // Get the resolution of the primary monitor
            val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())

            // Center the window
            if (vidmode != null) {
                glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
                )
            }
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(window)
        // Enable v-sync
        glfwSwapInterval(1)
        // Make the window visible
        glfwShowWindow(window)
    }

    private fun run() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities()

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, { window1, key1, scancode, action1, mods ->
            val key = Key.fromValue(key1)
            val keyActionType = KeyActionType.fromValue(action1)
            if (key != null && keyActionType != null) {
                for (listener in keyDownListeners) {
                    listener.action(key, keyActionType)
                }
            }
        })

        glfwSetWindowSizeCallback(window, { window1, w, h ->
            glViewport(0, 0, w, h)
            for (listener in sizeChangedListeners) {
                listener.change(w, h)
            }
        })

        initGame()

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
        glClearDepth(1.0)

        var previousFrameTime = glfwGetTime()
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
            // Poll for window events. The key callback above will only be invoked during this call.
            glfwPollEvents()

            var frameTime = glfwGetTime()
            updateGame(frameTime - previousFrameTime)

            glfwSwapBuffers(window)
            previousFrameTime = frameTime
        }
    }

    private fun terminate() {
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window)
        glfwDestroyWindow(window)

        // Terminate GLFW and free the error callback
        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
    }
}
