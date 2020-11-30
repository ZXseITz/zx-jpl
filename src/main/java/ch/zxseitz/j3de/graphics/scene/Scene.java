package ch.zxseitz.j3de.graphics.scene;

import ch.zxseitz.j3de.exceptions.J3deException;
import ch.zxseitz.j3de.graphics.programs.Program;
import ch.zxseitz.j3de.math.Matrix4;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private final List<Actor> actors;
    private Camera camera;
    private Program program;

    public Scene(Program program) {
        this.actors = new ArrayList<>(25);
        this.camera = Camera.DEFAULT_ORTHOGONAL;

        //todo generalize
        this.program = program;
    }

    public Program getProgram() {
        return program;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     * Renders the entire scene graph
     */
    public void update(double delta) throws J3deException {
        for (Actor actor : actors) {
            update(delta, actor, camera.getTransformation());
        }
    }

    private void update(double delta, Actor actor, Matrix4 transform) throws J3deException {
        var t = transform.multiply(actor.getTransformation());
        for (var component : actor.getComponents()) {
            component.update(delta, actor, t);
        }
        for (Actor child : actor.getChildren()) {
            update(delta, child, t);
        }
    }
}
