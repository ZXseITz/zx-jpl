package ch.zxseitz.jpl.graphics.scene;

import ch.zxseitz.jpl.graphics.programs.uniforms.UniformMatrix4;
import ch.zxseitz.jpl.math.Matrix4;
import ch.zxseitz.jpl.math.Vector4;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL45.*;

public class SceneGraph {
    private final List<SceneObj> nodes;
    private UniformMatrix4 T;

    /**
     * Constructor
     * @param t uniform transformation matrix
     */
    public SceneGraph(UniformMatrix4 t) {
        this.nodes = new ArrayList<>(25);
        this.T = t;
    }

    public List<SceneObj> getNodes() {
        return nodes;
    }

    /**
     * Renders the entire scene graph
     */
    public void render() {
        var camera = Camera.getCurrent();
        for (SceneObj node : nodes) {
            render(camera.getMatrix(), node);
        }
    }

    private void render(Matrix4 transform, SceneObj node) {
        var t = Matrix4.multiply(transform, node.getMatrix());
        var mesh = node.getMesh();
        if (mesh != null) {
            this.T.setValue(t);
            mesh.getProgram().use();
            mesh.render();
        }
        for (SceneObj node1 : node.getChildren()) {
            render(t, node1);
        }
    }
}
