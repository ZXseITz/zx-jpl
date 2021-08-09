package ch.zxseitz.j3de.math;

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Matrix4Test {
    @Test
    fun testEquals() {
        val A = Matrix4(1f, 2f, 3f, 4f,
                5f, 6f, 7f, 8f,
                9f, 10f, 11f, 12f,
                13f, 14f, 15f, 16f)

        val B = Matrix4(1f, 2f, 3f, 4f,
                5f, 6f, 7f, 8f,
                9f, 10f, 11f, 12f,
                13f, 14f, 15f, 16f)

        assertEquals(A, B)
    }

    @Test
    fun testFalseEquals() {
        val A = Matrix4(1f, 2f, 3f, 4f,
                5f, 6f, 7f, 8f,
                9f, 10f, 11f, 12f,
                13f, 14f, 15f, 16f)

        val B = Matrix4(1f, 2f, 3f, 4f,
                5f, 6f, 7f, 8f,
                9f, 10f, 0f, 12f,
                13f, 14f, 15f, 16f)

        assertNotEquals(A, B);
    }

    @Test
    fun testToString() {
        val A = Matrix4(1f, 2f, 3f, 4f,
                5f, 6f, 7f, 8f,
                9f, 10f, 11f, 12f,
                13f, 14f, 15f, 16f);

        assertEquals("""
[
  1.000, 2.000, 3.000, 4.000
  5.000, 6.000, 7.000, 8.000
  9.000, 10.000, 11.000, 12.000
  13.000, 14.000, 15.000, 16.000
]
""", A.toString());
    }

    @Test
    fun testAdd() {
        val A = Matrix4(
                4f, 8f, 3f, 1f,
                15f, 6f, 7f, 12f,
                9f, 2f, 11f, 9f,
                5f, 3f, 7f, 2f
        )

        val B = Matrix4(
                7f, 10f, 3f, 14f,
                11f, 6f, 7f, 13f,
                1f, 4f, 15f, 5f,
                12f, 8f, 2f, 9f
        )

        assertEquals(Matrix4(
                11f, 18f, 6f, 15f,
                26f, 12f, 14f, 25f,
                10f, 6f, 26f, 14f,
                17f, 11f, 9f, 11f
        ), A + B);
    }

    @Test
    fun testSubtract() {
        val A = Matrix4(
                4f, 8f, 3f, 1f,
                15f, 6f, 7f, 12f,
                9f, 2f, 11f, 9f,
                5f, 3f, 7f, 2f
        )

        val B = Matrix4(
                7f, 10f, 3f, 14f,
                11f, 6f, 7f, 13f,
                1f, 4f, 15f, 5f,
                12f, 8f, 2f, 9f
        )

        assertEquals(Matrix4(
                -3f, -2f, 0f, -13f,
                4f, 0f, 0f, -1f,
                8f, -2f, -4f, 4f,
                -7f, -5f, 5f, -7f
        ), A - B)
    }

    @Test
    fun testMultiply() {
        val A = Matrix4(
                4f, 8f, 3f, 1f,
                15f, 6f, 7f, 12f,
                9f, 2f, 11f, 9f,
                5f, 3f, 7f, 2f
        )

        val B = Matrix4(
                7f, 10f, 3f, 14f,
                11f, 6f, 7f, 13f,
                1f, 4f, 15f, 5f,
                12f, 8f, 2f, 9f
        )

        assertEquals(Matrix4(
                131f, 108f, 115f, 184f,
                322f, 310f, 216f, 431f,
                204f, 218f, 224f, 288f,
                99f, 112f, 145f, 162f
        ), A * B)
    }

    @Test
    fun testMultiplyElements() {
        val A = Matrix4(
                4f, 8f, 3f, 1f,
                15f, 6f, 7f, 12f,
                9f, 2f, 11f, 9f,
                5f, 3f, 7f, 2f
        )

        val B = Matrix4(
                7f, 10f, 3f, 14f,
                11f, 6f, 7f, 13f,
                1f, 4f, 15f, 5f,
                12f, 8f, 2f, 9f
        );

        assertEquals(Matrix4(
                28f, 80f, 9f, 14f,
                165f, 36f, 49f, 156f,
                9f, 8f, 165f, 45f,
                60f, 24f, 14f, 18f
        ), A _times B)
    }
}
