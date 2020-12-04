package ch.zxseitz.j3de.exceptions;

import ch.zxseitz.j3de.graphics.core.Shader;

public class ShaderException extends J3deException {
    private final Shader shader;

    public ShaderException(String message, Shader shader) {
        super(message);
        this.shader = shader;
    }

    public Shader getShader() {
        return shader;
    }
}
