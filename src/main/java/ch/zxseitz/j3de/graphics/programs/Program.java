package ch.zxseitz.j3de.graphics.programs;

import ch.zxseitz.j3de.exceptions.AttribException;
import ch.zxseitz.j3de.exceptions.ProgramException;
import ch.zxseitz.j3de.exceptions.UniformException;
import ch.zxseitz.j3de.math.Matrix4;
import ch.zxseitz.j3de.math.Vector2;
import ch.zxseitz.j3de.math.Vector3;
import ch.zxseitz.j3de.math.Vector4;

import java.util.*;

import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL45.*;

public class Program {
    private final int id;
    private final List<Shader> shaders;
    private final List<ShaderAttribute> attributes;

    public Program(Shader[] shaders, ShaderAttribute[] attributes) throws ProgramException {
        this.shaders = Collections.unmodifiableList(List.of(shaders));
        this.attributes = Collections.unmodifiableList(List.of(attributes));
        this.id = glCreateProgram();
        // attach shader
        for (var shader : shaders) {
            if (shader.isDeleted()) {
                throw new ProgramException("Cannot attach deleted shader " + shader + " to program " + id, this);
            }
            glAttachShader(id, shader.getId());
        }
        // link program
        glLinkProgram(id);
        if (glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE) {
            throw new ProgramException("Error linking program:" +
                    glGetProgramInfoLog(glGetProgrami(id, GL_INFO_LOG_LENGTH)), this);
        }
    }

    public int getId() {
        return id;
    }

    public List<Shader> getShaders() {
        return shaders;
    }

    public List<ShaderAttribute> getAttributes() {
        return attributes;
    }

    public void use() {
        glUseProgram(this.id);
    }

    public void writeUniform(String name, boolean value) throws UniformException {
        glUniform1i(getUniformLocation(name), value ? 1 : 0);
    }

    public void writeUniform(String name, int value) throws UniformException {
        glUniform1i(getUniformLocation(name), value);
    }

    public void writeUniform(String name, float value) throws UniformException {
        glUniform1f(getUniformLocation(name), value);
    }

    public void writeUniform(String name, Vector2 value) throws UniformException {
        glUniform2f(getUniformLocation(name), value.x, value.y);
    }

    public void writeUniform(String name, Vector3 value) throws UniformException {
        glUniform3f(getUniformLocation(name), value.x, value.y, value.z);
    }

    public void writeUniform(String name, Vector4 value) throws UniformException {
        glUniform4f(getUniformLocation(name), value.x, value.y, value.z, value.w);
    }

    public void writeUniform(String name, Matrix4 value) throws UniformException {
        glUniformMatrix4fv(getUniformLocation(name), true, value.extract());
    }

    public int getUniformLocation(String name) throws UniformException {
        var location = glGetUniformLocation(this.id, name);
        if (location < 0)
            throw new UniformException(String.format("AbstractUniform %s has no location in program %d", name, id),
                    this, name);
        return location;
    }

    public int getAttribLocation(String name) throws AttribException {
        var location = glGetAttribLocation(this.id, name);
        if (location < 0)
            throw new AttribException(String.format("Attribute %s has no location in program %d", name, id), this, name);
        return location;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Program) {
            return id == ((Program) obj).id;
        }
        return false;
    }
}
