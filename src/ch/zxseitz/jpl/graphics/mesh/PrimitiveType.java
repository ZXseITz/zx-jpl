package ch.zxseitz.jpl.graphics.mesh;

import static org.lwjgl.opengl.GL11.*;

public enum PrimitiveType {
    POINTS(GL_POINTS),
    LINES(GL_LINES),
    LINE_LOOP(GL_LINE_LOOP),
    TRIANGLES(GL_TRIANGLES),
    TRIANGLE_STRIP(GL_TRIANGLE_STRIP),
    TRIANGLE_FAN(GL_TRIANGLE_FAN);

    public final int type;
    PrimitiveType(int type) {this.type = type;}
}