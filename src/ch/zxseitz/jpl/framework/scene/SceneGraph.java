package ch.zxseitz.jpl.framework.scene;

import ch.zxseitz.jpl.framework.config.Program;
import ch.zxseitz.jpl.framework.math.Matrix4;

import java.util.ArrayList;
import java.util.List;

public class SceneGraph {
    private final List<SceneObj> nodes;

    private Camera camera;

    public SceneGraph() {
        this.nodes = new ArrayList<>(25);
        this.camera = new Camera();
    }

    public Camera getCamera() {
        return camera;
    }

    public List<SceneObj> getNodes() {
        return nodes;
    }

    public void render() {
        for (SceneObj node: nodes) {
            render(camera.getMatrix(), node);
        }
    }

    private void render(Matrix4 transform, SceneObj node) {
        var t = Matrix4.multiply(transform, node.getMatrix());
        var mesh = node.getMesh();
        if (mesh != null) {
            Program p = mesh.getProgram();
            p.writeMat4("P", camera.getProjection());
            p.writeMat4("T", t);
            mesh.render();
        }
        for (SceneObj node1: nodes) {
            render(t, node1);
        }
    }
}
