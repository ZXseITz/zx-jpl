package ch.zxseitz.j3de.exceptions;

import ch.zxseitz.j3de.graphics.mesh.VertexBuffer;

public class BufferException extends J3deException {
    private final VertexBuffer buffer;

    public BufferException(String message, VertexBuffer buffer) {
        super(message);
        this.buffer = buffer;
    }

    public VertexBuffer getBuffer() {
        return buffer;
    }
}
