package ch.zxseitz.jpl.graphics.programs.uniforms;

import ch.zxseitz.jpl.math.Matrix4;

import static org.lwjgl.opengl.GL45.glUniformMatrix4fv;

public class UniformMatrix4 extends AbstractUniform<Matrix4> {
    public UniformMatrix4(String name) {
        super(name);
    }

    @Override
    public void write(int location) {
        glUniformMatrix4fv(location, true, value.getData());
    }
}
