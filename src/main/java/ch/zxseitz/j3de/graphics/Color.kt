package ch.zxseitz.j3de.graphics;

import ch.zxseitz.j3de.math.Vector4;
import java.lang.Float.min

data class Color(val r: Float, val g: Float, val b: Float, val a: Float = 1f) {
    companion object {
        val WHITE = Color(1f, 1f, 1f)
        val BLACK = Color(0f, 0f, 0f)
        val RED = Color(1f, 0f, 0f)
        val GREEN = Color(0f, 1f, 0f)
        val BLUE = Color(0f, 0f, 1f)
        val YELLOW = Color(1f, 1f, 0f)
        val MARGENTA = Color(1f, 0f, 1f)
        val CYAN = Color(0f, 1f, 1f)

        private fun clamp(value: Float): Float {
            return if (value < 0f) 0f else min(value, 1f);
        }
    }

    fun clamp(): Color {
        return Color(clamp(r), clamp(g), clamp(b), a);
    }

    fun toVector4(): Vector4 {
        return Vector4(r, g, b, a);
    }

    //todo arithmetic handle alpha channel correctly

    operator fun plus(color: Color): Color {
        return Color(r + color.r, g + color.g, b + color.b, a + color.a)
    }

    operator fun minus(color: Color): Color {
        return Color(r - color.r, g - color.g, b - color.b, a - color.a)
    }

    operator fun times(color: Color): Color {
        return Color(r * color.r, g * color.g, b * color.b, a * color.a)
    }
}
