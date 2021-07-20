package ch.zxseitz.j3de.math;

import org.junit.Assert
import org.junit.Test

class Vector4Test {
    @Test
    fun testEquals() {
        val a = Vector4(1f, 2f, 3f, 4f)
        val b = Vector4(1f, 2f, 3f, 4f)

        Assert.assertEquals(a, b)
    }

    @Test
    fun testToString() {
        val a = Vector4(1f, 2f, 3f, 4f)

        Assert.assertEquals("(1.000, 2.000, 3.000, 4.000)", a.toString())
    }

    @Test
    fun testAdd() {
        val a = Vector4(1f, 2f, 3f, 4f)
        val b = Vector4(5f, 6f, 7f, 8f)

        Assert.assertEquals(Vector4(6f, 8f, 10f, 12f), a + b)
    }

    @Test
    fun testSubtract() {
        val a = Vector4(1f, 2f, 3f, 4f)
        val b = Vector4(5f, 6f, 7f, 8f)

        Assert.assertEquals(Vector4(-4f, -4f, -4f, -4f), a - b)
    }

    @Test
    fun testMultiply() {
        val a = Vector4(1f, 2f, 3f, 4f)
        val b = Vector4(5f, 6f, 7f, 8f)

        Assert.assertEquals(Vector4(5f, 12f, 21f, 32f), a _times b)
    }

    @Test
    fun testScale() {
        val a = Vector4(1f, 2f, 3f, 4f)
        val s = 5f

        Assert.assertEquals(Vector4(5f, 10f, 15f, 20f), a * s)
    }

    @Test
    fun testNormSquared() {
        val a = Vector4(1f, 2f, 3f, 4f)

        Assert.assertEquals(30f, a.normSquared(), MathUtils.TOLERANCE)
    }

    @Test
    fun testNorm() {
        val a = Vector4(1f, 2f, 3f, 4f)

        Assert.assertEquals(5.477226f, a.norm(), MathUtils.TOLERANCE)
    }

    @Test
    fun testPerspectiveDivision() {
        val a = Vector4(16f, 8f, 12f, 4f)

        Assert.assertEquals(Vector3(4f, 2f, 3f), a.persDiv())
    }
}
