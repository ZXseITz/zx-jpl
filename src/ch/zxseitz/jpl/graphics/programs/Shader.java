package ch.zxseitz.jpl.graphics.programs;

import javafx.util.Pair;

public class Shader {
    public enum ShaderType {
        VERTEX_SHADER,
        FRAGMENT_SHADER
    }

    @SuppressWarnings("unchecked")
    public static final Pair<String, Integer>[] shaderAttributes = new Pair[] {
            new Pair("pos", 3),
            new Pair("normal", 3),
            new Pair("color", 4),
            new Pair("uv", 2)
    };

    public final int id;
    private final ShaderType type;
    private final String name;

    public Shader(int id, ShaderType type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public ShaderType getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
