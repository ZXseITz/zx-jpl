package ch.zxseitz.jpl.graphics.programs.uniforms;

import static org.lwjgl.opengl.GL45.glUniform1f;

public class UniformFloat extends AbstractUniform<Float> {
    public UniformFloat(String name, float value) {
        super(name, value);
    }

    @Override
    public void write(int location) {
        glUniform1f(location, value);
    }
}
