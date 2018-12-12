package ch.zxseitz.jpl.graphics.programs.uniforms;

import static org.lwjgl.opengl.GL45.glUniform1i;

public class UniformBool extends AbstractUniform<Boolean> {
    public UniformBool(String name) {
        super(name);
    }

    @Override
    public void write(int location) {
        glUniform1i(location, value ? 1 : 0);
    }
}
