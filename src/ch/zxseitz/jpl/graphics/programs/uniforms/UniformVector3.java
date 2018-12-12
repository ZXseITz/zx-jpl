package ch.zxseitz.jpl.graphics.programs.uniforms;

import ch.zxseitz.jpl.math.Vector3;

import static org.lwjgl.opengl.GL45.glUniform3f;

public class UniformVector3 extends AbstractUniform<Vector3> {
    public UniformVector3(String name) {
        super(name);
    }

    @Override
    public void write(int location) {
        glUniform3f(location, value.x, value.y, value.z);
    }
}
