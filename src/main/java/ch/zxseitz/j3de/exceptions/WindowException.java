package ch.zxseitz.j3de.exceptions;

import ch.zxseitz.j3de.windows.Window;

public class WindowException extends J3deException {
    private final Window window;

    public WindowException(String message, Window window) {
        super(message);
        this.window = window;
    }

    public Window getWindow() {
        return window;
    }
}
