package ch.zxseitz.j3de.utils;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o instanceof Triple) {
            Triple<?, ?, ?> tuple = (Triple<?, ?, ?>) o;
            return Objects.equals(m1, tuple.m1)
                    && Objects.equals(m2, tuple.m2)
                    && Objects.equals(m3, tuple.m3);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(m1, m2, m3);
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %s)", m1.toString(), m2.toString(), m3.toString());
    }
}
