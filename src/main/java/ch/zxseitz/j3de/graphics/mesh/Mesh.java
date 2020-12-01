package ch.zxseitz.j3de.graphics.mesh;


import ch.zxseitz.j3de.exceptions.BufferException;
import ch.zxseitz.j3de.graphics.Texture;
import ch.zxseitz.j3de.graphics.programs.Program;
import ch.zxseitz.j3de.utils.ErrorUtils;

import static org.lwjgl.opengl.GL45.*;

public class Mesh {
    private final VertexBuffer buffer;
    private final int start, end;
    private final int vertexStart;
    private final PrimitiveType mode;
    private Texture texture;
    //todo texture

    Mesh(VertexBuffer buffer, int start, int end, int vertexStart, PrimitiveType mode) {
        this.buffer = buffer;
        this.start = start;
        this.end = end;
        this.vertexStart = vertexStart;
        this.mode = mode;
    }

    public Program getProgram() {
        return buffer.getProgram();
    }

    public PrimitiveType getMode() {
        return mode;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int count() {
        return end - start + 1;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void render() throws BufferException {
        glBindVertexArray(buffer.getVaoId());
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, buffer.getEboId());
        glDrawRangeElementsBaseVertex(mode.id, start, end, count(), GL_UNSIGNED_INT, 0, vertexStart);

        var error = glGetError();
        if (error != GL_NO_ERROR) {
            throw new BufferException(ErrorUtils.getErrorInfo(error), buffer);
        }
    }
}
