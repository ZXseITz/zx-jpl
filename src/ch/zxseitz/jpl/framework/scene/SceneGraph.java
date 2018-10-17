package ch.zxseitz.jpl.framework.scene;

import java.util.ArrayList;
import java.util.List;

public class SceneGraph {
    private final List<SceneObj> children;

    //TODO: Projection
    //TODO: Camera

    public SceneGraph() {
        children = new ArrayList<>(25);
    }

    public List<SceneObj> getChildren() {
        return children;
    }

    public void render() {
        //TODO: render, Transformation matrix
    }
}
