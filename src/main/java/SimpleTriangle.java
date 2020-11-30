import ch.zxseitz.j3de.Application;
import ch.zxseitz.j3de.exceptions.J3deException;
import ch.zxseitz.j3de.graphics.mesh.PrimitiveType;
import ch.zxseitz.j3de.graphics.programs.Program;
import ch.zxseitz.j3de.graphics.programs.Shader;
import ch.zxseitz.j3de.graphics.programs.ShaderAttribute;
import ch.zxseitz.j3de.graphics.scene.Scene;
import ch.zxseitz.j3de.graphics.scene.components.MeshComponent;
import ch.zxseitz.j3de.math.Matrix4;
import ch.zxseitz.j3de.graphics.mesh.Mesh;
import ch.zxseitz.j3de.graphics.scene.Actor;
import ch.zxseitz.j3de.utils.GraphicUtils;
import ch.zxseitz.j3de.utils.Tuple;
import ch.zxseitz.j3de.windows.ApplicationOptions;
import ch.zxseitz.j3de.windows.Key;
import ch.zxseitz.j3de.windows.KeyActionType;

import java.io.IOException;
import java.util.ArrayList;

public class SimpleTriangle extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private Scene scene;

    @Override
    protected ApplicationOptions applicationInit() {
        return new ApplicationOptions("Simple Triangle", 450, 450);
    }

    @Override
    protected void initGame() throws J3deException, IOException {
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

        // scene
        scene = new Scene(program);
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
        var component = new MeshComponent(mesh);
        var actor = new Actor(scene, Matrix4.createTranslation(0, 0, -5f));
        actor.getComponents().add(component);
        scene.getActors().add(actor);

        // keymap
        addKeyListener((key, keyActionType) -> {
            if (key == Key.ESC && keyActionType == KeyActionType.DOWN) {
                close();
            }
        });
        addSizeChangedListener(GraphicUtils.createResizeListenerStdOrtho(scene.getCamera()));
    }

    @Override
    protected void updateGame(double delta) throws J3deException {
        scene.update(delta);
    }
}
