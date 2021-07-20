package ch.zxseitz.j3de.utils;

import org.lwjgl.opengl.GL45.*;

class ErrorUtils private constructor() {
    companion object {
        fun getErrorInfo(error: Int): String {
            return when(error) {
                GL_NO_ERROR -> "GL_NO_ERROR";
                GL_INVALID_ENUM -> "GL_INVALID_ENUM";
                GL_INVALID_VALUE -> "GL_INVALID_VALUE";
                GL_INVALID_OPERATION -> "GL_INVALID_OPERATION";
                GL_STACK_OVERFLOW -> "GL_STACK_OVERFLOW";
                GL_STACK_UNDERFLOW -> "GL_STACK_UNDERFLOW";
                GL_OUT_OF_MEMORY -> "GL_OUT_OF_MEMORY";
                else -> "UNKNOWN_ERROR";
            };
        }
    }
}
