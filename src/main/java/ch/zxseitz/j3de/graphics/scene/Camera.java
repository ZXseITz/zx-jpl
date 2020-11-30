package ch.zxseitz.j3de.graphics.scene;

import ch.zxseitz.j3de.math.Matrix4;

public class Camera {
    public static Camera DEFAULT_ORTHOGONAL = new Camera(Matrix4.STD_ORTHOGONAL_PROJECTION);

    private Matrix4 projection;
    private Matrix4 transformation;

    public Camera(Matrix4 projection) {
        this(projection, Matrix4.ID);
    }

    public Camera(Matrix4 projection, Matrix4 transformation) {
        this.projection = projection;
        this.transformation = transformation;
    }

    public Matrix4 getProjection() {
        return projection;
    }

    public void setProjection(Matrix4 projection) {
        this.projection = projection;
    }

    public Matrix4 getTransformation() {
        return transformation;
    }

    public void setTransformation(Matrix4 transformation) {
        this.transformation = transformation;
    }
}
