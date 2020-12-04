package ch.zxseitz.j3de.graphics.mesh;

import ch.zxseitz.j3de.exceptions.BufferException;
import ch.zxseitz.j3de.exceptions.J3deException;
import ch.zxseitz.j3de.graphics.core.PrimitiveType;
import ch.zxseitz.j3de.graphics.core.Program;
import ch.zxseitz.j3de.graphics.core.ShaderAttribute;
import ch.zxseitz.j3de.utils.ErrorUtils;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL45.*;

public class VertexBuffer {
    private final Program program;
    private final int vertexSize, indexSize;
    private final int vao, ebo;
    private final Map<String, Integer> vbos;
    private int vertexPointer, indexPointer;
    private boolean deleted;

    VertexBuffer(Program program, int vertexSize, int indexSize) throws J3deException {
        this.program = program;
        this.vertexSize = vertexSize;
        this.indexSize = indexSize;
        this.vao = glGenVertexArrays();
        glBindVertexArray(this.vao);
        this.vbos = new HashMap<>(4);
        for (var attribute : program.getAttributes()) {
            var id = glGenBuffers();
            var location = program.getAttribLocation(attribute.name);
            glBindBuffer(GL_ARRAY_BUFFER, id);
            glEnableVertexAttribArray(location);
            glVertexAttribPointer(location, attribute.size, GL_FLOAT, false, 0, 0);
            glBufferData(GL_ARRAY_BUFFER, vertexSize * attribute.size * 4L, GL_STREAM_DRAW);
            vbos.put(attribute.name, id);
        }
        this.ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexSize * 4L, GL_STREAM_DRAW);

        var error = glGetError();
        if (error != GL_NO_ERROR) {
            throw new BufferException(ErrorUtils.getErrorInfo(error), this);
        }
    }

    public int countFreeVertices() {
        return vertexSize - vertexPointer;
    }

    public int countFreeIndices() {
        return indexSize - indexPointer;
    }

    Mesh createMesh(Map<ShaderAttribute, float[]> vertices, int vertexLength, int[] indices,
                    PrimitiveType mode) throws BufferException {
        // register remaining attributes
        for (Map.Entry<ShaderAttribute, float[]> entry : vertices.entrySet()) {
            var attr = entry.getKey();
            var data = entry.getValue();
            var id = vbos.get(attr.name);
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
        var mesh = new Mesh(program, vao, ebo, indexPointer, indexPointer + indices.length - 1,
                vertexPointer, mode);
        vertexPointer += vertexLength;
        indexPointer += indices.length;
        return mesh;
    }

    boolean isDeleted() {
        return deleted;
    }

    void destroy() {
        deleted = true;
        glDeleteVertexArrays(vao);
        for (var id : vbos.values()) {
            glDeleteBuffers(id);
        }
        glDeleteBuffers(ebo);
    }
}
