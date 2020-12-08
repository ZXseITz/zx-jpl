package ch.zxseitz.j3de.graphics.mesh;

import ch.zxseitz.j3de.graphics.core.PrimitiveType;

public class Rectangle2dFactory {
    private final PrimitiveType mode;
    private final int[] indices;
    private final float[] uv;

    public Rectangle2dFactory() {
        this.mode = PrimitiveType.TRIANGLE_FAN;
        this.indices = new int[] {
                0, 1, 2, 3
        };
        this.uv = new float[] {
                0f, 0f,
                0f, 1f,
                1f, 1f,
                1f, 0f,
        };
    }

    public PrimitiveType getMode() {
        return mode;
    }

    public int[] getIndices() {
        return indices;
    }

    public float[] getPos3(float width, float height) {
        var x = width / 2;
        var y = height / 2;
        return new float[] {
                -x, -y, 0f,
                x, -y, 0f,
                x, y, 0f,
                -x, y, 0f
        };
    }

    public float[] getUv2() {
        return uv;
    }
}
