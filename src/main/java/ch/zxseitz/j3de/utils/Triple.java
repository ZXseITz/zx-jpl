package ch.zxseitz.j3de.utils;

public class Triple<A, B, C> {
    private final A m1;
    private final B m2;
    private final C m3;

    public Triple(A m1, B m2, C m3) {
        this.m1 = m1;
        this.m2 = m2;
        this.m3 = m3;
    }

    public A getFirst() {
        return m1;
    }

    public B getSecond() {
        return m2;
    }

    public C getThird() {
        return m3;
    }
}
