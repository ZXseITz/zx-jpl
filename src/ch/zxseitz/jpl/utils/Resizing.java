package ch.zxseitz.jpl.utils;

import ch.zxseitz.jpl.framework.math.Matrix4;
import ch.zxseitz.jpl.framework.scene.Camera;
import javafx.beans.value.ChangeListener;

public abstract class Resizing {
    public static ChangeListener<Tuple<Integer, Integer>> createResizeListenerStdOrtho(Camera camera) {
        return (observable, oldValue, newValue) -> {
            var aspect = (float) newValue.getFirst() / (float) newValue.getSecond();
            if (aspect > 1f) {
                camera.setProjection(Matrix4.createOrthogonalProjection(-1f * aspect, 1f * aspect, -1f, 1f, -1f, 100f));
            } else {
                camera.setProjection(Matrix4.createOrthogonalProjection(-1f, 1f, -1 / aspect, 1 / aspect, -1f, 100f));
            }
        };
    }
}
