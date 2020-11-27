package ch.zxseitz.j3de.math;

public class Vector4 {
    public static final Vector4 ZERO = new Vector4(0f, 0f, 0f, 0f);
    public static final Vector4 ONE = new Vector4(1f, 1f, 1f, 1f);

    public final float x, y, z, w;

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

    public Vector4 add(Vector4 vec) {
        return new Vector4(
                x + vec.x,
                y + vec.y,
                z + vec.z,
                w + vec.w
        );
    }

    public Vector4 subtract(Vector4 vec) {
        return new Vector4(
                x - vec.x,
                y - vec.y,
                z - vec.z,
                w - vec.w
        );
    }

    public Vector4 multiply(Vector4 vec) {
        return new Vector4(
                x * vec.x,
                y * vec.y,
                z * vec.z,
                w * vec.w
        );
    }

    public Vector4 divide(Vector4 vec) {
        return new Vector4(
                x / vec.x,
                y / vec.y,
                z / vec.z,
                w / vec.w
        );
    }

    public Vector4 scale(float s) {
        return new Vector4(
                x * s,
                y * s,
                z * s,
                w * s
        );
    }

    public float norm() {
        return (float) Math.sqrt(normSquared());
    }

    public float normSquared() {
        return x * x + y * y + z * z + w * w;
    }

    public Vector3 perspectivDivision() {
        float dw = 1 / w;
        return new Vector3(x * dw, y * dw, z * dw);
    }

    @Override
    public boolean equals(Object obj) {
//        if (obj instanceof Vector4 vec) {
        if (obj instanceof Vector4) {
            var vec = (Vector4) obj;
            return MathUtils.isFloatEquals(x, vec.x) && MathUtils.isFloatEquals(y, vec.y)
                    && MathUtils.isFloatEquals(z, vec.z) && MathUtils.isFloatEquals(w, vec.w);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("(%.3f, %.3f, %.3f, %.3f)", x, y, z, w);
    }
}
