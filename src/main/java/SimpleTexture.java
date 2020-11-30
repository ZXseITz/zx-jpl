import ch.zxseitz.j3de.Application;
import ch.zxseitz.j3de.exceptions.J3deException;
import ch.zxseitz.j3de.graphics.Color;
import ch.zxseitz.j3de.graphics.Texture;
import ch.zxseitz.j3de.graphics.mesh.MeshFactory;
import ch.zxseitz.j3de.graphics.programs.Program;
import ch.zxseitz.j3de.graphics.programs.Shader;
import ch.zxseitz.j3de.graphics.programs.ShaderAttribute;
import ch.zxseitz.j3de.graphics.scene.Scene;
import ch.zxseitz.j3de.graphics.scene.components.MeshComponent;
import ch.zxseitz.j3de.math.Matrix4;
import ch.zxseitz.j3de.graphics.scene.Actor;
import ch.zxseitz.j3de.window.ApplicationOptions;
import ch.zxseitz.j3de.window.Key;
import ch.zxseitz.j3de.window.KeyActionType;

import java.io.IOException;

public class SimpleTexture extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private Scene scene;
    private Texture texture;

    @Override
    protected ApplicationOptions applicationInit() {
        return new ApplicationOptions("Simple Texture", 450, 450);
    }

    @Override
    protected void initGame() throws J3deException, IOException {
        // init program
        var vertexShader = new Shader(getClassResource( "shaders/vertexShaderTex.glsl"),
                Shader.Type.VERTEX_SHADER);
        var fragmentShader = new Shader(getClassResource( "shaders/fragmentShaderTex.glsl"),
                Shader.Type.FRAGMENT_SHADER);
        var program = new Program(new Shader[] {
                vertexShader,
                fragmentShader
        }, new ShaderAttribute[]{
                ShaderAttribute.POS,
                ShaderAttribute.COLOR,
                ShaderAttribute.UV
        });
        vertexShader.destroy();
        fragmentShader.destroy();

        //scene
        scene = new Scene(program);
        var factory = MeshFactory.getFactory(program);
        assert factory != null;
        texture = new Texture(getClassResource("textures/freebies.jpg"));
        var mesh = factory.createRect2D(2f, 2f, Color.WHITE, texture);
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
    }

    @Override
    protected void updateGame(double delta) throws J3deException {
        //todo move texture to mesh component
        scene.getProgram().writeUniform("tex", texture.id);
        scene.update(delta);
    }
}
