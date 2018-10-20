package ch.zxseitz.jpl.framework.scene;

import ch.zxseitz.jpl.framework.math.Matrix4;
import ch.zxseitz.jpl.framework.mesh.Mesh;

import java.util.ArrayList;
import java.util.List;

public class SceneObj {
    private Mesh mesh;
    private Matrix4 matrix;

    private final List<SceneObj> children;

    public SceneObj(Mesh mesh, Matrix4 matrix) {
        this.children = new ArrayList<>(10);
        this.matrix = matrix;
        this.mesh = mesh;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public Matrix4 getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix4 matrix) {
        this.matrix = matrix;
    }

    public List<SceneObj> getChildren() {
        return children;
    }
}
