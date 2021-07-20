package ch.zxseitz.j3de.utils;

import ch.zxseitz.j3de.math.Matrix4;
import ch.zxseitz.j3de.graphics.scene.Camera;
import ch.zxseitz.j3de.window.IWindowSizeListener;

class GraphicUtils private constructor() {
    companion object {
        fun createResizeListenerStdOrtho(camera: Camera): IWindowSizeListener {
            return IWindowSizeListener { width, height ->
                val aspect = width.toFloat() / height.toFloat();
                camera.projection = if (aspect > 1f) Matrix4.createOrthogonalProjection(
                    -1f * aspect, 1f * aspect, -1f, 1f, -1f, 100f
                ) else Matrix4.createOrthogonalProjection(
                    -1f, 1f, -1 / aspect, 1 / aspect, -1f, 100f
                )
            }
        }
    }
}
