package ch.zxseitz.j3de.graphics.mesh;

import ch.zxseitz.j3de.exceptions.BufferException;
import ch.zxseitz.j3de.exceptions.J3deException;
import ch.zxseitz.j3de.graphics.programs.Program;
import ch.zxseitz.j3de.graphics.programs.ShaderAttribute;
import ch.zxseitz.j3de.utils.ErrorUtils;
import ch.zxseitz.j3de.utils.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL45.*;

public class VertexBuffer {
    private static final long VERTEX_SIZE = 10000;
    private static final long INDEX_SIZE = 100000;

    private final Program program;
    private final int vao, ebo;
    private final Map<String, Integer> vbos;

    private volatile int vertexPointer, indexPointer;
    private volatile boolean deleted;

    public VertexBuffer(Program program) throws J3deException {
        this.program = program;
        this.vao = glGenVertexArrays();
        glBindVertexArray(this.vao);
        this.vbos = new HashMap<>(4);
        for (var sh : program.getAttributes()) {
            var id = glGenBuffers();
            var location = program.getAttribLocation(sh.name);
            glBindBuffer(GL_ARRAY_BUFFER, id);
            glEnableVertexAttribArray(location);
            glVertexAttribPointer(location, sh.size, GL_FLOAT, false, 0, 0);
            glBufferData(GL_ARRAY_BUFFER, VERTEX_SIZE * sh.size * 4L, GL_STREAM_DRAW);
            vbos.put(sh.name, id);
        }
        this.ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, INDEX_SIZE * 4L, GL_STREAM_DRAW);

        var error = glGetError();
        if (error != GL_NO_ERROR) {
            throw new BufferException(ErrorUtils.getErrorInfo(error), this);
        }
    }

    public Program getProgram() {
        return program;
    }

    public int getVaoId() {
        return vao;
    }

    public int getVboId(String name) {
        return vbos.get(name);
    }

    public int getEboId() {
        return ebo;
    }

    public synchronized Mesh createMesh(Map<ShaderAttribute, float[]> vertices, int[] indices,
                                        PrimitiveType mode) throws BufferException {
        var iterator = vertices.entrySet().iterator();
        // register first attributes
        var entry = iterator.next();
        var attr = entry.getKey();
        var data = entry.getValue();
        var n = data.length / attr.size;
        var id = vbos.get(attr.name);
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glBufferSubData(GL_ARRAY_BUFFER, vertexPointer * attr.size * 4L, data);
        // register remaining attributes
        while (iterator.hasNext()) {
            entry = iterator.next();
            attr = entry.getKey();
            data = entry.getValue();
            if (data.length / attr.size != n) {
                throw new IllegalArgumentException("Vertices length is not equal for all attributes");
            }
            id = vbos.get(attr.name);
            glBindBuffer(GL_ARRAY_BUFFER, id);
            glBufferSubData(GL_ARRAY_BUFFER, vertexPointer * attr.size * 4L, data);
        }
        // register indices
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, indexPointer * 4L, indices);

        var error = glGetError();
        if (error != GL_NO_ERROR) {
            throw new BufferException(ErrorUtils.getErrorInfo(error), this);
        }

        // create mesh with buffer reference
        var mesh = new Mesh(this, indexPointer, indexPointer + indices.length - 1, vertexPointer, mode);
        vertexPointer += n;
        indexPointer += indices.length;
        return mesh;
    }

    public synchronized boolean isDeleted() {
        return deleted;
    }

    public synchronized void destroy() {
        deleted = true;
        glDeleteVertexArrays(vao);
        for (var id : vbos.values()) {
            glDeleteBuffers(id);
        }
        glDeleteBuffers(ebo);
    }
}
