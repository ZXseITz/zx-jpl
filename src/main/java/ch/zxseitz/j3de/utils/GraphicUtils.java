package ch.zxseitz.j3de.utils;

import ch.zxseitz.j3de.math.Matrix4;
import ch.zxseitz.j3de.graphics.scene.Camera;
import ch.zxseitz.j3de.windows.IWindowSizeListener;

public final class GraphicUtils {
    private GraphicUtils() {}

    public static IWindowSizeListener createResizeListenerStdOrtho(Camera camera) {
        return (width, height) -> {
            var aspect = (float) width / (float) height;
            if (aspect > 1f) {
                camera.setProjection(Matrix4.createOrthogonalProjection(-1f * aspect, 1f * aspect, -1f, 1f, -1f, 100f));
            } else {
                camera.setProjection(Matrix4.createOrthogonalProjection(-1f, 1f, -1 / aspect, 1 / aspect, -1f, 100f));
            }
        };
    }
}
