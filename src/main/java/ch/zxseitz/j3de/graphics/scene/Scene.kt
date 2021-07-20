package ch.zxseitz.j3de.graphics.scene;

import ch.zxseitz.j3de.graphics.core.Program;
import ch.zxseitz.j3de.math.Matrix4;

class Scene(val program: Program) {
    val actors: MutableList<Actor> = ArrayList(25);
    var camera: Camera = Camera.DEFAULT_ORTHOGONAL;

    /**
     * Renders the entire scene graph
     */
    fun update(delta: Double) {
        for (actor in actors) {
            update(delta, actor, camera.transformation);
        }
    }

    private fun update(delta: Double, actor: Actor, transform: Matrix4) {
        val t = transform * actor.transformation
        for (component in actor.components) {
            component.update(delta, actor, t);
        }
        for (child in actor.children) {
            update(delta, child, t);
        }
    }
}
