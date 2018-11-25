package ch.zxseitz.jpl.graphics.programs;

public class Shader {
    public enum ShaderType {
        VERTEX_SHADER,
        FRAGMENT_SHADER
    }

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
