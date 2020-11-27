package ch.zxseitz.j3de.graphics.scene;

import ch.zxseitz.j3de.graphics.programs.uniforms.UniformMatrix4;
import ch.zxseitz.j3de.math.Matrix4;

import java.util.ArrayList;
import java.util.List;

public class SceneGraph {
    private final List<SceneObj> nodes;
    private final UniformMatrix4 uProjection;
    private final UniformMatrix4 uTransformation;
    private Camera camera;

    /**
     * Constructor
     * @param uProjection uniform projection matrix
     * @param uTransformation uniform transformation matrix
     */
    public SceneGraph(UniformMatrix4 uProjection, UniformMatrix4 uTransformation) {
        this.nodes = new ArrayList<>(25);
        this.uProjection = uProjection;
        this.uTransformation = uTransformation;
        this.camera = Camera.DEFAULT_ORTHOGONAL;
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
    public void render() {
        this.uProjection.setValue(camera.getProjection());
        for (SceneObj node : nodes) {
            render(camera.getTransformation(), node);
        }
    }

    private void render(Matrix4 transform, SceneObj node) {
        var t = transform.multiply(node.getTransformation());
        var mesh = node.getMesh();
        if (mesh != null) {
            this.uTransformation.setValue(t);
            mesh.getProgram().use();
            mesh.render();
        }
        for (SceneObj node1 : node.getChildren()) {
            render(t, node1);
        }
    }
}
