package ch.zxseitz.j3de.graphics.mesh;

import ch.zxseitz.j3de.graphics.Color;
import ch.zxseitz.j3de.graphics.core.*;

import java.nio.FloatBuffer;
import kotlin.collections.ArrayList

class MeshFactory(private val program: Program) {
    companion object {
        const val VERTEX_SIZE: Int = 10000
        const val INDEX_SIZE: Int = 100000

        val normalPattern: FloatArray = floatArrayOf(0f, 0f, 1f)
        fun createNormalArray(vertexLength: Int): FloatArray {
            val buffer = FloatBuffer.allocate(3 * vertexLength)
            for (i in 0 until vertexLength) {
                buffer.put(normalPattern)
            }
            return buffer.flip().array()
        }

        // interpolate between multiple colors
        fun createColorArray(color: Color, vertexLength: Int): FloatArray {
            val buffer = FloatBuffer.allocate(4 * vertexLength)
            for (i in 0 until vertexLength) {
                buffer.put(color.r)
                buffer.put(color.g)
                buffer.put(color.b)
                buffer.put(color.a)
            }
            return buffer.flip().array()
        }
    }

    private val buffers: MutableList<VertexBuffer> = ArrayList()

    fun create(vertices: FloatArray, indices: IntArray, mode: PrimitiveType): Mesh {
        val combinedAttributeSize = program.getAttributeSize()
        val vertexLength = vertices.size / combinedAttributeSize
        val attributeVertices = HashMap<ShaderAttribute, FloatArray>()
        // extract attribute data
        var offset = 0
        // todo use c++ matrix to extract columns?
        for (attribute in program.attributes) {
            val cache = FloatArray(vertexLength * attribute.size)
            var k = 0
            for (i in 0 until vertexLength) {
                for (j in 0 until attribute.size) {
                    cache[k++] = vertices[i * combinedAttributeSize + offset + j]
                }
            }
            offset += attribute.size
            attributeVertices[attribute] = cache
        }

        val buffer = getBuffer(vertexLength, indices.size)
        return buffer.createMesh(attributeVertices, vertexLength, indices, mode)
    }

    fun create(vertices: Map<ShaderAttribute, FloatArray>, indices: IntArray, mode: PrimitiveType): Mesh {
        // validate vertices
        var vertexLength = -1
        for (attribute in program.attributes) {
            val data: FloatArray = vertices[attribute]
                ?: throw IllegalArgumentException("Vertices does not contain required attribute " + attribute.name)
            val length = data.size / attribute.size
            if (vertexLength < 0) {
                vertexLength = length
            } else if (vertexLength != length) {
                throw IllegalArgumentException("Vertices length is not equal for all attributes")
            }
        }
        val buffer = getBuffer(vertexLength, indices.size)
        // todo concurrency
        return buffer.createMesh(vertices, vertexLength, indices, mode)
    }

    // todo concurrency
    private fun getBuffer(vertexLength: Int, indexSize: Int): VertexBuffer {
        for (buffer in buffers) {
            if (buffer.countFreeVertices() >= vertexLength && buffer.countFreeIndices() >= indexSize) {
                return buffer
            }
        }
        val buffer = VertexBuffer(program, VERTEX_SIZE, INDEX_SIZE)
        buffers.add(buffer)
        return buffer
    }
}
