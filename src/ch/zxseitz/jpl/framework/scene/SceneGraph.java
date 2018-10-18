package ch.zxseitz.jpl.framework.scene;

import ch.zxseitz.jpl.framework.config.Program;
import ch.zxseitz.jpl.framework.math.Matrix4;

import java.util.ArrayList;
import java.util.List;

public class SceneGraph {
    private final List<SceneObj> nodes;

    private Matrix4 projection;
    private Matrix4 camera;
    private Program p;

    public SceneGraph() {
        this.nodes = new ArrayList<>(25);
        this.projection = Matrix4.ID;
        this.camera = Matrix4.ID;
    }

    public List<SceneObj> getNodes() {
        return nodes;
    }

    public void render(Program p) {
        this.p = p;
        for (SceneObj node: nodes) {
            render(camera, node);
        }
    }

    private void render(Matrix4 transform, SceneObj node) {
        var t = new Matrix4(transform);
        t.multiply(node.getMatrix());

        var mesh = node.getMesh();
        if (mesh != null) {
            p.writeMat4("T", t);
            mesh.render();
        }
        for (SceneObj node1: nodes) {
            render(t, node1);
        }
    }
}
