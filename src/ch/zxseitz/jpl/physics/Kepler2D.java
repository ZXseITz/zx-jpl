package ch.zxseitz.jpl.physics;

import ch.zxseitz.jpl.math.Vector2;

/**
 * @author Claudio Seitz
 * @version 1.1
 */
public class Kepler2D extends NewtonSystem2D {
    private float GM;

    /**
     *
     * @param pos start position
     * @param v velocity
     * @param GM gravitational constant
     */
    public Kepler2D(Vector2 pos, Vector2 v, float GM) {
        super (pos, v);
        this.GM = GM;
    }

    @Override
    public float[] getField(float[] state) {
        float x = state[0], y = state[1];
        float vx = state[2], vy = state[3];
        float r3 = (float) Math.pow(x*x + y*y, 3./2);

        return new float[] {
                vx,
                vy,
                -GM / r3 * x,
                -GM / r3 * y
        };
    }
}
