package ch.zxseitz.j3de.graphics.programs.uniforms;

import ch.zxseitz.j3de.math.Vector4;

import static org.lwjgl.opengl.GL45.glUniform4f;

public class UniformVector4 extends AbstractUniform<Vector4> {
    public UniformVector4(String name) {
        super(name);
    }

    @Override
    public void write(int location) {
        glUniform4f(location, value.x, value.y, value.z, value.w);
    }
}
