package ch.zxseitz.j3de.math;

import org.junit.Assert;
import org.junit.Test;

public class Vector4Test {
    @Test
    public void testEquals() {
        var a = new Vector4(1f, 2f, 3f, 4f);
        var b = new Vector4(1f, 2f, 3f, 4f);

        Assert.assertEquals(a, b);
    }

    @Test
    public void testToString() {
        var a = new Vector4(1f, 2f, 3f, 4f);

        Assert.assertEquals("(1.000, 2.000, 3.000, 4.000)", a.toString());
    }

    @Test
    public void testAdd() {
        var a = new Vector4(1f, 2f, 3f, 4f);
        var b = new Vector4(5f, 6f, 7f, 8f);

        Assert.assertEquals(new Vector4(6f, 8f, 10f, 12f), a.add(b));
    }

    @Test
    public void testSubtract() {
        var a = new Vector4(1f, 2f, 3f, 4f);
        var b = new Vector4(5f, 6f, 7f, 8f);

        Assert.assertEquals(new Vector4(-4f, -4f, -4f, -4f), a.subtract(b));
    }

    @Test
    public void testMultiply() {
        var a = new Vector4(1f, 2f, 3f, 4f);
        var b = new Vector4(5f, 6f, 7f, 8f);

        Assert.assertEquals(new Vector4(5f, 12f, 21f, 32f), a.multiply(b));
    }

    @Test
    public void testDivide() {
        var a = new Vector4(1f, 2f, 3f, 4f);
        var b = new Vector4(5f, 6f, 7f, 8f);

        Assert.assertEquals(new Vector4(1f / 5f, 1f / 3f, 3f / 7f, 1f / 2f), a.divide(b));
    }

    @Test
    public void testScale() {
        var a = new Vector4(1f, 2f, 3f, 4f);
        var s = 5f;

        Assert.assertEquals(new Vector4(5f, 10f, 15f, 20f), a.scale(s));
    }

    @Test
    public void testNormSquared() {
        var a = new Vector4(1f, 2f, 3f, 4f);

        Assert.assertEquals(30f, a.normSquared(), MathUtils.TOLERANCE);
    }

    @Test
    public void testNorm() {
        var a = new Vector4(1f, 2f, 3f, 4f);

        Assert.assertEquals(5.477226f, a.norm(), MathUtils.TOLERANCE);
    }

    @Test
    public void testPerspectiveDivision() {
        var a = new Vector4(16f, 8f, 12f, 4f);

        Assert.assertEquals(new Vector3(4f, 2f, 3f), a.perspectivDivision());
    }
}
