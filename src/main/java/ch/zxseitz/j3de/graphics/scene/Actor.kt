package ch.zxseitz.j3de.graphics.scene;

import ch.zxseitz.j3de.graphics.scene.components.IComponent;
import ch.zxseitz.j3de.math.Matrix4;

import java.util.ArrayList;

data class Actor(val scene: Scene, var transformation: Matrix4) {
    val children: MutableList<Actor> = ArrayList()
    val components: MutableList<IComponent> = ArrayList()
}
