package ch.zxseitz.j3de.windows;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;

public class Window {
    private final long id;
    private final List<IWindowKeyCallback> keyCallbacks;
    private final List<IWindowSizeCallback> sizeCallbacks;

    public Window(long id) {
        this.id = id;
        this.keyCallbacks = new ArrayList<>();
        this.sizeCallbacks = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void show() {
        glfwShowWindow(id);
    }

    public void close() {
        glfwSetWindowShouldClose(id, true);
    }

    public List<IWindowKeyCallback> getKeyCallbacks() {
        return keyCallbacks;
    }

    public List<IWindowSizeCallback> getSizeCallbacks() {
        return sizeCallbacks;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Window) {
            var window = (Window) o;
            return id == window.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
