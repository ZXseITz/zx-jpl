package ch.zxseitz.j3de.graphics.mesh;

import ch.zxseitz.j3de.exceptions.J3deException;
import ch.zxseitz.j3de.graphics.Color;
import ch.zxseitz.j3de.graphics.Texture;
import ch.zxseitz.j3de.graphics.programs.Program;
import ch.zxseitz.j3de.graphics.programs.ShaderAttribute;
import ch.zxseitz.j3de.utils.Tuple;

import java.nio.FloatBuffer;
import java.util.*;

public class MeshFactory {
    private static final Set<ShaderAttribute> attributeSet;
    private static final Map<Program, MeshFactory> factoryMap;

    public static MeshFactory getFactory(VertexBuffer buffer) {
        var program = buffer.getProgram();
        if (!factoryMap.containsKey(buffer.getProgram())) {
            for (var attribute : program.getAttributes()) {
                if (!attributeSet.contains(attribute)) return null;
            }
            factoryMap.put(program, new MeshFactory(buffer));
        }
        return factoryMap.get(program);
    }

    static {
        attributeSet = new HashSet<>(4);
        attributeSet.add(ShaderAttribute.POS);
        attributeSet.add(ShaderAttribute.NORMAL);
        attributeSet.add(ShaderAttribute.COLOR);
        attributeSet.add(ShaderAttribute.UV);
        factoryMap = new HashMap<>(4);
    }

    public static float[] createArray(float[] pattern, int count) {
        var buffer = FloatBuffer.allocate(pattern.length * count);
        for (int i = 0; i < count; i++) {
            buffer.put(pattern);
        }
        return buffer.flip().array();
    }

    public static float[] createNormalArray(int count) {
        return createArray(new float[]{0f, 0f, 1f}, count);
    }

    public static float[] createColorArray(Color color, int count) {
        return createArray(new float[]{
                color.r,
                color.g,
                color.b,
                color.a
        }, count);
    }

    private final VertexBuffer buffer;

    private MeshFactory(VertexBuffer buffer) {
        this.buffer = buffer;
    }

    // 2D Factory
    public Mesh createRect2D(float width, float height, Color color) throws J3deException {
        return createRect2D(width, height, color, null);
    }

    public Mesh createRect2D(float width, float height, Color color, Texture texture) throws J3deException {
        var program = buffer.getProgram();
        var vertices = new ArrayList<Tuple<ShaderAttribute, float[]>>(4);
        var mode = PrimitiveType.TRIANGLE_FAN;
        var indices = new int[]{
                0, 1, 2, 3
        };
        var x = width / 2;
        var y = height / 2;
        var attributes = program.getAttributes();
        if (attributes.contains(ShaderAttribute.POS))
            vertices.add(new Tuple<>(ShaderAttribute.POS, new float[]{
                    -x, -y, 0f,
                    x, -y, 0f,
                    x, y, 0f,
                    -x, y, 0f,
            }));
        if (attributes.contains(ShaderAttribute.NORMAL))
            vertices.add(new Tuple<>(ShaderAttribute.NORMAL, createNormalArray(4)));
        if (color != null && attributes.contains(ShaderAttribute.COLOR))
            vertices.add(new Tuple<>(ShaderAttribute.COLOR, createColorArray(color, 4)));
        if (texture != null && attributes.contains(ShaderAttribute.UV)) {
            vertices.add(new Tuple<>(ShaderAttribute.UV, new float[]{
                    0f, 0f,
                    0f, 1f,
                    1f, 1f,
                    1f, 0f,
            }));
        }
        var mesh = buffer.createMesh(vertices, indices, mode);
        mesh.setTexture(texture);
        return mesh;
    }

//    private void configRegularPolygon(Mesh mesh, float radius, int n, Color color, boolean tex) {
//        var vertices = new ArrayList<Triple<String, Integer, float[]>>(4);
//        var mode = PrimitiveType.TRIANGLE_FAN;
//        var indices = new int[n + 2];
//        var posBuffer = FloatBuffer.allocate(3 * (n + 2));
//        var uvBuffer = FloatBuffer.allocate(2 * (n + 2));
//        indices[0] = 0;
//        posBuffer.put(0f);
//        posBuffer.put(0f);
//        posBuffer.put(0f);
//        uvBuffer.put(0.5f);
//        uvBuffer.put(0.5f);
//        var phi = 2 * Math.PI / n;
//        //TODO: optimize
//        for (int i = 0; i <= n; i++) {
//            indices[i + 1] = i + 1;
//            var angle = i * phi;
//            var x = (float) (Math.sin(angle));
//            var y = (float) (Math.cos(angle));
//            posBuffer.put(x * radius);
//            posBuffer.put(y * radius);
//            posBuffer.put(0f);
//            uvBuffer.put(x * 0.5f + 0.5f);
//            uvBuffer.put(y * 0.5f + 0.5f);
//        }
//        vertices.add(new Triple<>("pos", 3, posBuffer.flip().array()));
//        vertices.add(new Triple<>("normal", 3, createNormalArray(n + 2)));
//        vertices.add(new Triple<>("color", 4, createColorArray(color, n + 2)));
//        if (tex) {
//            vertices.add(new Triple<>("uv", 2, uvBuffer.flip().array()));
//        }
//        mesh.setVertices(vertices, indices, mode);
//    }

    // 3D Factory
}
