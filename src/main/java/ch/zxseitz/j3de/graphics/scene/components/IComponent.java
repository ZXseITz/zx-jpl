package ch.zxseitz.j3de.graphics.scene.components;

import ch.zxseitz.j3de.exceptions.J3deException;
import ch.zxseitz.j3de.graphics.scene.Actor;
import ch.zxseitz.j3de.math.Matrix4;

public interface IComponent {
    void update(double delta, Actor actor, Matrix4 transform) throws J3deException;
}
