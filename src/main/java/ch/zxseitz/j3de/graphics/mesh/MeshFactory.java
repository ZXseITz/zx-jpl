package ch.zxseitz.j3de.graphics.mesh;


import ch.zxseitz.j3de.exceptions.J3deException;
import ch.zxseitz.j3de.graphics.Color;
import ch.zxseitz.j3de.graphics.core.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeshFactory {
    private static final int VERTEX_SIZE = 10000;
    private static final int INDEX_SIZE = 100000;

    private final Program program;
    private final List<VertexBuffer> buffers;

    public MeshFactory(Program program) {
        this.program = program;
        this.buffers = new ArrayList<>();
    }

    public Mesh create(float[] vertices, int[] indices,
                       PrimitiveType mode) throws J3deException {
        var combinedAttributeSize = program.getAttributeSize();
        var vertexLength = vertices.length / combinedAttributeSize;
        var attributeVertices = new HashMap<ShaderAttribute, float[]>();
        // extract attribute data
        var offset = 0;
        // todo use c++ matrix to extract columns?
        for (var attribute : program.getAttributes()) {
            var cache = new float[vertexLength * attribute.size];
            var k = 0;
            for (var i = 0; i < vertexLength; i++) {
                for (var j = 0; j < attribute.size; j++) {
                    cache[k++] = vertices[i * combinedAttributeSize + offset + j];
                }
            }
            offset += attribute.size;
            attributeVertices.put(attribute, cache);
        }

        var buffer = getBuffer(vertexLength, indices.length);
        return buffer.createMesh(attributeVertices, vertexLength, indices, mode);
    }

    public Mesh create(Map<ShaderAttribute, float[]> vertices, int[] indices,
                       PrimitiveType mode) throws J3deException {
        // validate vertices
        var vertexLength = -1;
        for (var attribute : program.getAttributes()) {
            var data = vertices.get(attribute);
            if (data == null) {
                throw new IllegalArgumentException("Vertices does not contain required attribute " + attribute.name);
            }
            var length = data.length / attribute.size;
            if (vertexLength < 0) {
                vertexLength = length;
            } else if (vertexLength != length) {
                throw new IllegalArgumentException("Vertices length is not equal for all attributes");
            }
        }
        var buffer = getBuffer(vertexLength, indices.length);
        // todo concurrency
        return buffer.createMesh(vertices, vertexLength, indices, mode);
    }

    // todo concurrency
    private VertexBuffer getBuffer(int vertexLength, int indexSize) throws J3deException {
        for (var buffer : buffers) {
            if (buffer.countFreeVertices() >= vertexLength && buffer.countFreeIndices() >= indexSize) {
                return buffer;
            }
        }
        var buffer = new VertexBuffer(program, VERTEX_SIZE, INDEX_SIZE);
        buffers.add(buffer);
        return buffer;
    }

    private static final float[] normalPattern = new float[] {0f, 0f, 1f};

    public static float[] createNormalArray(int vertexLength) {
        var buffer = FloatBuffer.allocate(3 * vertexLength);
        for (int i = 0; i < vertexLength; i++) {
            buffer.put(normalPattern);
        }
        return buffer.flip().array();
    }

    // interpolate between multiple colors
    public static float[] createColorArray(Color color, int vertexLength) {
        var buffer = FloatBuffer.allocate(4 * vertexLength);
        for (int i = 0; i < vertexLength; i++) {
            buffer.put(color.r);
            buffer.put(color.g);
            buffer.put(color.b);
            buffer.put(color.a);
        }
        return buffer.flip().array();
    }
}
