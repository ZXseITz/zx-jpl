import ch.zxseitz.j3de.Application;
import ch.zxseitz.j3de.graphics.Color;
import ch.zxseitz.j3de.graphics.Texture;
import ch.zxseitz.j3de.graphics.mesh.MeshFactory;
import ch.zxseitz.j3de.graphics.programs.Program;
import ch.zxseitz.j3de.graphics.programs.Shader;
import ch.zxseitz.j3de.graphics.programs.ShaderAttribute;
import ch.zxseitz.j3de.graphics.programs.uniforms.UniformInt;
import ch.zxseitz.j3de.graphics.programs.uniforms.UniformMatrix4;
import ch.zxseitz.j3de.graphics.scene.SceneGraph;
import ch.zxseitz.j3de.math.Matrix4;
import ch.zxseitz.j3de.graphics.scene.SceneObj;
import ch.zxseitz.j3de.windows.ApplicationOptions;

import java.nio.file.Paths;

import static org.lwjgl.opengl.GL45.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL45.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL45.glClear;

public class SimpleTexture extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private SceneGraph scene;

    @Override
    protected ApplicationOptions applicationInit() {
        return new ApplicationOptions("Simple Texture", 450, 450);
    }

    @Override
    protected void initGame() throws Exception {
        // init program
        var vertexShader = new Shader(Paths.get(getClass().getClassLoader()
                .getResource( "shaders/vertexShaderTex.glsl").toURI()), Shader.Type.VERTEX_SHADER);
        var fragmentShader = new Shader(Paths.get(getClass().getClassLoader()
                .getResource( "shaders/fragmentShaderTex.glsl").toURI()), Shader.Type.FRAGMENT_SHADER);
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

        // init uniforms
        var P = new UniformMatrix4("P");
        var T = new UniformMatrix4("T");
        var tex = new UniformInt("tex");
        program.getUniforms().add(P);
        program.getUniforms().add(T);
        program.getUniforms().add(tex);

        //scene
        scene = new SceneGraph(P, T);
        var factory = MeshFactory.getFactory(program);
        var texture = new Texture(Paths.get(getClass().getClassLoader()
                .getResource("textures/freebies.jpg").toURI()));
        var mesh = factory.createRect2D(2f, 2f, Color.WHITE, texture);
        tex.setValue(texture.id);
        scene.getNodes().add(new SceneObj(mesh, Matrix4.createTranslation(0, 0, -5f)));
    }

    @Override
    protected void updateFrame() {

    }

    @Override
    protected void renderFrame() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        scene.render();
    }
}
