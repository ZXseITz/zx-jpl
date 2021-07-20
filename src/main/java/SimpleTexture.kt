import ch.zxseitz.j3de.Application;
import ch.zxseitz.j3de.graphics.Color;
import ch.zxseitz.j3de.graphics.Texture
import ch.zxseitz.j3de.graphics.core.Program
import ch.zxseitz.j3de.graphics.mesh.MeshFactory;
import ch.zxseitz.j3de.graphics.core.Shader;
import ch.zxseitz.j3de.graphics.core.ShaderAttribute;
import ch.zxseitz.j3de.graphics.mesh.Rectangle2dFactory
import ch.zxseitz.j3de.graphics.scene.Actor
import ch.zxseitz.j3de.graphics.scene.Scene
import ch.zxseitz.j3de.graphics.scene.components.MeshComponent
import ch.zxseitz.j3de.math.Matrix4
import ch.zxseitz.j3de.window.ApplicationOptions
import ch.zxseitz.j3de.window.Key;
import ch.zxseitz.j3de.window.KeyActionType;

fun main(args: Array<String>) {
    Application.launch(args, SimpleTexture::class.java)
}

class SimpleTexture: Application() {
    private lateinit var _scene: Scene

    override fun applicationInit(): ApplicationOptions {
        return ApplicationOptions("Simple Texture", 450, 450)
    }

    override fun initGame() {
        // init program
        val vertexShader = Shader(getClassResource( "shaders/vertexShaderTex.glsl"),
                Shader.Type.VERTEX_SHADER)
        val fragmentShader = Shader(getClassResource( "shaders/fragmentShaderTex.glsl"),
                Shader.Type.FRAGMENT_SHADER)
        val program = Program(arrayOf(
                vertexShader,
                fragmentShader
        ), arrayOf(
                ShaderAttribute.POS,
                ShaderAttribute.COLOR,
                ShaderAttribute.UV
        ))
        vertexShader.destroy()
        fragmentShader.destroy()

        //scene
        _scene = Scene(program)
        val factory = MeshFactory(program)
        val texture = Texture(getClassResource("textures/freebies.jpg"))
        val rectFactory = Rectangle2dFactory()
        val rectVertices = HashMap<ShaderAttribute, FloatArray>(3)
        rectVertices[ShaderAttribute.POS] = rectFactory.getPos3(2f, 2f)
        rectVertices[ShaderAttribute.COLOR] = MeshFactory.createColorArray(Color.WHITE, 4)
        rectVertices[ShaderAttribute.UV] = rectFactory.uv
        val rect = factory.create(rectVertices, rectFactory.indices, rectFactory.mode)
        rect.texture = texture;

        val component = MeshComponent(rect)
        val actor = Actor(_scene, Matrix4.createTranslation(0f, 0f, -5f))
        actor.components.add(component)
        _scene.actors.add(actor)

        // keymap
        addKeyListener({key, keyActionType ->
            if (key == Key.ESC && keyActionType == KeyActionType.DOWN) {
                close();
            }
        })
    }

    override fun updateGame(delta: Double) {
        _scene.update(delta)
    }
}
