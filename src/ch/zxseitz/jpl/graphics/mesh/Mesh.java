package ch.zxseitz.jpl.graphics.mesh;

import ch.zxseitz.jpl.graphics.Texture;
import ch.zxseitz.jpl.graphics.programs.Program;
import ch.zxseitz.jpl.graphics.programs.ShaderAttribute;
import ch.zxseitz.jpl.utils.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL45.*;

public class Mesh {
    private int vao, vboIdx;
    private Map<String, Integer> vbos;
    private PrimitiveType mode;
    private int idxLength;

    private Program program;
    private Texture tex;

    public Mesh(Program program) {
        this.program = program;
        this.vao = glGenVertexArrays();
        glBindVertexArray(this.vao);
        this.vbos = new HashMap<>(5);
        for(var sh : program.getAttributes()) {
            var id = glGenBuffers();
            vbos.put(sh.name, id);
        }
        this.vboIdx = glGenBuffers();
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

    public int getVboIdx() {
        return vboIdx;
    }

    public int getVbo(String name) {
        return vbos.get(name);
    }

    public void addAll(List<Tuple<ShaderAttribute, float[]>> vertices, int[] indices, PrimitiveType mode) {
//        var attributes = program.getAttributes();
//        if (attributes.size() != vertices.size()) throw new RuntimeException("Shader attributes don't match");
        this.mode = mode;
            for (var attribute : vertices) {
                var sv = attribute.getFirst();
//                if (!attributes.contains(sv)) throw new RuntimeException("Shader attributes don't match");
                var data = attribute.getSecond();
                var id = vbos.get(sv.name);
                glBindBuffer(GL_ARRAY_BUFFER, id);
                glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
                var location = program.getAttribLocation(sv.name);
                glEnableVertexAttribArray(location);
                glBindBuffer(GL_ARRAY_BUFFER, id);
                glVertexAttribPointer(location, sv.size, GL_FLOAT, false, 0, 0);
            }
        idxLength = indices.length;
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboIdx);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
    }

    public void render() {
        glBindVertexArray(vao);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboIdx);
        glDrawElements(mode.type, idxLength, GL_UNSIGNED_INT, 0);
    }
}
