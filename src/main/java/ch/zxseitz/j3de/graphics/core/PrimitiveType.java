package ch.zxseitz.j3de.graphics.core;

import static org.lwjgl.opengl.GL45.*;

public enum PrimitiveType {
    POINTS(GL_POINTS),
    LINES(GL_LINES),
    LINE_LOOP(GL_LINE_LOOP),
    TRIANGLES(GL_TRIANGLES),
    TRIANGLE_STRIP(GL_TRIANGLE_STRIP),
    TRIANGLE_FAN(GL_TRIANGLE_FAN);

    public final int id;
    PrimitiveType(int id) {this.id = id;}
}
