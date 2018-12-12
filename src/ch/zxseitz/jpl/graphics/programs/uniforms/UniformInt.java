package ch.zxseitz.jpl.graphics.programs.uniforms;

import static org.lwjgl.opengl.GL45.glUniform1i;

public class UniformInt extends AbstractUniform<Integer> {
    public UniformInt(String name) {
        super(name);
    }

    @Override
    public void write(int location) {
        glUniform1i(location, value);
    }
}
