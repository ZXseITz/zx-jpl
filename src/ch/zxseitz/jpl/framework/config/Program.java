package ch.zxseitz.jpl.framework.config;

import ch.zxseitz.jpl.framework.math.Vector2;
import ch.zxseitz.jpl.framework.math.Vector3;
import ch.zxseitz.jpl.framework.math.Vector4;
import org.ejml.data.FMatrix2;
import org.ejml.data.FMatrix3;
import org.ejml.data.FMatrix4;
import org.ejml.data.FMatrix4x4;

import static org.lwjgl.opengl.GL20.*;

public class Program {
    public final int id;
    public final Shader vertexShader, fragmentShader;

    public Program(String vertexShader, String fragmentShader) {
        var id = glCreateProgram();
        this.vertexShader = new Shader(vertexShader, Shader.ShaderType.VERTEX_SHADER);
        this.fragmentShader = new Shader(fragmentShader, Shader.ShaderType.FRAGMENT_SHADER);
        glLinkProgram(id);
        if (glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE) {
            throw new RuntimeException(String.format("Error linking program\n%s",
                    glGetProgramInfoLog(glGetProgrami(id, GL_INFO_LOG_LENGTH))));
        }
        this.id = id;
    }

    public void use() {
        glUseProgram(this.id);
    }

    private int getLocation(String name) {
        return glGetUniformLocation(this.id, name);
    }

    public void writeBool(String name, boolean value) {
        glUniform1i(getLocation(name), value ? 1 : 0);
    }

    public void writeInt(String name, int value) {
        glUniform1i(getLocation(name), value);
    }

    public void writeFloat(String name, float value) {
        glUniform1f(getLocation(name), value);
    }

    public void writeVec2(String name, Vector2 value) {
        glUniform2f(getLocation(name), value.x, value.y);
    }

    public void writeVec3(String name, Vector3 value) {
        glUniform3f(getLocation(name), value.x, value.y, value.z);
    }

    public void writeVec4(String name, Vector4 value) {
        glUniform4f(getLocation(name), value.x, value.y, value.z, value.w);
    }

    public void writeMat4(String name, FMatrix4x4 value) {
        //TODO: check transpose
        //TODO flyweight pattern array?
        glUniformMatrix4fv(getLocation(name), false, new float[]{
                value.a11, value.a12, value.a13, value.a14,
                value.a21, value.a22, value.a23, value.a24,
                value.a31, value.a32, value.a33, value.a34,
                value.a41, value.a42, value.a43, value.a44,
        });
    }
}
