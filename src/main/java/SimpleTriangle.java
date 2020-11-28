import ch.zxseitz.j3de.Application;
import ch.zxseitz.j3de.exceptions.J3deException;
import ch.zxseitz.j3de.graphics.mesh.PrimitiveType;
import ch.zxseitz.j3de.graphics.programs.Program;
import ch.zxseitz.j3de.graphics.programs.Shader;
import ch.zxseitz.j3de.graphics.programs.ShaderAttribute;
import ch.zxseitz.j3de.graphics.programs.uniforms.UniformMatrix4;
import ch.zxseitz.j3de.graphics.scene.SceneGraph;
import ch.zxseitz.j3de.math.Matrix4;
import ch.zxseitz.j3de.graphics.mesh.Mesh;
import ch.zxseitz.j3de.graphics.scene.SceneObj;
import ch.zxseitz.j3de.graphics.GraphicUtils;
import ch.zxseitz.j3de.utils.Tuple;
import ch.zxseitz.j3de.windows.ApplicationOptions;

import java.util.ArrayList;

public class SimpleTriangle extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private SceneGraph scene;

    @Override
    protected ApplicationOptions applicationInit() {
        return new ApplicationOptions("Simple Triangle", 450, 450);
    }

    @Override
    protected void initGame() throws J3deException {
        // init program
        var vertexShader = new Shader(getClassResource(  "shaders/vertexShader.glsl"),
                Shader.Type.VERTEX_SHADER);
        var fragmentShader = new Shader(getClassResource( "shaders/fragmentShader.glsl"),
                Shader.Type.FRAGMENT_SHADER);
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
        getSizeChangedListeners().add(GraphicUtils.createResizeListenerStdOrtho(scene.getCamera()));
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
    protected void updateGame(double delta) {
        scene.render();
    }
}
