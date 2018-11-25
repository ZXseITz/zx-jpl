package ch.zxseitz.jpl;

import ch.zxseitz.jpl.graphics.Application;
import ch.zxseitz.jpl.graphics.Texture;
import ch.zxseitz.jpl.graphics.mesh.MeshFactory;
import ch.zxseitz.jpl.graphics.programs.Program;
import ch.zxseitz.jpl.math.Matrix4;
import ch.zxseitz.jpl.graphics.scene.SceneObj;
import ch.zxseitz.jpl.graphics.GraphicUtils;
import javafx.scene.paint.Color;

public class SimpleTexture extends Application {
    public SimpleTexture() {
        super(800, 450, "SimpleTexture");
    }

    @Override
    protected void init() {
        //resizing
        size.addListener(GraphicUtils.createResizeListenerStdOrtho(scene.getCamera()));

        //scene
        var factory = MeshFactory.getFactory(Program.NORMAL_TEX);
        var mesh = factory.createRect2D(2f, 2f, Color.WHITE,
                Texture.createTexture("freebies.jpg"));
        var aspect = 16f/9f;
        scene.getCamera().setProjection(Matrix4.createOrthogonalProjection(-1f * aspect, 1f * aspect, -1f, 1f, -1f, 100f));
        scene.getNodes().add(new SceneObj(mesh, Matrix4.createTranslation(0, 0, -5f)));
    }

    @Override
    protected void updateFrame() {

    }

    public static void main(String[] args) {
        (new SimpleTexture()).run();
    }
}
