package ch.zxseitz.j3de.graphics.scene.components;

import ch.zxseitz.j3de.graphics.scene.Actor;
import ch.zxseitz.j3de.math.Matrix4;

interface IComponent {
    fun update(delta: Double, actor: Actor, transform: Matrix4);
}
