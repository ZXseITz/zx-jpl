package ch.zxseitz.j3de.graphics.mesh;


import ch.zxseitz.j3de.graphics.Texture;
import ch.zxseitz.j3de.graphics.core.PrimitiveType;
import ch.zxseitz.j3de.graphics.core.Program;

public class Mesh {
    private final Program program;
    private final int vao, ebo;
    private final int start, end;
    private final int vertexStart;
    private final PrimitiveType mode;
    private Texture texture;
    //todo texture

    Mesh(Program program, int vao, int ebo, int start, int end, int vertexStart, PrimitiveType mode) {
        this.program = program;
        this.vao = vao;
        this.ebo = ebo;
        this.start = start;
        this.end = end;
        this.vertexStart = vertexStart;
        this.mode = mode;
    }

    public Program getProgram() {
        return program;
    }

    public int getVao() {
        return vao;
    }

    public int getEbo() {
        return ebo;
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

    public int getVertexStart() {
        return vertexStart;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
