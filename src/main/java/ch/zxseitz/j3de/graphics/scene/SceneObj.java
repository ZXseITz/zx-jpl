package ch.zxseitz.j3de.graphics.scene;

import ch.zxseitz.j3de.math.Matrix4;
import ch.zxseitz.j3de.graphics.mesh.Mesh;

import java.util.ArrayList;
import java.util.List;

public class SceneObj {
    private final List<SceneObj> children;
    private Mesh mesh;
    private Matrix4 transformation;

    public SceneObj(Mesh mesh, Matrix4 matrix) {
        this.children = new ArrayList<>(10);
        this.transformation = matrix;
        this.mesh = mesh;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public Matrix4 getTransformation() {
        return transformation;
    }

    public void setTransformation(Matrix4 transformation) {
        this.transformation = transformation;
    }

    public List<SceneObj> getChildren() {
        return children;
    }
}
