package ch.zxseitz.j3de.graphics.mesh;

import ch.zxseitz.j3de.BufferException;
import ch.zxseitz.j3de.graphics.core.PrimitiveType;
import ch.zxseitz.j3de.graphics.core.Program;
import ch.zxseitz.j3de.graphics.core.ShaderAttribute;
import ch.zxseitz.j3de.utils.ErrorUtils;

import org.lwjgl.opengl.GL45.*;

class VertexBuffer(private val program: Program, val vertexSize: Int, val indexSize: Int) {
    private val _vbos: MutableMap<String, Int> = HashMap()
    private val _vao: Int
    private val _ebo: Int
    private var _vertexPointer: Int = 0
    private var _indexPointer: Int = 0

    @Volatile var deleted: Boolean = false
        private set
        @Synchronized get

    init {
        this._vao = glGenVertexArrays();
        glBindVertexArray(this._vao);
        for (attribute in program.attributes) {
            val id = glGenBuffers();
            val location = program.getAttribLocation(attribute.name);
            glBindBuffer(GL_ARRAY_BUFFER, id);
            glEnableVertexAttribArray(location);
            glVertexAttribPointer(location, attribute.size, GL_FLOAT, false, 0, 0);
            glBufferData(GL_ARRAY_BUFFER, vertexSize * attribute.size * 4L, GL_STREAM_DRAW);
            _vbos[attribute.name] = id;
        }
        this._ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, _ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexSize * 4L, GL_STREAM_DRAW);

        val error = glGetError();
        if (error != GL_NO_ERROR) {
            throw BufferException(ErrorUtils.getErrorInfo(error), this);
        }
    }

    @Synchronized fun countFreeVertices(): Int {
        return vertexSize - _vertexPointer;
    }

    @Synchronized fun countFreeIndices(): Int {
        return indexSize - _indexPointer;
    }

    @Synchronized fun createMesh(vertices: Map<ShaderAttribute, FloatArray>, vertexLength: Int, indices: IntArray,
                                     mode: PrimitiveType): Mesh {
        // register remaining attributes
        vertices.forEach{ (attr, data) ->
            val id = _vbos[attr.name] ?: throw BufferException("Cannot find vbo for attribute ${attr.name}", this)
            glBindBuffer(GL_ARRAY_BUFFER, id)
            glBufferSubData(GL_ARRAY_BUFFER, _vertexPointer * attr.size * 4L, data);
        }
        // register indices
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, _ebo);
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, _indexPointer * 4L, indices);

        val error = glGetError();
        if (error != GL_NO_ERROR) {
            throw BufferException(ErrorUtils.getErrorInfo(error), this);
        }

        // create mesh with buffer reference
        val mesh = Mesh(program, _vao, _ebo, _indexPointer, _indexPointer + indices.size - 1,
                _vertexPointer, mode);
        _vertexPointer += vertexLength;
        _indexPointer += indices.size;
        return mesh;
    }

    @Synchronized fun destroy() {
        deleted = true;
        glDeleteVertexArrays(_vao);
        for (id in _vbos.values) {
            glDeleteBuffers(id);
        }
        glDeleteBuffers(_ebo);
    }
}
