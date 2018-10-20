package ch.zxseitz.jpl.framework.math;

public class Vector3 {
    public static final Vector3 ZERO = new Vector3(0f, 0f, 0f);
    public static final Vector3 ONE = new Vector3(1f, 1f, 1f);
    public static final Vector3 XY = new Vector3(1f, 1f, 0f);
    public static final Vector3 XZ = new Vector3(1f, 0f, 1f);
    public static final Vector3 YZ = new Vector3(0f, 1f, 1f);
    public static final Vector3 X = new Vector3(1f, 0f, 0f);
    public static final Vector3 Y = new Vector3(0f, 1f, 0f);
    public static final Vector3 Z = new Vector3(0f, 0f, 1f);

    public float x, y, z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }

    public Vector3(Vector2 vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = 0;
    }

    public void add(Vector3 vec) {
        x += vec.x;
        y += vec.y;
        z += vec.z;
    }

    public void subtract(Vector3 vec) {
        x -= vec.x;
        y -= vec.y;
        z -= vec.z;
    }

    public void multiply(Vector3 vec) {
        x *= vec.x;
        y *= vec.y;
        z *= vec.z;
    }

    public void divide(Vector3 vec) {
        x /= vec.x;
        y /= vec.y;
        z /= vec.z;
    }

    public void scale(float s) {
        x *= s;
        y *= s;
        z *= s;
    }

    public float norm() {
        return (float) Math.sqrt(normSquared());
    }

    public float normSquared() {
        return x * x + y * y + z * z;
    }

    public float dot(Vector3 vec) {
        return x * vec.x + y * vec.y + z * vec.z;
    }

    public Vector3 cross(Vector3 vec) {
        return new Vector3(
                y * vec.z - z * vec.y,
                z * vec.x - x * vec.z,
                x * vec.y - y * vec.x
        );
    }

    public Vector4 toHomogen() {
        return new Vector4(x, y, z, 1f);
    }
}
