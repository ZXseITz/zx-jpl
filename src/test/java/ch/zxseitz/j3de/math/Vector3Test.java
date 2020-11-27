package ch.zxseitz.j3de.math;

import org.junit.Assert;
import org.junit.Test;

public class Vector3Test {
    @Test
    public void testEquals() {
        var a = new Vector3(1f, 2f, 3f);
        var b = new Vector3(1f, 2f, 3f);

        Assert.assertEquals(a, b);
    }

    @Test
    public void testToString() {
        var a = new Vector3(1f, 2f, 3f);

        Assert.assertEquals("(1.000, 2.000, 3.000)", a.toString());
    }

    @Test
    public void testAdd() {
        var a = new Vector3(1f, 2f, 3f);
        var b = new Vector3(4f, 5f, 6f);

        Assert.assertEquals(new Vector3(5f, 7f, 9f), a.add(b));
    }

    @Test
    public void testSubtract() {
        var a = new Vector3(1f, 2f, 3f);
        var b = new Vector3(4f, 5f, 6f);

        Assert.assertEquals(new Vector3(-3f, -3f, -3f), a.subtract(b));
    }

    @Test
    public void testMultiply() {
        var a = new Vector3(1f, 2f, 3f);
        var b = new Vector3(4f, 5f, 6f);

        Assert.assertEquals(new Vector3(5f, 7f, 9f), a.add(b));
    }

    @Test
    public void testDivide() {
        var a = new Vector3(1f, 2f, 3f);
        var b = new Vector3(4f, 5f, 6f);

        Assert.assertEquals(new Vector3(1f / 4f, 2f / 5f, 1f / 2f), a.divide(b));
    }

    @Test
    public void testScale() {
        var a = new Vector3(1f, 2f, 3f);
        var s = 4f;

        Assert.assertEquals(new Vector3(4f, 8f, 12f), a.scale(s));
    }

    @Test
    public void testNormSquared() {
        var a = new Vector3(1f, 2f, 3f);

        Assert.assertEquals(14f, a.normSquared(), MathUtils.TOLERANCE);
    }

    @Test
    public void testNorm() {
        var a = new Vector3(1f, 2f, 3f);

        Assert.assertEquals(3.741657f, a.norm(), MathUtils.TOLERANCE);
    }

    @Test
    public void testDot() {
        var a = new Vector3(1f, 2f, 3f);
        var b = new Vector3(4f, 5f, 6f);

        Assert.assertEquals(32f, a.dot(b), MathUtils.TOLERANCE);
    }

    @Test
    public void testCross() {
        var a = new Vector3(1f, 2f, 3f);
        var b = new Vector3(4f, 5f, 6f);

        Assert.assertEquals(new Vector3(-3f, 6f, -3f), a.cross(b));
    }

    @Test
    public void testToHomogenous() {
        var a = new Vector3(1f, 2f, 3f);

        Assert.assertEquals(new Vector4(1f, 2f, 3f, 1f), a.toHomogenous());
    }
}
