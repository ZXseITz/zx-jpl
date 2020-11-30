package ch.zxseitz.j3de.math;

import org.junit.Assert;
import org.junit.Test;

public class Vector2Test {
    @Test
    public void testEquals() {
        var a = new Vector2(1f, 2f);
        var b = new Vector2(1f, 2f);

        Assert.assertEquals(a, b);
    }

    @Test
    public void testToString() {
        var a = new Vector2(1f, 2f);

        Assert.assertEquals("(1.000, 2.000)", a.toString());
    }

    @Test
    public void testAdd() {
        var a = new Vector2(1f, 2f);
        var b = new Vector2(3f, 4f);

        Assert.assertEquals(new Vector2(4f, 6f), a.add(b));
    }

    @Test
    public void testSubtract() {
        var a = new Vector2(1f, 2f);
        var b = new Vector2(3f, 4f);

        Assert.assertEquals(new Vector2(-2f, -2f), a.subtract(b));
    }

    @Test
    public void testMultiply() {
        var a = new Vector2(1f, 2f);
        var b = new Vector2(3f, 4f);

        Assert.assertEquals(new Vector2(3f, 8f), a.multiply(b));
    }

    @Test
    public void testDivide() {
        var a = new Vector2(1f, 2f);
        var b = new Vector2(3f, 4f);

        Assert.assertEquals(new Vector2(1f / 3f, 1f / 2f), a.divide(b));
    }

    @Test
    public void testScale() {
        var a = new Vector2(1f, 2f);
        var s = 3f;

        Assert.assertEquals(new Vector2(3f, 6f), a.scale(s));
    }

    @Test
    public void testNormSquared() {
        var a = new Vector2(1f, 2f);

        Assert.assertEquals(5f, a.normSquared(), MathUtils.TOLERANCE);
    }

    @Test
    public void testNorm() {
        var a = new Vector2(1f, 2f);

        Assert.assertEquals(2.2360679f, a.norm(), MathUtils.TOLERANCE);
    }

    @Test
    public void testDot() {
        var a = new Vector2(1f, 2f);
        var b = new Vector2(3f, 4f);

        Assert.assertEquals(11f, a.dot(b), MathUtils.TOLERANCE);
    }
}
