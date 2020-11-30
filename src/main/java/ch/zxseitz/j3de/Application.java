package ch.zxseitz.j3de;

import ch.zxseitz.j3de.exceptions.J3deException;
import ch.zxseitz.j3de.exceptions.ResourceNotFoundException;
import ch.zxseitz.j3de.exceptions.WindowException;
import ch.zxseitz.j3de.windows.*;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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

    public static Path getClassResource(String path) throws ResourceNotFoundException {
        try {
            return Paths.get(Objects.requireNonNull(Application.class.getClassLoader().getResource(path)).toURI());
        } catch (Exception e) {
            throw new ResourceNotFoundException(path);
        }
    }

    private long window;
    // todo custom keymap
    protected final List<IWindowKeyListener> keyDownListeners;
    protected final List<IWindowSizeListener> sizeChangedListeners;

    public Application() {
        this.keyDownListeners = new ArrayList<>(1);
        this.sizeChangedListeners = new ArrayList<>(1);
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
     * @throws J3deException
     */
    protected abstract void initGame() throws J3deException, IOException;

    /**
     * Updates scene.
     *
     * @param delta elapsed time since the last frame was rendered.
     * @throws J3deException
     */
    protected abstract void updateGame(double delta) throws J3deException;

    /**
     * Set a flag to close this application.
     * This method does not interrupt the current frame
     */
    protected void close() {
        glfwSetWindowShouldClose(window, true);
    }

    public void addKeyListener(IWindowKeyListener keyListener) {
        keyDownListeners.add(keyListener);
    }

    public void removeKeyListener(IWindowKeyListener keyListener) {
        keyDownListeners.remove(keyListener);
    }

    public void addSizeChangedListener(IWindowSizeListener sizeListener) {
        sizeChangedListeners.add(sizeListener);
    }

    public void removeSizeChangedListener(IWindowSizeListener sizeListener) {
        sizeChangedListeners.remove(sizeListener);
    }

    private void setUp() throws J3deException {
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
            throw new WindowException("Cannot create window " + applicationOptions.getTitle(), 0L);
        }

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

    private void run() throws J3deException, IOException {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window1, key1, scancode, action1, mods) -> {
            var key = Key.fromValue(key1);
            var keyActionType = KeyActionType.fromValue(action1);
            for (var listener : keyDownListeners) {
                listener.action(key, keyActionType);
            }
        });

        glfwSetWindowSizeCallback(window, (window1, w, h) -> {
            glViewport(0, 0, w, h);
            for (var listener : sizeChangedListeners) {
                listener.change(w, h);
            }
        });

        initGame();

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClearDepth(1.);

        var previousFrameTime = glfwGetTime();
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            // Poll for window events. The key callback above will only be invoked during this call.
            glfwPollEvents();

            var frameTime = glfwGetTime();
            updateGame(frameTime - previousFrameTime);

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
