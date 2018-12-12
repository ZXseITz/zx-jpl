package ch.zxseitz.jpl.graphics.scene;

import ch.zxseitz.jpl.graphics.programs.uniforms.UniformMatrix4;
import ch.zxseitz.jpl.math.Matrix4;
import ch.zxseitz.jpl.math.Vector4;

import static org.lwjgl.opengl.GL45.glClearColor;

public class Camera {
    private static Camera current = null;

    public static Camera getCurrent() {
        return current;
    }

    private UniformMatrix4 P;
    private Matrix4 projection;
    private Matrix4 matrix;
    private Vector4 background; //x red, y green, z blue

    public Camera(UniformMatrix4 p) {
        this.P = p;
        this.projection = Matrix4.createOrthogonalProjection(-1f, 1f, -1f, 1f, -1f, 100f);
        this.matrix = Matrix4.ID;
        this.background = Vector4.ONE;
    }

    public Matrix4 getProjection() {
        return projection;
    }

    public void setProjection(Matrix4 projection) {
        this.projection = projection;
//        if (current == this) P.setValue(projection);
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

    public void use() {
        glClearColor(background.x, background.y, background.z, background.w);
        P.setValue(projection);
        current = this;
    }
}
