package ch.zxseitz.j3de.graphics.scene;

import ch.zxseitz.j3de.exceptions.J3deException;
import ch.zxseitz.j3de.graphics.programs.Program;
import ch.zxseitz.j3de.math.Matrix4;

import java.util.ArrayList;
import java.util.List;

public class SceneGraph {
    private final List<SceneObj> nodes;
    private Camera camera;
    private Program program;

    public SceneGraph(Program program) {
        this.nodes = new ArrayList<>(25);
        this.camera = Camera.DEFAULT_ORTHOGONAL;

        //todo generalize
        this.program = program;
    }

    public List<SceneObj> getNodes() {
        return nodes;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     * Renders the entire scene graph
     */
    public void render() throws J3deException {
        //todo generalize
        program.writeUniform("P", camera.getProjection());
        for (SceneObj node : nodes) {
            render(camera.getTransformation(), node);
        }
    }

    private void render(Matrix4 transform, SceneObj node) throws J3deException {
        var t = transform.multiply(node.getTransformation());
        var mesh = node.getMesh();
        if (mesh != null) {
            //todo generalize
            program.writeUniform("T", t);
            mesh.getProgram().use();
            mesh.render();
        }
        for (SceneObj node1 : node.getChildren()) {
            render(t, node1);
        }
    }
}
