package ch.zxseitz.j3de.math;

public class Vector2 {
    public static final Vector2 ZERO = new Vector2(0f, 0f);
    public static final Vector2 ONE = new Vector2(1f, 1f);
    public static final Vector2 X = new Vector2(1f, 0f);
    public static final Vector2 Y = new Vector2(0f, 1f);

    public final float x, y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 a) {
        this.x = a.x;
        this.y = a.y;
    }

    public Vector2 add(Vector2 vec) {
        return new Vector2(x + vec.x, y + vec.y);
    }

    public Vector2 subtract(Vector2 vec) {
        return new Vector2(x - vec.x, y - vec.y);
    }

    public Vector2 multiply(Vector2 vec) {
        return new Vector2(x * vec.x, y * vec.y);
    }

    public Vector2 divide(Vector2 vec) {
        return new Vector2(x / vec.x, y / vec.y);
    }

    public Vector2 scale(float s) {
        return new Vector2(x * s, y * s);
    }

    public float norm() {
        return (float) Math.sqrt(normSquared());
    }

    public float normSquared() {
        return x * x + y * y;
    }

    public float dot(Vector2 v) {
        return x * v.x + y * v.y;
    }

    @Override
    public boolean equals(Object obj) {
//        if (obj instanceof Vector2 vec) {
        if (obj instanceof Vector2) {
            var vec = (Vector2) obj;
            return MathUtils.isFloatEquals(x, vec.x) && MathUtils.isFloatEquals(y, vec.y);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("(%.3f, %.3f)", x, y);
    }
}
