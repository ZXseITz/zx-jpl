package ch.zxseitz.jpl.graphics;

import javafx.util.Pair;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;

import static org.lwjgl.opengl.GL45.*;

public class Shader {
    private static final Charset utf8 = Charset.forName("UTF-8");

    public enum ShaderType {
        VERTEX_SHADER(GL_VERTEX_SHADER),
        FRAGMENT_SHADER(GL_FRAGMENT_SHADER);

        public final int code;
        ShaderType(int code) {this.code = code;}
    }

    @SuppressWarnings("unchecked")
    public static final Pair<String, Integer>[] shaderAttributes = new Pair[] {
            new Pair("pos", 3),
            new Pair("normal", 3),
            new Pair("color", 4),
            new Pair("uv", 2)
    };

    public final int id;
    private final String path;
    private final ShaderType type;

    //TODO: parse shader variables

    Shader(String path, ShaderType type) {
        this.path = path;
        this.type = type;

        var source = loadShader(path, utf8);
        var id = glCreateShader(type.code);
        glShaderSource(id, source);
        glCompileShader(id);

        if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RuntimeException(String.format("Error creating shader %s\n%s", this.path,
                    glGetShaderInfoLog(id, glGetShaderi(id, GL_INFO_LOG_LENGTH))));
        }

        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public ShaderType getType() {
        return type;
    }

    private String loadShader(String path, Charset encoding) {
        try {
            var file = new File(path);
            var encoded = Files.readAllBytes(file.toPath());
            return new String(encoded, encoding);
        } catch (Exception e) {
            System.err.println(String.format("Error reading shader %s", path));
            e.printStackTrace();
        }
        return null;
    }
}
