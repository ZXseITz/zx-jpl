package ch.zxseitz.j3de.graphics.core;

import org.lwjgl.opengl.GL45.*;

enum class PrimitiveType(val id: Int) {
    POINTS(GL_POINTS),
    LINES(GL_LINES),
    LINE_LOOP(GL_LINE_LOOP),
    TRIANGLES(GL_TRIANGLES),
    TRIANGLE_STRIP(GL_TRIANGLE_STRIP),
    TRIANGLE_FAN(GL_TRIANGLE_FAN);
}
