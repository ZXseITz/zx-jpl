package ch.zxseitz.j3de.graphics.programs;

public class ShaderAttribute {
    public static final ShaderAttribute POS = new ShaderAttribute("pos", 3);
    public static final ShaderAttribute NORMAL = new ShaderAttribute("normal", 3);
    public static final ShaderAttribute COLOR = new ShaderAttribute("color", 4);
    public static final ShaderAttribute UV = new ShaderAttribute("uv", 2);

    public final String name;
    public final int size;
    private final int hash;

    public ShaderAttribute(String name, int size) {
        this.name = name;
        this.size = size;
        this.hash = String.format("%s:%d", name, size).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ShaderAttribute) {
            var other = (ShaderAttribute) obj;
            return name.equals(other.name) && size == other.size;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.hash;
    }
}
