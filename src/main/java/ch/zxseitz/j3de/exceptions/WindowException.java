package ch.zxseitz.j3de.exceptions;

public class WindowException extends J3deException {
    private final long window;

    public WindowException(String message, long window) {
        super(message);
        this.window = window;
    }

    public long getWindow() {
        return window;
    }
}
