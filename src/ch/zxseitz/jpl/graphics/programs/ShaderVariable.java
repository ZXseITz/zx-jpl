package ch.zxseitz.jpl.graphics.programs;

public class ShaderVariable {
    public static final ShaderVariable POS = new ShaderVariable("pos", 3);
    public static final ShaderVariable NORMAL = new ShaderVariable("normal", 3);
    public static final ShaderVariable COLOR = new ShaderVariable("color", 4);
    public static final ShaderVariable UV = new ShaderVariable("uv", 2);

    public final String name;
    public final int size;
    private final int hash;

    public ShaderVariable(String name, int size) {
        this.name = name;
        this.size = size;
        this.hash = String.format("%s:%d", name, size).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ShaderVariable) {
            var other = (ShaderVariable) obj;
            return name.equals(other.name) && size == other.size;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.hash;
    }
}
