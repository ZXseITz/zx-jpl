package ch.zxseitz.j3de.graphics;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

import org.lwjgl.opengl.GL45.*;

class Texture(path: Path) {
    companion object {
        private val count: AtomicInteger = AtomicInteger(1)
    }

    val id: Int
    val width: Int
    val height: Int

    init {
        this.id = count.getAndIncrement()
        val img = ImageIO.read(path.toFile());
        this.width = img.width;
        this.height = img.height;
        val pixels = IntArray(width * height);
        img.getRGB(0, 0, width, height, pixels, 0, width);    //handles SRGB
        val buffer = BufferUtils.createByteBuffer(width * height * 4);
        for (y in height - 1 downTo 0) {
            for (x in 0 until width) {
                val pixel = pixels[y * width + x];
                buffer.put(((pixel shr 16) and 0xFF).toByte());     // red channel
                buffer.put(((pixel shr 8) and 0xFF).toByte());      // green channel
                buffer.put(((pixel shr 0) and 0xFF).toByte());      // blue channel
                buffer.put(((pixel shr 24) and 0xFF).toByte());     // alpha channel
            }
        }
        buffer.flip()

        val texId = glGenTextures();
        glActiveTexture(GL_TEXTURE0 + this.id);
        glBindTexture(GL_TEXTURE_2D, texId);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height,
                0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        glGenerateMipmap(GL_TEXTURE_2D);
    }
}
