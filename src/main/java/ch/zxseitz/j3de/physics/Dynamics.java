package ch.zxseitz.j3de.physics;

/**
 * @author Claudio Seitz
 * @version 1.1
 */
public abstract class Dynamics {
    protected float[] state;
    /**
     * Creates a vector getField of a given state
     *
     * @param state current state
     * @return vector getField
     */
    protected abstract float[] getField(float[] state);

    /**
     * @param dt    delta time
     */
    protected void euler(float dt) {
        float[] vecField = getField(state);
        for (int i = 0; i < state.length; i++) {
            state[i] += vecField[i] * dt;
        }
    }

    /**
     * @param dt    delta time
     */
    protected void rungeKutta(float dt) {
        int n = state.length;
        float[] newState = new float[n];
        float[][] vecfields = new float[5][n];
        vecfields[0] = getField(state);
        for (int i = 0; i < n; i++) {
            newState[i] = state[i] + vecfields[0][i] * dt / 2;
        }
        vecfields[1] = getField(newState);
        for (int i = 0; i < n; i++) {
            newState[i] = state[i] + vecfields[1][i] * dt / 2;
        }
        vecfields[2] = getField(newState);
        for (int i = 0; i < n; i++) {
            newState[i] = state[i] + vecfields[2][i] * dt;
        }
        vecfields[3] = getField(newState);
        for (int i = 0; i < state.length; i++) {
            state[i] += dt * (vecfields[0][i] + 2 * vecfields[1][i] + 2 * vecfields[2][i] + vecfields[3][i]) / 6;
        }
    }
}
