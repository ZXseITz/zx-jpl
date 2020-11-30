package ch.zxseitz.j3de.windows;

import static org.lwjgl.glfw.GLFW.*;

public enum KeyActionType {
    UNKNOWN,
    UP,
    DOWN,
    PRESSED;

    public static KeyActionType fromValue(int value) {
        return switch (value) {
            case GLFW_RELEASE -> UP;
            case GLFW_PRESS -> DOWN;
            case GLFW_REPEAT -> PRESSED;
            default -> UNKNOWN;
        };
    }
}
