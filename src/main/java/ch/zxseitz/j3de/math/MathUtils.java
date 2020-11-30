package ch.zxseitz.j3de.math;

public class MathUtils {
    public static final float TOLERANCE = 0.000001f;

    public static boolean isFloatEquals(float a, float b) {
        return Math.abs(a - b) < TOLERANCE;
    }

    private MathUtils() {}
}
