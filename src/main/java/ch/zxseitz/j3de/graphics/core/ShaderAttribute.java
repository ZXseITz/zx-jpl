package ch.zxseitz.j3de.graphics.core;

import java.util.Objects;

public class ShaderAttribute {
    public static final ShaderAttribute POS = new ShaderAttribute("pos", 3);
    public static final ShaderAttribute NORMAL = new ShaderAttribute("normal", 3);
    public static final ShaderAttribute COLOR = new ShaderAttribute("color", 4);
    public static final ShaderAttribute UV = new ShaderAttribute("uv", 2);

    public final String name;
    public final int size;

    public ShaderAttribute(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ShaderAttribute) {
            var other = (ShaderAttribute) o;
            return name.equals(other.name) && size == other.size;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, size);
    }

    @Override
    public String toString() {
        return String.format("%s:%d", name, size);
    }
}
