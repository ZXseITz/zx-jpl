package ch.zxseitz.j3de.graphics;

import ch.zxseitz.j3de.math.Vector4;

public class Color {
    public static Color WHITE = new Color(1f, 1f, 1f);
    public static Color BLACK = new Color(0f, 0f, 0f);
    public static Color RED = new Color(1f, 0f, 0f);
    public static Color GREEN = new Color(0f, 1f, 0f);
    public static Color BLUE = new Color(0f, 0f, 1f);
    public static Color YELLOW = new Color(1f, 1f, 0f);
    public static Color MARGENTA = new Color(1f, 0f, 1f);
    public static Color CYAN = new Color(0f, 1f, 1f);

    public static Color fromByte(byte r, byte g, byte b) {
        var x = 1f / 255;
        return new Color(r * x, g * x, b * x, 1f);
    }

    public static Color fromByte(byte r, byte g, byte b, byte a) {
        var x = 1f / 255;
        return new Color(r * x, g * x, b * x, a * x);
    }

    public final float r;
    public final float g;
    public final float b;
    public final float a;

    public Color(float r, float g, float b) {
        this(r, g, b, 1f);
    }

    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Color clamp() {
        return new Color(clamp(r), clamp(g), clamp(b), a);
    }

    public Vector4 toVector4() {
        return new Vector4(r, g, b, a);
    }

    private static float clamp(float value) {
        return value < 0f ? 0f : Math.min(value, 1f);
    }

    //todo arithmetic handle alpha channel correctly

    public Color add(Color color) {
        return Color.add(this, color);
    }

    public Color subtract(Color color) {
        return Color.subtract(this, color);
    }

    public Color multiply(Color color) {
        return Color.multiply(this, color);
    }

    public static Color add(Color a, Color b) {
        return new Color(
                a.r + b.r,
                a.g + b.g,
                a.b + b.b
        );
    }

    public static Color subtract(Color a, Color b) {
        return new Color(
                a.r - b.r,
                a.g - b.g,
                a.b - b.b
        );
    }

    public static Color multiply(Color a, Color b) {
        return new Color(
                a.r * b.r,
                a.g * b.g,
                a.b * b.b
        );
    }
}
