package ch.zxseitz.jpl.graphics.programs.uniforms;

import ch.zxseitz.jpl.math.Vector2;

import static org.lwjgl.opengl.GL45.glUniform2f;

public class UniformVector2 extends AbstractUniform<Vector2> {
    public UniformVector2(String name, Vector2 value) {
        super(name, value);
    }

    @Override
    public void write(int location) {
        glUniform2f(location, value.x, value.y);
    }
}
