package ch.zxseitz.j3de.graphics.programs.uniforms;

import ch.zxseitz.j3de.graphics.Color;

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
