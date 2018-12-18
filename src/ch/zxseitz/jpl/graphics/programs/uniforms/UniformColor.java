package ch.zxseitz.jpl.graphics.programs.uniforms;

import ch.zxseitz.jpl.graphics.Color;

import static org.lwjgl.opengl.GL45.glUniform4f;

public class UniformColor extends AbstractUniform<Color> {
    public UniformColor(String name) {
        super(name);
    }

    @Override
    public void write(int location) {
        glUniform4f(location, value.r, value.g, value.b, value.a);
    }
}
