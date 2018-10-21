package ch.zxseitz.jpl.math;

public class Vector2 {
    public static final Vector2 ZERO = new Vector2(0f, 0f);
    public static final Vector2 ONE = new Vector2(1f, 1f);
    public static final Vector2 X = new Vector2(1f, 0f);
    public static final Vector2 Y = new Vector2(0f, 1f);

    public float x, y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 vec) {
        this.x = vec.x;
        this.y = vec.y;
    }

    public void add(Vector2 vec) {
        x += vec.x;
        y += vec.y;
    }

    public void subtract(Vector2 vec) {
        x -= vec.x;
        y -= vec.y;
    }

    public void multiply(Vector2 vec) {
        x *= vec.x;
        y *= vec.y;
    }

    public void divide(Vector2 vec) {
        x /= vec.x;
        y /= vec.y;
    }

    public void scale(float s) {
        x *= s;
        y *= s;
    }

    public float norm() {
        return (float) Math.sqrt(normSquared());
    }

    public float normSquared() {
        return x * x + y * y;
    }

    public float dot(Vector2 vec) {
        return x * vec.x + y * vec.y;
    }
}
