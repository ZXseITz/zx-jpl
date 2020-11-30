package ch.zxseitz.j3de.graphics.mesh;

import ch.zxseitz.j3de.exceptions.J3deException;
import ch.zxseitz.j3de.graphics.Texture;
import ch.zxseitz.j3de.graphics.programs.Program;
import ch.zxseitz.j3de.graphics.programs.ShaderAttribute;
import ch.zxseitz.j3de.utils.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL45.*;

public class Mesh {
    private int vao, ebo;
    private Map<String, Integer> vbos;
    private PrimitiveType mode;
    private int idxLength;
    private boolean deleted;

    private Program program;
    private Texture tex;

    public Mesh(Program program) throws J3deException {
        this.program = program;
        this.vao = glGenVertexArrays();
        glBindVertexArray(this.vao);
        this.vbos = new HashMap<>(4);
        for(var sh : program.getAttributes()) {
            var id = glGenBuffers();
            var location = program.getAttribLocation(sh.name);
            glBindBuffer(GL_ARRAY_BUFFER, id);
            glEnableVertexAttribArray(location);
            glVertexAttribPointer(location, sh.size, GL_FLOAT, false, 0, 0);
            vbos.put(sh.name, id);
        }
        this.ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
    }

    public Program getProgram() {
        return program;
    }

    public Texture getTex() {
        return tex;
    }

    public void setTex(Texture tex) {
        this.tex = tex;
    }

    public int getVao() {
        return vao;
    }

    public int getEbo() {
        return ebo;
    }

    public int getVbo(String name) {
        return vbos.get(name);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setVertices(List<Tuple<ShaderAttribute, float[]>> vertices, int[] indices, PrimitiveType mode) {
        this.mode = mode;
            for (var attribute : vertices) {
                var id = vbos.get(attribute.getFirst().name);
                var data = attribute.getSecond();
                glBindBuffer(GL_ARRAY_BUFFER, id);
                glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
            }
        idxLength = indices.length;
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
    }

    public void render() {
        glBindVertexArray(vao);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glDrawElements(mode.id, idxLength, GL_UNSIGNED_INT, 0);
    }

    public void destroy() {
        deleted = true;
        glDeleteVertexArrays(vao);
        for (var id : vbos.values()) {
            glDeleteBuffers(id);
        }
        glDeleteBuffers(ebo);
    }
}
