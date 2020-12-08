import ch.zxseitz.j3de.Application;
import ch.zxseitz.j3de.exceptions.J3deException;
import ch.zxseitz.j3de.graphics.Color;
import ch.zxseitz.j3de.graphics.Texture;
import ch.zxseitz.j3de.graphics.mesh.Rectangle2dFactory;
import ch.zxseitz.j3de.graphics.mesh.MeshFactory;
import ch.zxseitz.j3de.graphics.core.Program;
import ch.zxseitz.j3de.graphics.core.Shader;
import ch.zxseitz.j3de.graphics.core.ShaderAttribute;
import ch.zxseitz.j3de.graphics.scene.Scene;
import ch.zxseitz.j3de.graphics.scene.components.MeshComponent;
import ch.zxseitz.j3de.math.Matrix4;
import ch.zxseitz.j3de.graphics.scene.Actor;
import ch.zxseitz.j3de.window.ApplicationOptions;
import ch.zxseitz.j3de.window.Key;
import ch.zxseitz.j3de.window.KeyActionType;

import java.io.IOException;
import java.util.HashMap;

public class SimpleTexture extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private Scene scene;

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
        var factory = new MeshFactory(program);
        var texture = new Texture(getClassResource("textures/freebies.jpg"));
        var rectFactory = new Rectangle2dFactory();
        var rectVertices = new HashMap<ShaderAttribute, float[]>(3);
        rectVertices.put(ShaderAttribute.POS, rectFactory.getPos3(2f, 2f));
        rectVertices.put(ShaderAttribute.COLOR, MeshFactory.createColorArray(Color.WHITE, 4));
        rectVertices.put(ShaderAttribute.UV, rectFactory.getUv2());
        var rect = factory.create(rectVertices, rectFactory.getIndices(), rectFactory.getMode());
        rect.setTexture(texture);

        var component = new MeshComponent(rect);
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
        scene.update(delta);
    }
}
