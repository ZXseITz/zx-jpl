package ch.zxseitz.j3de.math

import kotlin.math.cos
import kotlin.math.sin


/**
 * TODO: compare native impl
 */
data class Matrix4(
    val a11: Float, val a12: Float, val a13: Float, val a14: Float,
    val a21: Float, val a22: Float, val a23: Float, val a24: Float,
    val a31: Float, val a32: Float, val a33: Float, val a34: Float,
    val a41: Float, val a42: Float, val a43: Float, val a44: Float
) {
    companion object {
        val ID = Matrix4(
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f
        )

        val STD_ORTHOGONAL_PROJECTION = Matrix4.createOrthogonalProjection(-1f, 1f, -1f, 1f, -1f, 10f)

        fun createTranslation(x: Float, y: Float, z: Float): Matrix4 {
            return Matrix4(
                1f, 0f, 0f, x,
                0f, 1f, 0f, y,
                0f, 0f, 1f, z,
                0f, 0f, 0f, 1f
            )
        }

        fun createTranslation(vec: Vector3): Matrix4 {
            return Matrix4(
                1f, 0f, 0f, vec.x,
                0f, 1f, 0f, vec.y,
                0f, 0f, 1f, vec.z,
                0f, 0f, 0f, 1f
            )
        }

        fun createRotation(axis: Vector3, phi: Float): Matrix4 {
            val sin = sin(phi)
            val cos = cos(phi)
            val cos1 = 1 - cos
            val ax = axis.x
            val ay = axis.y
            val az = axis.z
            return Matrix4(
                cos1 * ax * ax + cos, cos1 * ax * ay - sin * az, cos1 * ax * az + sin * ay, 0f,
                cos1 * ay * ax + sin * az, cos1 * ay * ay + cos, cos1 * ay * az - sin * ax, 0f,
                cos1 * az * ax - sin * ay, cos1 * az * ay + sin * ax, cos1 * az * az + cos, 0f,
                0f, 0f, 0f, 1f
            )
        }

        fun createRotationX(phi: Float): Matrix4 {
            val sin = sin(phi)
            val cos = cos(phi)
            return Matrix4(
                1f, 0f, 0f, 0f,
                0f, cos, -sin, 0f,
                0f, sin, cos, 0f,
                0f, 0f, 0f, 1f
            )
        }

        fun createRotationY(phi: Float): Matrix4 {
            val sin = sin(phi)
            val cos = cos(phi)
            return Matrix4(
                cos, 0f, sin, 0f,
                0f, 1f, 0f, 0f,
                -sin, 0f, cos, 0f,
                0f, 0f, 0f, 1f
            )
        }

        fun createRotationZ(phi: Float): Matrix4 {
            val sin = sin(phi)
            val cos = cos(phi)
            return Matrix4(
                cos, -sin, 0f, 0f,
                sin, cos, 0f, 0f,
                0f, 0f, 1f, 0f,
                0f, 0f, 0f, 1f
            )
        }

        fun createScale(sx: Float, sy: Float, sz: Float): Matrix4 {
            return Matrix4(
                sx, 0f, 0f, 0f,
                0f, sy, 0f, 0f,
                0f, 0f, sz, 0f,
                0f, 0f, 0f, 1f
            )
        }

        fun createScale(s: Vector3): Matrix4 {
            return Matrix4(
                s.x, 0f, 0f, 0f,
                0f, s.y, 0f, 0f,
                0f, 0f, s.z, 0f,
                0f, 0f, 0f, 1f
            )
        }

        fun createOrthogonalProjection(left: Float, right: Float, bottom: Float,
                                       top: Float, near: Float, far: Float ): Matrix4 {
            val rl = 1f / (right - left)
            val tb = 1f / (top - bottom)
            val fn = 1f / (far - near)
            return Matrix4(
                2f * rl, 0f, 0f, -(right + left) * rl,
                0f, 2f * tb, 0f, -(top + bottom) * tb,
                0f, 0f, -2f * fn, -(far + near) * fn,
                0f, 0f, 0f, 1f
            )
        }

        fun createPerspectiveFieldOfView(fov: Float, aspect: Float, near: Float, far: Float): Matrix4? {
            //TODO: Implement
            //    return new Matrix4(
            //        0f, 0f, 0f, 0f,
            //        0f, 0f, 0f, 0f,
            //        0f, 0f, 0f, 0f,
            //        0f, 0f, 0f, 1f
            //    );
            return null
        }
    }

    operator fun plus(m: Matrix4): Matrix4 {
        return Matrix4(
            a11 + m.a11, a12 + m.a12, a13 + m.a13, a14 + m.a14,
            a21 + m.a21, a22 + m.a22, a23 + m.a23, a24 + m.a24,
            a31 + m.a31, a32 + m.a32, a33 + m.a33, a34 + m.a34,
            a41 + m.a41, a42 + m.a42, a43 + m.a43, a44 + m.a44
        )
    }

    operator fun minus(m: Matrix4): Matrix4 {
        return Matrix4(
            a11 - m.a11, a12 - m.a12, a13 - m.a13, a14 - m.a14,
            a21 - m.a21, a22 - m.a22, a23 - m.a23, a24 - m.a24,
            a31 - m.a31, a32 - m.a32, a33 - m.a33, a34 - m.a34,
            a41 - m.a41, a42 - m.a42, a43 - m.a43, a44 - m.a44
        )
    }

    operator fun times(m: Matrix4): Matrix4 {
        return Matrix4(
            a11 * m.a11 + a12 * m.a21 + a13 * m.a31 + a14 * m.a41,
            a11 * m.a12 + a12 * m.a22 + a13 * m.a32 + a14 * m.a42,
            a11 * m.a13 + a12 * m.a23 + a13 * m.a33 + a14 * m.a43,
            a11 * m.a14 + a12 * m.a24 + a13 * m.a34 + a14 * m.a44,
            a21 * m.a11 + a22 * m.a21 + a23 * m.a31 + a24 * m.a41,
            a21 * m.a12 + a22 * m.a22 + a23 * m.a32 + a24 * m.a42,
            a21 * m.a13 + a22 * m.a23 + a23 * m.a33 + a24 * m.a43,
            a21 * m.a14 + a22 * m.a24 + a23 * m.a34 + a24 * m.a44,
            a31 * m.a11 + a32 * m.a21 + a33 * m.a31 + a34 * m.a41,
            a31 * m.a12 + a32 * m.a22 + a33 * m.a32 + a34 * m.a42,
            a31 * m.a13 + a32 * m.a23 + a33 * m.a33 + a34 * m.a43,
            a31 * m.a14 + a32 * m.a24 + a33 * m.a34 + a34 * m.a44,
            a41 * m.a11 + a42 * m.a21 + a43 * m.a31 + a44 * m.a41,
            a41 * m.a12 + a42 * m.a22 + a43 * m.a32 + a44 * m.a42,
            a41 * m.a13 + a42 * m.a23 + a43 * m.a33 + a44 * m.a43,
            a41 * m.a14 + a42 * m.a24 + a43 * m.a34 + a44 * m.a44
        )
    }

    operator fun times(v: Vector4): Vector4 {
        return Vector4(
            a11 * v.x + a12 * v.y + a13 * v.z + a14 * v.w,
            a21 * v.x + a22 * v.y + a23 * v.z + a24 * v.w,
            a31 * v.x + a32 * v.y + a33 * v.z + a34 * v.w,
            a41 * v.x + a42 * v.y + a43 * v.z + a44 * v.w
        )
    }

    operator fun times(s: Float): Matrix4 {
        return Matrix4(
            a11 * s, a12 * s, a13 * s, a14 * s,
            a21 * s, a22 * s, a23 * s, a24 * s,
            a31 * s, a32 * s, a33 * s, a34 * s,
            a41 * s, a42 * s, a43 * s, a44 * s
        )
    }

    override fun toString(): String {
        return """
[
  %.3f, %.3f, %.3f, %.3f
  %.3f, %.3f, %.3f, %.3f
  %.3f, %.3f, %.3f, %.3f
  %.3f, %.3f, %.3f, %.3f
]
""".format(a11, a12, a13,  a14, a21, a22, a23, a24, a31, a32, a33, a34, a41, a42, a43, a44)
    }
}

infix fun Matrix4._times(m: Matrix4): Matrix4 {
    return Matrix4(
        a11 * m.a11, a12 * m.a12, a13 * m.a13, a14 * m.a14,
        a21 * m.a21, a22 * m.a22, a23 * m.a23, a24 * m.a24,
        a31 * m.a31, a32 * m.a32, a33 * m.a33, a34 * m.a34,
        a41 * m.a41, a42 * m.a42, a43 * m.a43, a44 * m.a44
    )
}
