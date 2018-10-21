package ch.zxseitz.jpl.graphics;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import static org.lwjgl.opengl.GL45.*;

public class Texture {
    private static AtomicInteger count = new AtomicInteger();

    public final int id;
    private final int width, height;
    private final String path;

    public Texture(String path) {
        this.id = count.getAndIncrement();
        this.path = path;

        try {
            var file = new File(path);
            var img = ImageIO.read(file);
            this.width = img.getWidth();
            this.height = img.getHeight();
            var pixels = new int[width * height];
            img.getRGB(0, 0, width, height, pixels, 0, width);    //handles SRGB
            var buffer = BufferUtils.createByteBuffer(width * height * 4);
            for (var y = height - 1; y >= 0; y--) {
                for (var x = 0; x < width; x++) {
                    var pixel = pixels[y * width + x];
                    buffer.put((byte) ((pixel >> 16) & 0xFF));     // red channel
                    buffer.put((byte) ((pixel >> 8) & 0xFF));      // green channel
                    buffer.put((byte) (pixel & 0xFF));             // blue channel
                    buffer.put((byte) ((pixel >> 24) & 0xFF));     // alpha channel
                }
            }
            buffer.flip();

            var texId = glGenTextures();
            glActiveTexture(GL_TEXTURE0 + this.id);
            glBindTexture(GL_TEXTURE_2D, texId);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height,
                    0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
            glGenerateMipmap(GL_TEXTURE_2D);
        } catch (Exception e) {
            throw new RuntimeException("Cannot read image from " + path);
        }
    }

    public static Texture createTexture(String name) {
        return new Texture(String.format("res/textures/%s", name));
    }

    public String getPath() {
        return path;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
