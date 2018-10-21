package ch.zxseitz.jpl.physics;

import ch.zxseitz.jpl.math.Vector2;

/**
 * Newton  system
 *
 * @author Claudio Seitz
 * @version 1.1
 */
public abstract class NewtonSystem2D extends Dynamics {
    /**
     * @param pos start position
     * @param v   velocity
     */
    public NewtonSystem2D(Vector2 pos, Vector2 v) {
        state = new float[]{pos.x, pos.y, v.x, v.y};
    }

    public void move(float dt) {
        rungeKutta(dt);
    }
    
    public void move(float dt, boolean euler) {
        if (!euler) {
            rungeKutta(dt);
        } else {
            euler(dt);
        }
    }

    public Vector2 getPos() {
        return new Vector2(state[0], state[1]);
    }

    public Vector2 getVelocity() {
        return new Vector2(state[2], state[2]);
    }
}
