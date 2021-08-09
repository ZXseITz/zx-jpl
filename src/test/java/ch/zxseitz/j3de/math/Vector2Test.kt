package ch.zxseitz.j3de.math

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Vector2Test {
    @Test
    fun testEquals() {
        val a = Vector2(1f, 2f)
        val b = Vector2(1f, 2f)
        assertEquals(a, b)
    }

    @Test
    fun testToString() {
        val a = Vector2(1f, 2f)
        assertEquals("(1.000, 2.000)", a.toString())
    }

    @Test
    fun testAdd() {
        val a = Vector2(1f, 2f)
        val b = Vector2(3f, 4f)
        assertEquals(Vector2(4f, 6f), a + b)
    }

    @Test
    fun testSubtract() {
        val a = Vector2(1f, 2f)
        val b = Vector2(3f, 4f)
        assertEquals(Vector2(-2f, -2f), a - b)
    }

    @Test
    fun testMultiply() {
        val a = Vector2(1f, 2f)
        val b = Vector2(3f, 4f)
        assertEquals(Vector2(3f, 8f), a _times b)
    }
    @Test
    fun testScale() {
        val a = Vector2(1f, 2f)
        val s = 3f
        assertEquals(Vector2(3f, 6f), a * s)
    }

    @Test
    fun testNormSquared() {
        val a = Vector2(1f, 2f)
        assertEquals(5f, a.normSquared(), MathUtils.TOLERANCE)
    }

    @Test
    fun testNorm() {
        val a = Vector2(1f, 2f)
        assertEquals(2.2360679f, a.norm(), MathUtils.TOLERANCE)
    }

    @Test
    fun testDot() {
        val a = Vector2(1f, 2f)
        val b = Vector2(3f, 4f)
        assertEquals(11f, a dot b, MathUtils.TOLERANCE)
    }
}
