package ch.zxseitz.j3de.math;

public class Vector4 {
    public static final Vector4 ZERO = new Vector4(0f, 0f, 0f, 0f);
    public static final Vector4 ONE = new Vector4(1f, 1f, 1f, 1f);

    public float x, y, z, w;

    public Vector4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4(Vector4 vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
        this.w = vec.w;
    }

    public Vector4(Vector3 vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
        this.w = 0;
    }

    public void add(Vector4 vec) {
        x += vec.x;
        y += vec.y;
        z += vec.z;
        w += vec.w;
    }

    public void subtract(Vector4 vec) {
        x -= vec.x;
        y -= vec.y;
        z -= vec.z;
        w -= vec.w;
    }

    public void multiply(Vector4 vec) {
        x *= vec.x;
        y *= vec.y;
        z *= vec.z;
        w *= vec.w;
    }

    public void divide(Vector4 vec) {
        x /= vec.x;
        y /= vec.y;
        z /= vec.z;
        w /= vec.w;
    }

    public void scale(float s) {
        x *= s;
        y *= s;
        z *= s;
        w *= s;
    }

    public Vector3 persDiv() {
        float dw = 1 / w;
        return new Vector3(x * dw, y * dw, z * dw);
    }

    public float norm() {
        return (float) Math.sqrt(normSquared());
    }

    public float normSquared() {
        return x * x + y * y;
    }
}
