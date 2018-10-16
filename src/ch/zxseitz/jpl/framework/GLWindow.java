package ch.zxseitz.jpl.framework;

import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.nio.ByteBuffer;

public class GLWindow extends ImageView {
  private static final PixelFormat<ByteBuffer> format = PixelFormat.getByteBgraInstance();

  public final int width, height;
  private final PixelWriter writer;
  private final byte[] buffer;

  public GLWindow(int width, int height) {
    this.width = width;
    this.height = height;
    this.buffer = new byte[width * height * 4];
    WritableImage image = new WritableImage(width, height);
    this.writer = image.getPixelWriter();
    this.setImage(image);
  }

  private static byte toByte(double d) {
    return (byte) (d <= 0 ? 0 : d >= 1 ? 255 : Math.round(d * 255));
  }

  public void write() {
    writer.setPixels(0, 0, width, height, format, buffer, 0, width * 4);
  }

  public void setPixel(int x, int y, Color color) {
    if (x >= 0 && x < width && y >= 0 && y < height) {
      int idx = (x + y * width) * 4;
      buffer[idx] = toByte(color.getBlue());
      buffer[idx + 1] = toByte(color.getGreen());
      buffer[idx + 2] = toByte(color.getRed());
      buffer[idx + 3] = toByte(color.getOpacity());
    }
  }

  public void setPixels(int x, int y, int w, int h, Color color) {
    int xw = x+w, yh = y+h;
    if (x >= 0 && xw < width && y >= 0 && yh < height) {
      for (int v = y; v < yh; v++) {
        int xmin = (v * width + x) * 4;
        int xmax = (v * width + xw) * 4;
        for (int u = xmin; u < xmax; u += 4) {
          buffer[u] = toByte(color.getBlue());
          buffer[u + 1] = toByte(color.getGreen());
          buffer[u + 2] = toByte(color.getRed());
          buffer[u + 3] = toByte(color.getOpacity());
        }
      }
    }
  }
}
