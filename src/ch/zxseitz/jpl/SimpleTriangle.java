package ch.zxseitz.jpl;

import ch.zxseitz.jpl.framework.Application;
import ch.zxseitz.jpl.framework.config.Program;
import ch.zxseitz.jpl.framework.math.Matrix4;
import ch.zxseitz.jpl.framework.mesh.AbstractMesh;
import ch.zxseitz.jpl.framework.mesh.Mesh;
import ch.zxseitz.jpl.framework.scene.SceneObj;

public class SimpleTriangle extends Application {
    public SimpleTriangle() {
        super(800, 450, "SimpleTriangle");
    }

    @Override
    protected void init() {
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
        scene.getCamera().setProjection(Matrix4.createOrthogonalProjection(-2f, 2f, (float) width / height, 1f, 100f));
        scene.getNodes().add(new SceneObj(mesh, Matrix4.createTranslation(0, 0, -5f)));
    }

    @Override
    protected void updateFrame() {

    }

    public static void main(String[] args) {
        (new SimpleTriangle()).run();
    }
}
