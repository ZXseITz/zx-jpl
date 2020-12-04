package ch.zxseitz.j3de.graphics.core;

import ch.zxseitz.j3de.exceptions.ShaderException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL45.*;

public class Shader {
    public enum Type {
        VERTEX_SHADER(GL_VERTEX_SHADER),
        FRAGMENT_SHADER(GL_FRAGMENT_SHADER),
        GEOMETRY_SHADER(GL_GEOMETRY_SHADER);

        int id;

        Type(int id) {
            this.id = id;
        }
    }

    private final int id;
    private final Type type;
    private boolean deleted;

    public Shader(Path path, Type type) throws ShaderException, IOException {
        this.id = glCreateShader(type.id);
        this.type = type;
        this.deleted = false;
        glShaderSource(id, loadShader(path));
        glCompileShader(id);
        if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE)
            throw new ShaderException("Error compiling shader\n" + glGetShaderInfoLog(id), this);
    }

    private String loadShader(Path path) throws IOException {
        var content = new StringBuilder();
        try (var reader = Files.newBufferedReader(path)) {
            reader.lines().forEach(line -> {
                content.append(line);
                content.append('\n');
            });
        }
        return content.toString();
    }

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public synchronized boolean isDeleted() {
        return deleted;
    }

    public synchronized void destroy() {
        deleted = true;
        glDeleteShader(id);
    }

    @Override
    public String toString() {
        return type.toString() + ',' + id;
    }
}
