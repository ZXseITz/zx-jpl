package ch.zxseitz.jpl.graphics.scene;

import ch.zxseitz.jpl.math.Matrix4;
import ch.zxseitz.jpl.math.Vector3;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL45.*;

public class SceneGraph {
    private final List<SceneObj> nodes;
    private Vector3 lightPos; //TODO: improve
    private Color ambient; //TODO: improve
    private Camera camera; //TODO: multiple cams

    public SceneGraph() {
        this.nodes = new ArrayList<>(25);
        this.camera = new Camera();
        this.lightPos = new Vector3(5f, 10f, 20f);
        this.ambient = new Color(0.1, 0.1, 0.1, 1.);
    }

    public Camera getCamera() {
        return camera;
    }

    public Vector3 getLightPos() {
        return lightPos;
    }

    public void setLightPos(Vector3 lightPos) {
        this.lightPos = lightPos;
    }

    public Color getAmbient() {
        return ambient;
    }

    public void setAmbient(Color ambient) {
        this.ambient = ambient;
    }

    public List<SceneObj> getNodes() {
        return nodes;
    }

    public void render() {
        Color bg = camera.getBackground();
        glClearColor((float) bg.getRed(), (float) bg.getGreen(), (float) bg.getBlue(), 0f);
        for (SceneObj node : nodes) {
            render(camera.getMatrix(), node);
        }
    }

    private void render(Matrix4 transform, SceneObj node) {
        var t = Matrix4.multiply(transform, node.getMatrix());
        var mesh = node.getMesh();
        if (mesh != null) {
            mesh.getProgram().use();
            mesh.getProgram().writeUniforms(this, t, mesh.getTex());
            mesh.render();
        }
        for (SceneObj node1 : node.getChildren()) {
            render(t, node1);
        }
    }
}
