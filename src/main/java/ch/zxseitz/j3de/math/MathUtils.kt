package ch.zxseitz.j3de.math;

import kotlin.math.abs

class MathUtils private constructor() {
    companion object {
        const val TOLERANCE: Float = 0.000001f;
        fun isFloatEquals(a: Float, b: Float): Boolean {
            return abs(a - b) < TOLERANCE;
        }
    }
}
