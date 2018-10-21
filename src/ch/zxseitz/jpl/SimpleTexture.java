package ch.zxseitz.jpl;

import ch.zxseitz.jpl.framework.Application;
import ch.zxseitz.jpl.framework.config.Program;
import ch.zxseitz.jpl.framework.config.Texture;
import ch.zxseitz.jpl.framework.math.Matrix4;
import ch.zxseitz.jpl.framework.mesh.AbstractMesh;
import ch.zxseitz.jpl.framework.mesh.MeshTex;
import ch.zxseitz.jpl.framework.scene.SceneObj;
import javafx.util.Pair;

public class SimpleTexture extends Application {
    public SimpleTexture() {
        super(800, 450, "SimpleTexture");
    }

    @Override
    protected void init() {
        var p = new Program("res/shaders/vertexShaderTex.glsl", "res/shaders/fragmentShaderTex.glsl");
        var mesh = new MeshTex(p, Texture.createTexture("freebies.jpg"));
        mesh.addAll(new float[][]{{
                -1f, -1f, 0f,
                1f, -1f, 0f,
                1f, 1f, 0f,
                -1f, 1f, 0f
        }, {
                0f, 0f, 1f,
                0f, 0f, 1f,
                0f, 0f, 1f,
                0f, 0f, 1f
        }, {
                1f, 1f, 1f, 1f,
                1f, 1f, 1f, 1f,
                1f, 1f, 1f, 1f,
                1f, 1f, 1f, 1f,
        }, {
                0f, 0f,
                1f, 0f,
                1f, 1f,
                0f, 1f
        }}, new int[]{
                0, 1, 2, 3
        }, AbstractMesh.PrimitiveType.TRIANGLE_FAN);
        scene.getCamera().setProjection(Matrix4.createOrthogonalProjection(-2f, 2f, (float) width / height, 1f, 100f));
        scene.getNodes().add(new SceneObj(mesh, Matrix4.createTranslation(0, 0, -5f)));
    }

    @Override
    protected void updateFrame() {

    }

    public static void main(String[] args) {
        (new SimpleTexture()).run();
    }
}
