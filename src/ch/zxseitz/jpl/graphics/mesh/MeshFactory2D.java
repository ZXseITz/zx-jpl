package ch.zxseitz.jpl.graphics.mesh;

import ch.zxseitz.jpl.graphics.Program;
import ch.zxseitz.jpl.graphics.Texture;
import javafx.scene.paint.Color;

import java.nio.FloatBuffer;

public class MeshFactory2D {
    private Program p;

    public MeshFactory2D(Program p) {
        this.p = p;
    }

    public static float[] createArray(float[] pattern, int count) {
        var buffer = FloatBuffer.allocate(pattern.length * count);
        for (int i = 0; i < count; i++) {
            buffer.put(pattern);
        }
        return buffer.flip().array();
    }

    public static float[] createNormalArray(int count) {
        return createArray(new float[] {0f, 0f, 1f}, count);
    }

    public static float[] createColorArray(Color color, int count) {
        return createArray(new float[] {
                (float) color.getRed(),
                (float) color.getGreen(),
                (float) color.getBlue(),
                (float) color.getOpacity()
        }, count);
    }

    public Mesh createRect(float width, float height, Color color) {
        var mesh = new Mesh(p);
        configRect(mesh, width, height, color, false);
        return mesh;
    }

    public MeshTex createRectTex(float width, float height, Color color, Texture tex) {
        var mesh = new MeshTex(p, tex);
        configRect(mesh, width, height, color, true);
        return mesh;
    }

    private void configRect(AbstractMesh mesh, float width, float height, Color color, boolean tex) {
        var mode = AbstractMesh.PrimitiveType.TRIANGLE_FAN;
        var indices = new int[]{
                0, 1, 2, 3
        };
        var x = width / 2;
        var y = height / 2;
        var pos = new float[]{
                -x, -y, 0f,
                x, -y, 0f,
                x, y, 0f,
                -x, y, 0f,
        };
        var normals = createNormalArray(4);
        var colors = createColorArray(color, 4);
        float[][] vertices;
        if (tex) {
            var uvs = new float[]{
                    0f, 0f,
                    0f, 1f,
                    1f, 1f,
                    1f, 0f,
            };
            vertices = new float[][]{pos, normals, colors, uvs};
        } else {
            vertices = new float[][]{pos, normals, colors};
        }
        mesh.addAll(vertices, indices, mode);
    }

    public Mesh createRegularPolygon(float radius, int n, Color color) {
        var mesh = new Mesh(p);
        configRegularPolygon(mesh, radius, n, color, false);
        return mesh;
    }

    public MeshTex createRegularPolygon(float radius, int n, Color color, Texture tex) {
        var mesh = new MeshTex(p, tex);
        configRegularPolygon(mesh, radius, n, color, true);
        return mesh;
    }

    private void configRegularPolygon(AbstractMesh mesh, float radius, int n, Color color, boolean tex) {
        var mode = AbstractMesh.PrimitiveType.TRIANGLE_FAN;
        var indices = new int[n + 2];
        var posBuffer = FloatBuffer.allocate(3*(n+2));
        var uvBuffer = FloatBuffer.allocate(2*(n+2));
        indices[0] = 0;
        posBuffer.put(0f);
        posBuffer.put(0f);
        posBuffer.put(0f);
        uvBuffer.put(0.5f);
        uvBuffer.put(0.5f);
        var phi = 2 * Math.PI / n;
        //TODO: optimize
        for (int i = 0; i <= n; i++) {
            indices[i + 1] = i + 1;
            var angle = i * phi;
            var x = (float) (Math.sin(angle));
            var y = (float) (Math.cos(angle));
            posBuffer.put(x * radius);
            posBuffer.put(y * radius);
            posBuffer.put(0f);
            uvBuffer.put(x * 0.5f + 0.5f);
            uvBuffer.put(y * 0.5f + 0.5f);
        }
        var pos = posBuffer.flip().array();
        var normals = createNormalArray(n+2);
        var colors = createColorArray(color, n+2);
        float[][] vertices;
        if (tex) {
            vertices = new float[][]{pos, normals, colors, uvBuffer.flip().array()};
        } else {
            vertices = new float[][]{pos, normals, colors};
        }
        mesh.addAll(vertices, indices, mode);
    }

    //TODO: Optional config polygon
}
