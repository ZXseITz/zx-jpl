package ch.zxseitz.jpl.framework.scene;

import ch.zxseitz.jpl.framework.config.Program;
import ch.zxseitz.jpl.framework.math.Matrix4;
import ch.zxseitz.jpl.framework.math.Vector3;
import ch.zxseitz.jpl.framework.mesh.MeshTex;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL45.*;

public class SceneGraph {
    private final List<SceneObj> nodes;
    private Vector3 lightPos; //TODO: improve
    private Color ambient; //TODO: improve
    private Camera camera; //TODO: multiple cams

    private int programId = -1;

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
        for (SceneObj node: nodes) {
            render(camera.getMatrix(), node);
        }
    }

    private void render(Matrix4 transform, SceneObj node) {
        var t = Matrix4.multiply(transform, node.getMatrix());
        var mesh = node.getMesh();
        if (mesh != null) {
            Program p = mesh.getProgram();
            if (programId != p.id) {
                p.use();
                p.writeMat4("P", camera.getProjection());
                p.writeVec4("ambient", ambient);
                p.writeVec3("l_pos", lightPos);

                programId = p.id;
            }
            if (mesh instanceof MeshTex) {
                p.writeTexture("tex", ((MeshTex) mesh).getTexture());
            }
            p.writeMat4("T", t);
            mesh.render();
        }
        for (SceneObj node1: node.getChildren()) {
            render(t, node1);
        }
    }
}
