package ch.zxseitz.j3de;

import ch.zxseitz.j3de.exceptions.J3deException;
import ch.zxseitz.j3de.exceptions.WindowException;
import ch.zxseitz.j3de.graphics.ISizeChanged;
import ch.zxseitz.j3de.windows.ApplicationOptions;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public abstract class Application {
    protected static void launch(String[] args) {
        try {
            // todo verify
            var clazz = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
            var app = (Application) clazz.getConstructor().newInstance();

            app.setUp();
            app.run();
            app.terminate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long window;
    protected List<ISizeChanged> sizeChangedListeners;

    public Application() {
        this.sizeChangedListeners = new ArrayList<>(1);
    }

    public List<ISizeChanged> getSizeChangedListeners() {
        return sizeChangedListeners;
    }

    /**
     * Configures the application before init the game.
     *
     * @return application options.
     */
    protected ApplicationOptions applicationInit() {
        return new ApplicationOptions("J3de Application", 450, 450);
    }

    /**
     * Configures the game: Setup shader programs and scene.
     *
     * @throws Exception
     */
    protected abstract void initGame() throws Exception;

    /**
     * Updates scene.
     *
     * @param delta elapsed time since the last frame was rendered.
     * @throws Exception
     */
    protected abstract void update(double delta) throws Exception;

    /**
     * Set a flag to close this application.
     * This method does not interrupt the current frame
     */
    protected void close() {
        glfwSetWindowShouldClose(window, true);
    }

    private void setUp() throws Exception {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        var applicationOptions = applicationInit();

        // Create the window
        window = glfwCreateWindow(applicationOptions.getWidth(), applicationOptions.getHeight(),
                applicationOptions.getTitle(), NULL, NULL);
        if (window == NULL) {
            throw new WindowException("Cannot create window " + applicationOptions.getTitle(), null);
        }
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window1, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window1, true); // We will detect this in the rendering start
        });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);
        // Make the window visible
        glfwShowWindow(window);
    }

    private void run() throws Exception {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClearDepth(1.);

        glfwSetWindowSizeCallback(window, (window1, w, h) -> {
            glViewport(0, 0, w, h);
            for (var listener : sizeChangedListeners) {
                listener.change(w, h);
            }
        });
        initGame();
        var previousFrameTime = glfwGetTime();
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            // Poll for window events. The key callback above will only be invoked during this call.
            glfwPollEvents();

            var frameTime = glfwGetTime();
            update(frameTime - previousFrameTime);

            glfwSwapBuffers(window);
            previousFrameTime = frameTime;
        }
    }

    private void terminate() {
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
