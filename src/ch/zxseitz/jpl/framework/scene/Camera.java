package ch.zxseitz.jpl.framework.scene;

import ch.zxseitz.jpl.framework.math.Matrix4;
import javafx.scene.paint.Color;

public class Camera {
  private Matrix4 projection;
  private Matrix4 matrix;
  private Color background; //x red, y green, z blue

  public Camera() {
    this(Matrix4.createOrthogonalProjection(-1f, 1f, -1f, 1f, -1f, 100f), Matrix4.ID, Color.LIGHTGREY);
  }

  public Camera(Matrix4 projection, Matrix4 matrix, Color background) {
    this.projection = projection;
    this.matrix = matrix;
    this.background = background;
  }

  public Matrix4 getProjection() {
    return projection;
  }

  public void setProjection(Matrix4 projection) {
    this.projection = projection;
  }

  public Matrix4 getMatrix() {
    return matrix;
  }

  public void setMatrix(Matrix4 matrix) {
    this.matrix = matrix;
  }

  public Color getBackground() {
    return background;
  }

  public void setBackground(Color background) {
    this.background = background;
  }
}
