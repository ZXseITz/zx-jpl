package ch.zxseitz.j3de.math

import kotlin.math.sqrt

data class Vector4(val x: Float = 0f, val y: Float = 0f, val z: Float = 0f, val w: Float = 0f) {
    companion object {
        val ZERO = Vector4()
        val ONE = Vector4(1f, 1f, 1f, 1f)
    }

    constructor(v: Vector2, z: Float = 0f, w: Float = 0f): this(v.x, v.y, z, w)

    constructor(v: Vector3, w: Float = 0f): this(v.x, v.y, v.z, w)

    operator fun plus(v: Vector4): Vector4 {
        return Vector4(x + v.x, y + v.y, z + v.z, w + v.w)
    }

    operator fun minus(v: Vector4): Vector4 {
        return Vector4(x - v.x, y - v.y, z - v.z, w -v.w)
    }

    operator fun times(s: Float): Vector4 {
        return Vector4(x * s, y * s, z * s, w * s)
    }

    fun normSquared(): Float {
        return x * x + y * y + z * z + w * w
    }

    fun norm(): Float {
        return sqrt(normSquared())
    }

    fun normalize(): Vector4 {
        return this * (1f / norm())
    }

    /**
     * Perspective division
     */
    fun persDiv(): Vector3 {
        val dw = 1 / w
        return Vector3(x * dw, y * dw, z * dw)
    }

    override fun toString(): String {
        return  "(%.3f, %.3f, %.3f, %.3f)".format(x, y, z, w)
    }
}

operator fun Vector4.unaryMinus() = Vector4(-x, -y, -z, -w)

infix fun Vector4._times(v: Vector4): Vector4 {
    return Vector4(x * v.x, y * v.y, z * v.z, w * v.w)
}

infix fun Vector4.dot(v: Vector4): Float {
    return x * v.x + y * v.y + z * v.z + w * v.w
}

infix fun Vector4.cross(v: Vector4): Vector4 {
    return Vector4(
        y * v.z - z * v.y,
        z * v.x - x * v.z,
        x * v.y - y * v.x
    )
}
