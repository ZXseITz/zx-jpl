import ch.zxseitz.j3de.Application;
import ch.zxseitz.j3de.J3deException;
import ch.zxseitz.j3de.graphics.core.PrimitiveType;
import ch.zxseitz.j3de.graphics.core.Program;
import ch.zxseitz.j3de.graphics.core.Shader;
import ch.zxseitz.j3de.graphics.core.ShaderAttribute;
import ch.zxseitz.j3de.graphics.mesh.MeshFactory;
import ch.zxseitz.j3de.graphics.scene.Scene;
import ch.zxseitz.j3de.graphics.scene.components.MeshComponent;
import ch.zxseitz.j3de.math.Matrix4;
import ch.zxseitz.j3de.graphics.scene.Actor;
import ch.zxseitz.j3de.utils.GraphicUtils;
import ch.zxseitz.j3de.window.ApplicationOptions;
import ch.zxseitz.j3de.window.Key;
import ch.zxseitz.j3de.window.KeyActionType;

import java.io.IOException;
import java.util.HashMap;

fun main(args: Array<String>) {
    Application.launch(args, SimpleTriangle::class.java)
}

class SimpleTriangle: Application() {
    private lateinit var _scene: Scene;

    override fun applicationInit(): ApplicationOptions {
        return ApplicationOptions("Simple Triangle", 450, 450)
    }

    override fun initGame() {
        // init program
        val vertexShader = Shader(getClassResource(  "shaders/vertexShader.glsl"),
                Shader.Type.VERTEX_SHADER)
        val fragmentShader = Shader(getClassResource( "shaders/fragmentShader.glsl"),
                Shader.Type.FRAGMENT_SHADER)
        val program = Program(arrayOf(
                vertexShader, fragmentShader
        ), arrayOf(
                ShaderAttribute.POS, ShaderAttribute.COLOR
        ))
        vertexShader.destroy()
        fragmentShader.destroy()

        // scene and buffer
        _scene = Scene(program)

        // first triangle
        val factory = MeshFactory(program)
        val vertices = HashMap<ShaderAttribute, FloatArray>(2)
        vertices[ShaderAttribute.POS] = floatArrayOf(
            -0.8f, -0.6f, 0f,
            0.8f, -0.6f, 0f,
            0f, 0.6f, 0f
        )
        vertices[ShaderAttribute.COLOR] = floatArrayOf(
            1f, 0f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 0f, 1f, 1f
        )
        var component = MeshComponent(factory.create(vertices, intArrayOf(
                0, 1, 2
        ), PrimitiveType.TRIANGLES))
        var actor = Actor(_scene, Matrix4.createTranslation(0f, 0f, -5f));
        actor.components.add(component);
        _scene.actors.add(actor);

        // second triangle
        component = MeshComponent(factory.create(floatArrayOf(
                -0.9f, 0.9f, 0f,   0f, 0f, 1f, 1f,
                -0.9f, 0.8f, 0f,   0f, 0f, 1f, 1f,
                -0.8f, 0.9f, 0f,   0f, 0f, 1f, 1f
        ), intArrayOf(
                0, 1, 2
        ), PrimitiveType.TRIANGLES));
        actor = Actor(_scene, Matrix4.createTranslation(0f, 0f, -5f));
        actor.components.add(component);
        _scene.actors.add(actor);

        // keymap
        addKeyListener { key, keyActionType ->
            if (key == Key.ESC && keyActionType == KeyActionType.DOWN) {
                close();
            }
        }
        addSizeChangedListener(GraphicUtils.createResizeListenerStdOrtho(_scene.camera));
    }

    @Override
    override fun updateGame(delta: Double) {
        _scene.update(delta);
    }
}
