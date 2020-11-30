package ch.zxseitz.j3de.graphics.scene;

import ch.zxseitz.j3de.graphics.scene.components.IComponent;
import ch.zxseitz.j3de.math.Matrix4;

import java.util.ArrayList;
import java.util.List;

public class Actor {
    private final Scene scene;
    private final List<Actor> children;
    private final List<IComponent> components;
    private Matrix4 transformation;

    public Actor(Scene scene, Matrix4 matrix) {
        this.scene = scene;
        this.children = new ArrayList<>(10);
        this.components = new ArrayList<>(10);
        this.transformation = matrix;
    }

    public Scene getScene() {
        return scene;
    }

    public Matrix4 getTransformation() {
        return transformation;
    }

    public void setTransformation(Matrix4 transformation) {
        this.transformation = transformation;
    }

    public List<Actor> getChildren() {
        return children;
    }

    public List<IComponent> getComponents() {
        return components;
    }
}
