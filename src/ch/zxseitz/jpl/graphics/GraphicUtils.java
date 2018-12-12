package ch.zxseitz.jpl.graphics;

import ch.zxseitz.jpl.math.Matrix4;
import ch.zxseitz.jpl.graphics.scene.Camera;

public final class GraphicUtils {
    private GraphicUtils() {}

    public static ISizeChanged createResizeListenerStdOrtho(Camera camera) {
        return (width, height) -> {
            var aspect = (float) width / (float) height;
            if (aspect > 1f) {
                camera.setProjection(Matrix4.createOrthogonalProjection(-1f * aspect, 1f * aspect, -1f, 1f, -1f, 100f));
            } else {
                camera.setProjection(Matrix4.createOrthogonalProjection(-1f, 1f, -1 / aspect, 1 / aspect, -1f, 100f));
            }
            camera.use();
        };
    }
}
