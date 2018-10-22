package ch.zxseitz.jpl;

import ch.zxseitz.jpl.graphics.Application;
import ch.zxseitz.jpl.graphics.mesh.PrimitiveType;
import ch.zxseitz.jpl.graphics.programs.Program;
import ch.zxseitz.jpl.math.Matrix4;
import ch.zxseitz.jpl.graphics.mesh.Mesh;
import ch.zxseitz.jpl.graphics.scene.SceneObj;
import ch.zxseitz.jpl.graphics.GraphicUtils;
import ch.zxseitz.jpl.utils.Triple;

import java.util.ArrayList;

public class SimpleTriangle extends Application {
    public SimpleTriangle() {
        super(450, 450, "SimpleTriangle");
    }

    @Override
    protected void init() {
        // resizing
        size.addListener(GraphicUtils.createResizeListenerStdOrtho(scene.getCamera()));

        // scene
        var mesh = new Mesh(Program.NOLIGHT);
        var vertices = new ArrayList<Triple<String, Integer, float[]>>(2);
        vertices.add(new Triple<>("pos", 3, new float[] {
                -1f, -1f, 0f,
                1f, -1f, 0f,
                1f, 1f, 0f
        }));
        vertices.add(new Triple<>("color", 4, new float[] {
                1f, 0f, 0f, 1f,
                0f, 1f, 0f, 1f,
                0f, 0f, 1f, 1f
        }));
        mesh.addAll(vertices, new int[] {
                0, 1, 2
        }, PrimitiveType.TRIANGLES);
        scene.getNodes().add(new SceneObj(mesh, Matrix4.createTranslation(0, 0, -5f)));
    }

    @Override
    protected void updateFrame() {

    }

    public static void main(String[] args) {
        (new SimpleTriangle()).run();
    }
}
