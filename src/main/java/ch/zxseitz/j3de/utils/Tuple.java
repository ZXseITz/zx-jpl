package ch.zxseitz.j3de.utils;

public class Tuple<A, B> {
    private final A m1;
    private final B m2;

    public Tuple(A m1, B m2) {
        this.m1 = m1;
        this.m2 = m2;
    }

    public A getFirst() {
        return m1;
    }

    public B getSecond() {
        return m2;
    }
}
