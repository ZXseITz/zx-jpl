package ch.zxseitz.j3de.graphics.programs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL45.*;

public class Shader {
    public enum Type {
        VERTEX_SHADER(GL_VERTEX_SHADER),
        FRAGMENT_SHADER(GL_FRAGMENT_SHADER),
        GEOMETRY_SHADER(GL_GEOMETRY_SHADER);

        int id;
        Type(int id) {this.id = id; }
    }

    private int id;
    private Type type;
    private boolean deleted;

    public Shader(Path path, Type type) {
        try {
            this.id = glCreateShader(type.id);
            this.type = type;
            this.deleted = false;
            glShaderSource(id, loadShader(path));
            glCompileShader(id);
            if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE)
                throw new Exception("Error compiling shader\n" + glGetShaderInfoLog(id));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
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

    public boolean isDeleted() {
        return deleted;
    }

    public void destroy() {
        deleted = true;
        glDeleteShader(id);
    }

    @Override
    public String toString() {
        return type.toString() + ',' + id;
    }
}
