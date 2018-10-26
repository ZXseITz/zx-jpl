package ch.zxseitz.jpl.math;

import java.util.Arrays;

public class Matrix4 {
    static {
        System.load(System.getProperty("user.dir") + "/libs/Math.dll");
    }

    public static final Matrix4 ID = new Matrix4(
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1);

    private static final int SIZE = 16;

    private static native void addC(float[] a, float[] b, float[] r);
    private static native void subtractC(float[] a, float[] b, float[] r);
    private static native void multiplyC(float[] a, float[] b, float[] r);
    private static native void multiplyElementsC(float[] a, float[] b, float[] r);
    private static native void multiplyScalarC(float[] a, float b, float[] r);

    public float[] data;

    public Matrix4(float a11, float a12, float a13, float a14,
                   float a21, float a22, float a23, float a24,
                   float a31, float a32, float a33, float a34,
                   float a41, float a42, float a43, float a44) {
        data = new float[]{
                a11, a12, a13, a14,
                a21, a22, a23, a24,
                a31, a32, a33, a34,
                a41, a42, a43, a44
        };
    }

    public Matrix4() {
        this.data = new float[SIZE];
    }

    public Matrix4(float[] data) {
        if (data.length != SIZE) throw new RuntimeException("Invalid Matrix size");
        this.data = data;
    }

    public Matrix4(Matrix4 mat) {
        this.data = Arrays.copyOf(mat.data, SIZE);
    }

    public void add(Matrix4 mat) {
        float[] r = new float[SIZE];
        addC(data, mat.data, r);
        data = r;
    }

    public void subtract(Matrix4 mat) {
        float[] r = new float[SIZE];
        subtractC(data, mat.data, r);
        data = r;
    }

    public void multiply(Matrix4 mat) {
        float[] r = new float[SIZE];
        multiplyC(data, mat.data, r);
        data = r;
    }

    public void multiplyElements(Matrix4 mat) {
        float[] r = new float[SIZE];
        multiplyElementsC(data, mat.data, r);
        data = r;
    }

    public void multiplyScalar(float s) {
        float[] r = new float[SIZE];
        multiplyScalarC(data, s, r);
        data = r;
    }

    public static Matrix4 add(Matrix4 a, Matrix4 b) {
        float[] r = new float[SIZE];
        addC(a.data, b.data, r);
        return new Matrix4(r);
    }

    public static Matrix4 subtract(Matrix4 a, Matrix4 b) {
        float[] r = new float[SIZE];
        subtractC(a.data, b.data, r);
        return new Matrix4(r);
    }

    public static Matrix4 multiply(Matrix4 a, Matrix4 b) {
        float[] r = new float[SIZE];
        multiplyC(a.data, b.data, r);
        return new Matrix4(r);
    }

    public static Matrix4 multiplyElements(Matrix4 a, Matrix4 b) {
        float[] r = new float[SIZE];
        multiplyElementsC(a.data, b.data, r);
        return new Matrix4(r);
    }

    public static Matrix4 multiplyScalar(Matrix4 a, float s) {
        float[] r = new float[SIZE];
        multiplyScalarC(a.data, s, r);
        return new Matrix4(r);
    }

    public static Matrix4 createTranslation(float x, float y, float z) {
        return new Matrix4(
                1f, 0f, 0f, x,
                0f, 1f, 0f, y,
                0f, 0f, 1f, z,
                0f, 0f, 0f, 1f
        );
    }

    public static Matrix4 createTranslation(Vector3 vec) {
        return new Matrix4(
                1f, 0f, 0f, vec.x,
                0f, 1f, 0f, vec.y,
                0f, 0f, 1f, vec.z,
                0f, 0f, 0f, 1f
        );
    }

    public static Matrix4 createRotation(Vector3 axis, float phi) {
        var sin = (float) Math.sin(phi);
        var cos = (float) Math.cos(phi);
        var cos1 = (1 - cos);
        var ax = axis.x;
        var ay = axis.y;
        var az = axis.z;
        return new Matrix4(
                cos1 * ax * ax + cos, cos1 * ax * ay - sin * az, cos1 * ax * az + sin * ay, 0f,
                cos1 * ay * ax + sin * az, cos1 * ay * ay + cos, cos1 * ay * az - sin * ax, 0f,
                cos1 * az * ax - sin * ay, cos1 * az * ay + sin * ax, cos1 * az * az + cos, 0f,
                0f, 0f, 0f, 1f
        );
    }

    public static Matrix4 createRotationX(float phi) {
        var sin = (float) Math.sin(phi);
        var cos = (float) Math.cos(phi);
        return new Matrix4(
                1f, 0f, 0f, 0f,
                0f, cos, -sin, 0f,
                0f, sin, cos, 0f,
                0f, 0f, 0f, 1f
        );
    }

    public static Matrix4 createRotationY(float phi) {
        var sin = (float) Math.sin(phi);
        var cos = (float) Math.cos(phi);
        return new Matrix4(
                cos, 0f, sin, 0f,
                0f, 1f, 0f, 0f,
                -sin, 0f, cos, 0f,
                0f, 0f, 0f, 1f
        );
    }

    public static Matrix4 createRotationZ(float phi) {
        var sin = (float) Math.sin(phi);
        var cos = (float) Math.cos(phi);
        return new Matrix4(
                cos, -sin, 0f, 0f,
                sin, cos, 0f, 0f,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f
        );
    }

    public static Matrix4 createScale(float sx, float sy, float sz) {
        return new Matrix4(
                sx, 0f, 0f, 0f,
                0f, sy, 0f, 0f,
                0f, 0f, sz, 0f,
                0f, 0f, 0f, 1f
        );
    }

    public static Matrix4 createScale(Vector3 s) {
        return new Matrix4(
                s.x, 0f, 0f, 0f,
                0f, s.y, 0f, 0f,
                0f, 0f, s.z, 0f,
                0f, 0f, 0f, 1f
        );
    }

    public static Matrix4 createOrthogonalProjection(float left, float right, float bottom, float top, float near, float far) {
        var rl = 1f / (right - left);
        var tb = 1f / (top - bottom);
        var fn = 1f / (far - near);
        return new Matrix4(
                2f * rl, 0f, 0f, -(right + left) * rl,
                0f, 2f * tb, 0f, -(top + bottom) * tb,
                0f, 0f, -2f * fn, -(far + near) * fn,
                0f, 0f, 0f, 1f
        );
    }

    public static Matrix4 createPerspectiveFieldOfView(float fov, float aspect, float near, float far) {
        //TODO: Implement
//    return new Matrix4(
//        0f, 0f, 0f, 0f,
//        0f, 0f, 0f, 0f,
//        0f, 0f, 0f, 0f,
//        0f, 0f, 0f, 1f
//    );
        return null;
    }
}
