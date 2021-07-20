package ch.zxseitz.j3de.graphics.core;

import ch.zxseitz.j3de.AttribException;
import ch.zxseitz.j3de.ProgramException;
import ch.zxseitz.j3de.UniformException;
import ch.zxseitz.j3de.math.Matrix4;
import ch.zxseitz.j3de.math.Vector2;
import ch.zxseitz.j3de.math.Vector3;
import ch.zxseitz.j3de.math.Vector4;

import org.lwjgl.opengl.GL20.glUniform4f;
import org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import org.lwjgl.opengl.GL45.*;

class Program(shaders: Array<Shader>, attributes: Array<ShaderAttribute>) {
    val id: Int
    val shaders: List<Shader>
    val attributes: List<ShaderAttribute>;

    init  {
        this.id = glCreateProgram();
        this.shaders = shaders.toList()
        this.attributes = attributes.toList()
        // attach shader
        for (shader in shaders) {
            if (shader.deleted) {
                throw ProgramException("Cannot attach deleted shader " + shader + " to program " + id, this);
            }
            glAttachShader(id, shader.id);
        }
        // link program
        glLinkProgram(id);
        if (glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE) {
            throw ProgramException("Error linking program:" +
                    glGetProgramInfoLog(glGetProgrami(id, GL_INFO_LOG_LENGTH)), this);
        }
    }

    fun getAttributeSize(): Int {
        return attributes.stream().map{ attribute -> attribute.size }.reduce(Integer::sum).get();
    }

    fun use() {
        glUseProgram(id);
    }

    fun writeUniform(name: String, value: Boolean) {
        glUniform1i(getUniformLocation(name),  if(value) 1 else 0);
    }

    fun writeUniform(name: String, value: Int)  {
        glUniform1i(getUniformLocation(name), value);
    }

    fun writeUniform(name: String, value: Float) {
        glUniform1f(getUniformLocation(name), value);
    }

    fun writeUniform(name: String, value: Vector2) {
        glUniform2f(getUniformLocation(name), value.x, value.y);
    }

    fun writeUniform(name: String, value: Vector3) {
        glUniform3f(getUniformLocation(name), value.x, value.y, value.z);
    }

    fun writeUniform(name: String, value: Vector4) {
        glUniform4f(getUniformLocation(name), value.x, value.y, value.z, value.w);
    }

    fun writeUniform(name: String, value: Matrix4)  {
        glUniformMatrix4fv(getUniformLocation(name), true, floatArrayOf(
            value.a11, value.a12, value.a13, value.a14,
            value.a21, value.a22, value.a23, value.a24,
            value.a31, value.a32, value.a33, value.a34,
            value.a41, value.a42, value.a43, value.a44,
        ))
    }

    fun getUniformLocation(name: String): Int  {
        val location = glGetUniformLocation(id, name);
        if (location < 0)
            throw UniformException("AbstractUniform $name has no location in program $id", this, name);
        return location;
    }

    fun getAttribLocation(name: String): Int {
        val location = glGetAttribLocation(this.id, name);
        if (location < 0)
            throw AttribException("Attribute $name has no location in program $id", this, name);
        return location;
    }

    @Override
    override fun toString(): String {
        return "($id, (${attributes.stream().map { sa -> sa.toString() }.reduce("", { left, right -> "$left,$right" })}))"
    }
}
