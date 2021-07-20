package ch.zxseitz.j3de.window;

import org.lwjgl.glfw.GLFW.*;

enum class KeyActionType {
    UP,
    DOWN,
    PRESSED;

    companion object {
        fun fromValue(value: Int): KeyActionType? {
            return when(value) {
                GLFW_RELEASE -> UP;
                GLFW_PRESS -> DOWN;
                GLFW_REPEAT -> PRESSED;
                else -> null;
            }
        }
    }
}
