package ch.zxseitz.j3de.math;

import org.junit.Assert
import org.junit.Test

class Vector3Test {
    @Test
    fun testEquals() {
        val a = Vector3(1f, 2f, 3f)
        val b = Vector3(1f, 2f, 3f)

        Assert.assertEquals(a, b)
    }

    @Test
    fun testToString() {
        val a = Vector3(1f, 2f, 3f)

        Assert.assertEquals("(1.000, 2.000, 3.000)", a.toString())
    }

    @Test
    fun testAdd() {
        val a = Vector3(1f, 2f, 3f)
        val b = Vector3(4f, 5f, 6f)

        Assert.assertEquals(Vector3(5f, 7f, 9f), a + b)
    }

    @Test
    fun testSubtract() {
        val a = Vector3(1f, 2f, 3f)
        val b = Vector3(4f, 5f, 6f)
        Assert.assertEquals(Vector3(-3f, -3f, -3f), a - b)
    }

    @Test
    fun testElementMultiply() {
        val a = Vector3(1f, 2f, 3f)
        val b = Vector3(4f, 5f, 6f)

        Assert.assertEquals(Vector3(4f, 10f, 18f), a _times b)
    }

    @Test
    fun testScale() {
        val a = Vector3(1f, 2f, 3f)
        val s = 4f

        Assert.assertEquals(Vector3(4f, 8f, 12f), a * s)
    }

    @Test
    fun testNormSquared() {
        val a = Vector3(1f, 2f, 3f)

        Assert.assertEquals(14f, a.normSquared(), MathUtils.TOLERANCE)
    }

    @Test
    fun testNorm() {
        val a = Vector3(1f, 2f, 3f)

        Assert.assertEquals(3.741657f, a.norm(), MathUtils.TOLERANCE)
    }

    @Test
    fun testDot() {
        val a = Vector3(1f, 2f, 3f)
        val b = Vector3(4f, 5f, 6f)

        Assert.assertEquals(32f, a dot b, MathUtils.TOLERANCE)
    }

    @Test
    fun testCross() {
        val a = Vector3(1f, 2f, 3f)
        val b = Vector3(4f, 5f, 6f)

        Assert.assertEquals(Vector3(-3f, 6f, -3f), a cross b)
    }

    @Test
    fun testToHomogenous() {
        val a = Vector3(1f, 2f, 3f)

        Assert.assertEquals(Vector4(1f, 2f, 3f, 1f), a.toHomogen())
    }
}
