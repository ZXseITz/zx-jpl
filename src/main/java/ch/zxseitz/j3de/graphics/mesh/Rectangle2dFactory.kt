package ch.zxseitz.j3de.graphics.mesh;

import ch.zxseitz.j3de.graphics.core.PrimitiveType;

class Rectangle2dFactory {
    val mode: PrimitiveType = PrimitiveType.TRIANGLE_FAN
    val indices: IntArray = intArrayOf(0, 1, 2, 3)
    val uv: FloatArray = floatArrayOf(
        0f, 0f,
        0f, 1f,
        1f, 1f,
        1f, 0f,
    )

    fun getPos3(width: Float, height: Float): FloatArray {
        val x = width / 2;
        val y = height / 2;
        return floatArrayOf(
            -x, -y, 0f,
            x, -y, 0f,
            x, y, 0f,
            -x, y, 0f
        );
    }
}
