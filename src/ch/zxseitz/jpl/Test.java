package ch.zxseitz.jpl;

import ch.zxseitz.jpl.framework.Application;
import ch.zxseitz.jpl.framework.config.Program;
import ch.zxseitz.jpl.framework.math.Matrix4;
import ch.zxseitz.jpl.framework.mesh.Mesh;
import ch.zxseitz.jpl.framework.scene.SceneObj;
import javafx.util.Pair;

public class Test extends Application {
  public Test() {
    super(800, 450, "Test");
  }

  @Override
  protected void init() {
    var p = new Program("res/shaders/vertexShader.glsl", "res/shaders/fragmentShader.glsl");
    var mesh = new Mesh(p);
    mesh.addAll(new Pair[]{
        new Pair(new Pair("pos", 3), new float[] {
            -1f, -1f, 0f,
            1f, -1f, 0f,
            1f, 1f, 0f
        }),
        new Pair(new Pair("normal", 3), new float[] {
            0f, 0f, 1f,
            0f, 0f, 1f,
            0f, 0f, 1f
        }),
        new Pair(new Pair("color", 4), new float[] {
            1f, 0f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 0f, 1f, 1f
        })
    }, new int[] {
        0, 1, 2
    }, Mesh.PrimitiveType.TRIANGLES);
    scene.getCamera().setProjection(Matrix4.createOrthogonalProjection(-2f, 2f, (float) width/height, 1f, 100f));
    scene.getNodes().add(new SceneObj(mesh, Matrix4.createTranslation(0,0,-5f)));
  }

  @Override
  protected void updateFrame() {
  }

  public static void main(String[] args) {
    (new Test()).run();
  }
}
