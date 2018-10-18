package ch.zxseitz.jpl;

import ch.zxseitz.jpl.framework.Application;
import ch.zxseitz.jpl.framework.config.Program;
import ch.zxseitz.jpl.framework.math.Matrix4;
import ch.zxseitz.jpl.framework.mesh.Mesh;
import ch.zxseitz.jpl.framework.scene.SceneObj;

public class Test extends Application {
  public Test() {
    super(800, 450, "Test");
  }

  @Override
  protected void init() {
    var p = new Program("simpleVertex", "simpleFragment");
    var mesh = new Mesh(p);
//    mesh.addAll();
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
