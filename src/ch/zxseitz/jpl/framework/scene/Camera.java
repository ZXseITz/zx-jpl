package ch.zxseitz.jpl.framework.scene;

import ch.zxseitz.jpl.framework.math.Matrix4;

public class Camera {
  private Matrix4 projection;
  private Matrix4 matrix;

  public Camera() {
    this.projection = Matrix4.ID;
    this.matrix = Matrix4.ID;
  }

  public Camera(Matrix4 projection, Matrix4 matrix) {
    this.projection = projection;
    this.matrix = matrix;
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
}
