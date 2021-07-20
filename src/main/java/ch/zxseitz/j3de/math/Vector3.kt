package ch.zxseitz.j3de.math

import kotlin.math.sqrt

data class Vector3(val x: Float = 0f, val y: Float = 0f, val z: Float = 0f) {
    companion object {
        val ZERO = Vector3()
        val ONE = Vector3(1f, 1f, 1f)
        val XY = Vector3(1f, 1f, 0f)
        val XZ = Vector3(1f, 0f, 1f)
        val YZ = Vector3(0f, 1f, 1f)
        val X = Vector3(1f, 0f, 0f)
        val Y = Vector3(0f, 1f, 0f)
        val Z = Vector3(0f, 0f, 1f)
    }

    constructor(v: Vector2, z: Float = 0f) : this(v.x, v.y, z)

    operator fun plus(v: Vector3): Vector3 {
        return Vector3(x + v.x, y + v.y, z + v.z)
    }

    operator fun minus(v: Vector3): Vector3 {
        return Vector3(x - v.x, y - v.y, z - v.z)
    }

    operator fun times(s: Float): Vector3 {
        return Vector3(x * s, y * s, z * s)
    }

    fun normSquared(): Float {
        return x * x + y * y + z * z
    }

    fun norm(): Float {
        return sqrt(normSquared())
    }

    fun normalize(): Vector3 {
        return this * (1f / norm())
    }

    fun toHomogen(): Vector4 {
        return Vector4(this, 1f)
    }

    override fun toString(): String {
        return "(%.3f, %.3f, %.3f)".format(x, y, z)
    }
}

operator fun Vector3.unaryMinus() = Vector3(-x, -y, -z)

infix fun Vector3._times(v: Vector3): Vector3 {
    return Vector3(x * v.x, y * v.y, z * v.z)
}

infix fun Vector3.dot(v: Vector3): Float {
    return x * v.x + y * v.y + z * v.z
}

infix fun Vector3.cross(v: Vector3): Vector3 {
    return Vector3(
        y * v.z - z * v.y,
        z * v.x - x * v.z,
        x * v.y - y * v.x
    )
}
