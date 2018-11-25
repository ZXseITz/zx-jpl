package ch.zxseitz.jpl.graphics.mesh;

import ch.zxseitz.jpl.graphics.Texture;
import ch.zxseitz.jpl.graphics.programs.Program;
import ch.zxseitz.jpl.graphics.programs.ShaderVariable;
import ch.zxseitz.jpl.utils.Tuple;
import javafx.scene.paint.Color;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MeshFactory {
    private static Map<Integer, MeshFactory> factoryMap;
    public static MeshFactory getFactory(Program program) {
        return factoryMap.get(program.id);
    }
    static {
        Program[] programs = new Program[] {
                Program.NOLIGHT,
                Program.NOLIGHT_TEX,
                Program.NORMAL,
                Program.NORMAL_TEX,
        };
        factoryMap = new HashMap<>(4);
        for (var program : programs) {
            factoryMap.put(program.id, new MeshFactory(program));
        }
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
                (float) color.getRed(),
                (float) color.getGreen(),
                (float) color.getBlue(),
                (float) color.getOpacity()
        }, count);
    }

    private Program program;
    private MeshFactory(Program program) {
        this.program = program;
    }

    // 2D Factory
    public Mesh createRect2D(float width, float height, Color color) {
        return createRect2D(width, height, color, null);
    }
    public Mesh createRect2D(float width, float height, Color color, Texture texture) {
        var mesh = new Mesh(program);
        var vertices = new ArrayList<Tuple<ShaderVariable, float[]>>(4);
        var mode = PrimitiveType.TRIANGLE_FAN;
        var indices = new int[]{
                0, 1, 2, 3
        };
        var x = width / 2;
        var y = height / 2;
        var attributes = program.getAttributes();
        if (attributes.contains(ShaderVariable.POS))
            vertices.add(new Tuple<>(ShaderVariable.POS, new float[]{
                    -x, -y, 0f,
                    x, -y, 0f,
                    x, y, 0f,
                    -x, y, 0f,
            }));
        if(attributes.contains(ShaderVariable.NORMAL))
            vertices.add(new Tuple<>(ShaderVariable.NORMAL, createNormalArray(4)));
        if(color != null && attributes.contains(ShaderVariable.COLOR))
            vertices.add(new Tuple<>(ShaderVariable.COLOR, createColorArray(color, 4)));
        if (texture != null && attributes.contains(ShaderVariable.UV)) {
            mesh.setTex(texture);
            vertices.add(new Tuple<>(ShaderVariable.UV, new float[]{
                    0f, 0f,
                    0f, 1f,
                    1f, 1f,
                    1f, 0f,
            }));
        }
        mesh.addAll(vertices, indices, mode);
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
//        mesh.addAll(vertices, indices, mode);
//    }

    // 3D Factory
}
