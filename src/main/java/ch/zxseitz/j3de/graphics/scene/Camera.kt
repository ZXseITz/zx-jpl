package ch.zxseitz.j3de.graphics.scene;

import ch.zxseitz.j3de.math.Matrix4;

data class Camera(var projection: Matrix4, var transformation: Matrix4 = Matrix4.ID) {
    companion object {
        val DEFAULT_ORTHOGONAL: Camera = Camera(Matrix4.STD_ORTHOGONAL_PROJECTION)
    }
}
