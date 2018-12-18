package ch.zxseitz.jpl;

import ch.zxseitz.jpl.graphics.Application;
import ch.zxseitz.jpl.graphics.Color;
import ch.zxseitz.jpl.graphics.Texture;
import ch.zxseitz.jpl.graphics.mesh.MeshFactory;
import ch.zxseitz.jpl.graphics.programs.Program;
import ch.zxseitz.jpl.graphics.programs.Shader;
import ch.zxseitz.jpl.graphics.programs.ShaderAttribute;
import ch.zxseitz.jpl.graphics.programs.uniforms.UniformInt;
import ch.zxseitz.jpl.graphics.programs.uniforms.UniformMatrix4;
import ch.zxseitz.jpl.graphics.scene.Camera;
import ch.zxseitz.jpl.graphics.scene.SceneGraph;
import ch.zxseitz.jpl.math.Matrix4;
import ch.zxseitz.jpl.graphics.scene.SceneObj;

public class SimpleTexture extends Application {
    private SceneGraph scene;

    public SimpleTexture() {
        super(800, 450, "SimpleTexture");
    }

    @Override
    protected void init() {
        // init program
        var vertexShader = new Shader("res/shaders/vertexShaderTex.glsl", Shader.Type.VERTEX_SHADER);
        var fragmentShader = new Shader("res/shaders/fragmentShaderTex.glsl", Shader.Type.FRAGMENT_SHADER);
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
        var texture = Texture.createTexture("freebies.jpg");
        var mesh = factory.createRect2D(2f, 2f, Color.WHITE, texture);
        tex.setValue(texture.id);
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
        (new SimpleTexture()).run();
    }
}
