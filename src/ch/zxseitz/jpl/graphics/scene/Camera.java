package ch.zxseitz.jpl.graphics.scene;

import ch.zxseitz.jpl.math.Matrix4;
import ch.zxseitz.jpl.math.Vector4;

public class Camera {
    private Matrix4 projection;
    private Matrix4 matrix;
    private Vector4 background; //x red, y green, z blue

    public Camera() {
        this(Matrix4.createOrthogonalProjection(-1f, 1f, -1f, 1f, -1f, 100f),
                Matrix4.ID, new Vector4(0.7f, 0.7f, 0.7f, 1f));
    }

    public Camera(Matrix4 projection, Matrix4 matrix, Vector4 background) {
        this.projection = projection;
        this.matrix = matrix;
        this.background = background;
    }

    public Matrix4 getProjection() {
        return projection;
    }

    public void setProjection(Matrix4 projection) {
        this.projection = projection;
    }

    public Matrix4 getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix4 matrix) {
        this.matrix = matrix;
    }

    public Vector4 getBackground() {
        return background;
    }

    public void setBackground(Vector4 background) {
        this.background = background;
    }
}
