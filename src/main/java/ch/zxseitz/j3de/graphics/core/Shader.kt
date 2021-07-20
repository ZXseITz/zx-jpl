package ch.zxseitz.j3de.graphics.core;

import ch.zxseitz.j3de.ShaderException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.lwjgl.opengl.GL45.*;

class Shader(val path: Path, val type: Type) {
    enum class Type(val id: Int) {
        VERTEX_SHADER(GL_VERTEX_SHADER),
        FRAGMENT_SHADER(GL_FRAGMENT_SHADER),
        GEOMETRY_SHADER(GL_GEOMETRY_SHADER);
    }

    val id: Int
    @Volatile
    var deleted: Boolean = false
        private set
        @Synchronized get

    init {
        this.id = glCreateShader(type.id);
        glShaderSource(id, loadShader(path));
        glCompileShader(id);
        if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE)
            throw ShaderException("Error compiling shader\n" + glGetShaderInfoLog(id), this);
    }

    @Throws(IOException::class)
    private fun loadShader(path: Path): String {
        val content = StringBuilder();
        Files.newBufferedReader(path).use { reader ->
            reader.lines().forEach { line ->
                content.append(line);
                content.append('\n');
            }
        }
        return content.toString();
    }

    @Synchronized
    fun destroy() {
        deleted = true;
        glDeleteShader(id);
    }

    override fun toString(): String {
        return "($type, $id)"
    }
}
