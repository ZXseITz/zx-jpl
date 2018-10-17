package ch.zxseitz.jpl.framework.scene;

import java.util.ArrayList;
import java.util.List;

public class SceneObj {
    private Object mesh;
    private Object matrix;

    private final List<SceneObj> children;

    public SceneObj(Object mesh, Object matrix) {
        this.children = new ArrayList<>(10);
        this.matrix = matrix;
        this.mesh = mesh;
    }

    public Object getMesh() {
        return mesh;
    }

    public void setMesh(Object mesh) {
        this.mesh = mesh;
    }

    public Object getMatrix() {
        return matrix;
    }

    public void setMatrix(Object matrix) {
        this.matrix = matrix;
    }

    public List<SceneObj> getChildren() {
        return children;
    }
}
