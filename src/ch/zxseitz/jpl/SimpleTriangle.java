package ch.zxseitz.jpl;

import ch.zxseitz.jpl.graphics.Application;
import ch.zxseitz.jpl.graphics.Program;
import ch.zxseitz.jpl.math.Matrix4;
import ch.zxseitz.jpl.graphics.mesh.AbstractMesh;
import ch.zxseitz.jpl.graphics.mesh.Mesh;
import ch.zxseitz.jpl.graphics.scene.SceneObj;
import ch.zxseitz.jpl.graphics.GraphicUtils;

public class SimpleTriangle extends Application {
    public SimpleTriangle() {
        super(800, 450, "SimpleTriangle");
    }

    @Override
    protected void init() {
        // resizing
        size.addListener(GraphicUtils.createResizeListenerStdOrtho(scene.getCamera()));

        // scene
        var p = new Program("res/shaders/vertexShader.glsl", "res/shaders/fragmentShader.glsl");
        var mesh = new Mesh(p);
        mesh.addAll(new float[][]{{
                -1f, -1f, 0f,
                1f, -1f, 0f,
                1f, 1f, 0f
        }, {
                0f, 0f, 1f,
                0f, 0f, 1f,
                0f, 0f, 1f
        }, {
                1f, 0f, 0f, 1f,
                0f, 1f, 0f, 1f,
                0f, 0f, 1f, 1f
        } }, new int[] {
                0, 1, 2
        }, AbstractMesh.PrimitiveType.TRIANGLES);
        var aspect = 16f/9f;
        scene.getCamera().setProjection(Matrix4.createOrthogonalProjection(-1f * aspect, 1f * aspect, -1f, 1f, -1f, 100f));
        scene.getNodes().add(new SceneObj(mesh, Matrix4.createTranslation(0, 0, -5f)));
    }

    @Override
    protected void updateFrame() {

    }

    public static void main(String[] args) {
        (new SimpleTriangle()).run();
    }
}
