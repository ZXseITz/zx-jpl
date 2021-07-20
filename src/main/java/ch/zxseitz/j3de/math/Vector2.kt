package ch.zxseitz.j3de.math

import kotlin.math.sqrt

data class Vector2(val x : Float = 0f, val y: Float = 0f) {
    operator fun plus(v: Vector2): Vector2 {
        return Vector2(x + v.x, y + v.y)
    }
    operator fun minus(v: Vector2): Vector2 {
        return Vector2(x - v.x, y - v.y)
    }
    operator fun times(s: Float): Vector2 {
        return Vector2(x * s, y * s)
    }
    fun normSquared(): Float {
        return x * x + y * y
    }
    fun norm(): Float {
        return sqrt(normSquared())
    }
    fun normalize(): Vector2 {
        return this * (1f / norm())
    }

    override fun toString(): String {
        return "(%.3f, %.3f)".format(x, y)
    }
}
operator fun Vector2.unaryMinus() = Vector2(-x, -y)

infix fun Vector2._times(v: Vector2): Vector2 {
    return Vector2(x * v.x, y * v.y)
}
infix fun Vector2.dot(v: Vector2): Float {
    return x * v.x + y * v.y
}
