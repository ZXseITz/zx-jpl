package ch.zxseitz.jpl.framework.math;

import java.util.Arrays;

public class Matrix4 {
  //TODO: native functions using jni
  public static final Matrix4 ID = new Matrix4(
      1, 0, 0, 0,
      0, 1, 0, 0,
      0, 0, 1, 0,
      0, 0, 0, 1);

  private static final int SIZE = 16;

  public final float[] data;

  public Matrix4(float a11, float a12, float a13, float a14,
                 float a21, float a22, float a23, float a24,
                 float a31, float a32, float a33, float a34,
                 float a41, float a42, float a43, float a44) {
    data = new float[]{
        a11, a12, a13, a14,
        a21, a22, a23, a24,
        a31, a32, a33, a34,
        a41, a42, a43, a44
    };
  }

  public Matrix4(float[] data) {
    if (data.length != SIZE) throw new RuntimeException("Invalid Matrix size");
    this.data = data;
  }

  public Matrix4(Matrix4 mat) {
    this.data = Arrays.copyOf(mat.data, SIZE);
  }

  public void add(Matrix4 mat) {
    for (int i = 0; i < SIZE; i++) {
      data[i] += mat.data[i];
    }
  }

  public void subtract(Matrix4 mat) {
    for (int i = 0; i < SIZE; i++) {
      data[i] -= mat.data[i];
    }
  }

  public void multiplyElements(Matrix4 mat) {
    for (int i = 0; i < SIZE; i++) {
      data[i] *= mat.data[i];
    }
  }

  public void multiplyLeft(Matrix4 mat) {
    for (int i = 0; i < SIZE; i++) {
      float value = 0;
      int r = i / 4;
      int c = i % 4;
      for (int x = 0; x < 4; x++) {
        value += mat.data[r * 4 + x] * data[c + x * 4];
      }
      data[i] = value;
    }
  }

  public void multiply(Matrix4 mat) {
    for (int i = 0; i < SIZE; i++) {
      float value = 0;
      int r = i / 4;
      int c = i % 4;
      for (int x = 0; x < 4; x++) {
        value += data[r * 4 + x] * mat.data[c + x * 4];
      }
      data[i] = value;
    }
  }
}
