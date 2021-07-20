package ch.zxseitz.j3de.window;

import org.lwjgl.glfw.GLFW.*;

enum class Key {
    ESC,
    CTRL_LEFT,
    CTRL_RIGHT,
    SHIFT_LEFT,
    SHIFT_RIGHT,
    ALT_LEFT,
    ALT_RIGHT,
    ENTER,
    SPACE,
    TAB,
    CAPS_LOCK,
    A,
    B,
    C,
    D,
    E,
    F,
    G,
    H,
    I,
    J,
    K,
    L,
    M,
    N,
    O,
    P,
    Q,
    R,
    S,
    T,
    U,
    V,
    W,
    X,
    Y,
    Z,
    ZERO,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE;

    companion object {
        fun fromValue(value: Int): Key? {
            return when (value) {
                GLFW_KEY_ESCAPE -> ESC;
                GLFW_KEY_LEFT_CONTROL -> CTRL_LEFT;
                GLFW_KEY_RIGHT_CONTROL -> CTRL_RIGHT;
                GLFW_KEY_LEFT_SHIFT -> SHIFT_LEFT;
                GLFW_KEY_RIGHT_SHIFT -> SHIFT_RIGHT;
                GLFW_KEY_LEFT_ALT -> ALT_LEFT;
                GLFW_KEY_RIGHT_ALT -> ALT_RIGHT;
                GLFW_KEY_ENTER -> ENTER;
                GLFW_KEY_SPACE -> SPACE;
                GLFW_KEY_TAB -> TAB;
                GLFW_KEY_CAPS_LOCK -> CAPS_LOCK;
                GLFW_KEY_A -> A;
                GLFW_KEY_B -> B;
                GLFW_KEY_C -> C;
                GLFW_KEY_D -> D;
                GLFW_KEY_E -> E;
                GLFW_KEY_F -> F;
                GLFW_KEY_G -> G;
                GLFW_KEY_H -> H;
                GLFW_KEY_I -> I;
                GLFW_KEY_J -> J;
                GLFW_KEY_K -> K;
                GLFW_KEY_L -> L;
                GLFW_KEY_M -> M;
                GLFW_KEY_N -> N;
                GLFW_KEY_O -> O;
                GLFW_KEY_P -> P;
                GLFW_KEY_Q -> Q;
                GLFW_KEY_R -> R;
                GLFW_KEY_S -> S;
                GLFW_KEY_T -> T;
                GLFW_KEY_U -> U;
                GLFW_KEY_V -> V;
                GLFW_KEY_W -> W;
                GLFW_KEY_X -> X;
                GLFW_KEY_Y -> Y;
                GLFW_KEY_Z -> Z;
                GLFW_KEY_0 -> ZERO;
                GLFW_KEY_1 -> ONE;
                GLFW_KEY_2 -> TWO;
                GLFW_KEY_3 -> THREE;
                GLFW_KEY_4 -> FOUR;
                GLFW_KEY_5 -> FIVE;
                GLFW_KEY_6 -> SIX;
                GLFW_KEY_7 -> SEVEN;
                GLFW_KEY_8 -> EIGHT;
                GLFW_KEY_9 -> NINE;
                else -> null
            }
        }
    }
}
