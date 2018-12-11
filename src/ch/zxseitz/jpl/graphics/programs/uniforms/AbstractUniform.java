package ch.zxseitz.jpl.graphics.programs.uniforms;

public abstract class AbstractUniform<T> implements IUniform {
    protected String name;
    protected T value;

    AbstractUniform(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public abstract void write(int location);
}
