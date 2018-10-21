package ch.zxseitz.jpl.graphics.javafx;

import javafx.scene.paint.Color;

public class HdrColor {
    public static HdrColor WHITE = new HdrColor(1f, 1f, 1f);
    public static HdrColor BLACK = new HdrColor(0f, 0f, 0f);
    public static HdrColor GREY = new HdrColor(0.5f, 0.5f, 0.5f);
    public static HdrColor RED = new HdrColor(1f, 0f, 0f);
    public static HdrColor GREEN = new HdrColor(0f, 1f, 0f);
    public static HdrColor BLUE = new HdrColor(0f, 0f, 1f);
    public static HdrColor YELLOW = new HdrColor(1f, 1f, 1f);
    public static HdrColor CYAN = new HdrColor(0f, 1f, 1f);
    public static HdrColor MARGENTA = new HdrColor(1f, 0f, 1f);

    public final float red, green, blue;

    public HdrColor(float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public HdrColor(Color color) {
        this.red = (float) color.getRed();
        this.green = (float) color.getGreen();
        this.blue = (float) color.getBlue();
    }

    private static byte toByte(float f) {
        return (byte) (f <= 0 ? 0 : f >= 1 ? 255 : Math.round(f * 255));
    }

    public byte getRed() {
        return toByte(red);
    }

    public byte getGreen() {
        return toByte(green);
    }

    public byte getBlue() {
        return toByte(blue);
    }

    public HdrColor add(HdrColor other) {
        return new HdrColor(red + other.red, green + other.green, blue + other.blue);
    }

    public HdrColor subtract(HdrColor other) {
        return new HdrColor(red - other.red, green - other.green, blue - other.blue);
    }

    public HdrColor multiply(HdrColor other) {
        return new HdrColor(red * other.red, green * other.green, blue * other.blue);
    }
}
