package ch.zxseitz.jpl;

import ch.zxseitz.jpl.graphics.Application;
import ch.zxseitz.jpl.graphics.mesh.PrimitiveType;
import ch.zxseitz.jpl.graphics.programs.Program;
import ch.zxseitz.jpl.graphics.programs.Shader;
import ch.zxseitz.jpl.graphics.programs.ShaderAttribute;
import ch.zxseitz.jpl.graphics.programs.uniforms.UniformMatrix4;
import ch.zxseitz.jpl.graphics.scene.Camera;
import ch.zxseitz.jpl.graphics.scene.SceneGraph;
import ch.zxseitz.jpl.math.Matrix4;
import ch.zxseitz.jpl.graphics.mesh.Mesh;
import ch.zxseitz.jpl.graphics.scene.SceneObj;
import ch.zxseitz.jpl.graphics.GraphicUtils;
import ch.zxseitz.jpl.utils.Tuple;

import java.util.ArrayList;

public class SimpleTriangle extends Application {
    private SceneGraph scene;

    public SimpleTriangle() {
        super(450, 450, "SimpleTriangle");
    }

    @Override
    protected void init() {
        // init program
        var vertexShader = new Shader("res/shaders/vertexShader.glsl", Shader.Type.VERTEX_SHADER);
        var fragmentShader = new Shader("res/shaders/fragmentShader.glsl", Shader.Type.FRAGMENT_SHADER);
        var program = new Program(new Shader[]{
                vertexShader, fragmentShader
        }, new ShaderAttribute[]{
                ShaderAttribute.POS, ShaderAttribute.COLOR
        });
        vertexShader.destroy();
        fragmentShader.destroy();

        // init uniforms
        var P = new UniformMatrix4("P");
        var T = new UniformMatrix4("T");
        program.getUniforms().add(P);
        program.getUniforms().add(T);


        // scene
        scene = new SceneGraph(P, T);
        registerSizeChangedListener(GraphicUtils.createResizeListenerStdOrtho(scene.getCamera()));
        var mesh = new Mesh(program);
        var vertices = new ArrayList<Tuple<ShaderAttribute, float[]>>(2);
        vertices.add(new Tuple<>(ShaderAttribute.POS, new float[] {
                -0.8f, -0.6f, 0f,
                0.8f, -0.6f, 0f,
                0f, 0.6f, 0f
        }));
        vertices.add(new Tuple<>(ShaderAttribute.COLOR, new float[] {
                1f, 0f, 0f, 1f,
                0f, 1f, 0f, 1f,
                0f, 0f, 1f, 1f
        }));
        mesh.setVertices(vertices, new int[] {
                0, 1, 2
        }, PrimitiveType.TRIANGLES);
        scene.getNodes().add(new SceneObj(mesh, Matrix4.createTranslation(0, 0, -5f)));
    }

    @Override
    protected void updateFrame() {

    }

    @Override
    protected void renderFrame() {
        scene.render();
    }

    public static void main(String[] args) {
        (new SimpleTriangle()).run();
    }
}
