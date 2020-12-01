package ch.zxseitz.j3de.utils;

import static org.lwjgl.opengl.GL45.*;

public final class ErrorUtils {
    private ErrorUtils() {}

    public static String getErrorInfo(int error) {
        return switch(error) {
            case GL_NO_ERROR -> "GL_NO_ERROR";
            case GL_INVALID_ENUM -> "GL_INVALID_ENUM";
            case GL_INVALID_VALUE -> "GL_INVALID_VALUE";
            case GL_INVALID_OPERATION -> "GL_INVALID_OPERATION";
            case GL_STACK_OVERFLOW -> "GL_STACK_OVERFLOW";
            case GL_STACK_UNDERFLOW -> "GL_STACK_UNDERFLOW";
            case GL_OUT_OF_MEMORY -> "GL_OUT_OF_MEMORY";
            default -> "UNKNOWN_ERROR";
        };
    }
}
