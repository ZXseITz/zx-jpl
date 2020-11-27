package ch.zxseitz.j3de.graphics.programs.uniforms;

public abstract class AbstractUniform<T> implements IUniform {
    protected String name;
    protected T value;

    AbstractUniform(String name) {
        this.name = name;
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
