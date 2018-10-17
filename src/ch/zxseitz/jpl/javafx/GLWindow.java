package ch.zxseitz.jpl.javafx;

import javafx.scene.image.*;
import javafx.scene.layout.Pane;

import java.nio.ByteBuffer;

public class GLWindow extends Pane {
  private static final PixelFormat<ByteBuffer> format = PixelFormat.getByteBgraInstance();

  public final int width, height;
  private final int stride;
  private final PixelWriter writer;
  private final byte[] buffer;

  public GLWindow(int width, int height) {
    this.width = width;
    this.height = height;
    this.stride = width * 4;
    this.buffer = new byte[width * height * 4];
    for (int i = 0; i < buffer.length; i++) {
      buffer[i] = (byte)255; // white
    }
    var image = new WritableImage(width, height);
    this.writer = image.getPixelWriter();
    this.getChildren().add(new ImageView(image));
  }

  public void write() {
    writer.setPixels(0, 0, width, height, format, buffer, 0, stride);
  }

  public void setPixel(int x, int y, HdrColor color) {
    if (x >= 0 && x < width && y >= 0 && y < height) {
      int idx = (x + y * width) * 4;
      buffer[idx] = color.getBlue();
      buffer[idx + 1] = color.getGreen();
      buffer[idx + 2] = color.getRed();
//      buffer[idx + 3] = 1; // alpha channel
    }
  }

  public void setPixels(int x, int y, int w, int h, HdrColor color) {
    int xw = x+w, yh = y+h;
    byte red = color.getRed();
    byte green = color.getGreen();
    byte blue = color.getBlue();
    if (x >= 0 && xw < width && y >= 0 && yh < height) {
      for (int v = y; v < yh; v++) {
        int xmin = (v * width + x) * 4;
        int xmax = (v * width + xw) * 4;
        for (int u = xmin; u < xmax; u += 4) {
          buffer[u] = blue;
          buffer[u + 1] = green;
          buffer[u + 2] = red;
//          buffer[u + 3] = 1; // alpha channel
        }
      }
    }
  }
}
