package ch.zxseitz.jpl;

import ch.zxseitz.jpl.framework.Application;
import ch.zxseitz.jpl.framework.config.Program;
import ch.zxseitz.jpl.framework.config.Texture;
import ch.zxseitz.jpl.framework.math.Matrix4;
import ch.zxseitz.jpl.framework.mesh.AbstractMesh;
import ch.zxseitz.jpl.framework.mesh.MeshFactory;
import ch.zxseitz.jpl.framework.mesh.MeshFactory2D;
import ch.zxseitz.jpl.framework.mesh.MeshTex;
import ch.zxseitz.jpl.framework.scene.SceneObj;
import ch.zxseitz.jpl.utils.Resizing;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public class SimpleTexture extends Application {
    public SimpleTexture() {
        super(800, 450, "SimpleTexture");
    }

    @Override
    protected void init() {
        //resizing
        size.addListener(Resizing.createResizeListenerStdOrtho(scene.getCamera()));

        //scene
        var pt = Program.createTexProgram();
        var meshFactoryTex = new MeshFactory2D(pt);
        var mesh = meshFactoryTex.createRectTex(2f, 2f, Color.WHITE,
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
