package ch.zxseitz.j3de.utils;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o instanceof Tuple) {
            Tuple<?, ?> tuple = (Tuple<?, ?>) o;
            return Objects.equals(m1, tuple.m1) &&
                    Objects.equals(m2, tuple.m2);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(m1, m2);
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", m1.toString(), m2.toString());
    }
}
