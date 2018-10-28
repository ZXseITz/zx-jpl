package ch.zxseitz.jpl.graphics.programs;

import ch.zxseitz.jpl.graphics.Texture;
import ch.zxseitz.jpl.math.Matrix4;
import ch.zxseitz.jpl.math.Vector2;
import ch.zxseitz.jpl.math.Vector3;
import ch.zxseitz.jpl.math.Vector4;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL45.*;

public class Program {
    public final static Program NOLIGHT = createProgram(
            "vertexShader.glsl", "fragmentShader.glsl");
    public final static Program NOLIGHT_TEX = createProgram(
            "vertexShaderTex.glsl", "fragmentShaderTex.glsl");
    public final static Program NORMAL = createProgram(
            "vertexShaderLight.glsl", "fragmentShaderLight.glsl");
    public final static Program NORMAL_TEX = createProgram(
            "vertexShaderLightTex.glsl", "fragmentShaderLightTex.glsl");

    private final Map<String, Integer> attributes;
    private final Map<String, String> uniforms;
    public int id;
    private Shader vertexShader, fragmentShader;

    public Program(String vertexShaderPath, String fragmentShaderPath) {
        this.attributes = new HashMap<>();
        this.uniforms = new HashMap<>();

        try {
            var id = glCreateProgram();
            // vertex shader
            var vid = glCreateShader(GL_VERTEX_SHADER);
            glShaderSource(vid, parseShader(vertexShaderPath, true, true));
            glCompileShader(vid);
            glAttachShader(id, vid);
            this.vertexShader = new Shader(vid, Shader.ShaderType.VERTEX_SHADER, vertexShaderPath);
            // fragment shader
            var fid = glCreateShader(GL_FRAGMENT_SHADER);
            glShaderSource(fid, parseShader(fragmentShaderPath, false, true));
            glCompileShader(fid);
            glAttachShader(id, fid);
            this.fragmentShader = new Shader(fid, Shader.ShaderType.FRAGMENT_SHADER, fragmentShaderPath);
            // link
            glLinkProgram(id);
            if (glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE) {
                throw new RuntimeException(String.format("Error linking program\n%s",
                        glGetProgramInfoLog(glGetProgrami(id, GL_INFO_LOG_LENGTH))));
            }
            this.id = id;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static Program createProgram(String vertexShader, String fragmentShader) {
        return new Program("res/shaders/" + vertexShader, "res/shaders/" + fragmentShader);
    }

    private static int getVarSize(String v) {
        switch (v) {
            case "float":
                return 1;
            case "vec2":
                return 2;
            case "vec3":
                return 3;
            case "vec4":
                return 4;
            default:
                return -1;
        }
    }

    private String parseShader(String path, boolean parseAttributes, boolean parseUniforms) throws IOException {
        var file = new File(path);
        var content = new StringBuilder();
        try (var reader = new BufferedReader(new FileReader(file))) {
            reader.lines().forEach(line -> {
                content.append(line);
                content.append('\n');
                if (parseAttributes && line.startsWith("in")) {
                    // shader attribute
                    var args = line.split("\\s+");
                    var size = getVarSize(args[1]);
                    if (size < 0) throw new IllegalArgumentException("Unknown shader attribute " + args[1]);
                    attributes.put(args[2].replace(";", ""), size);
                } else if (parseUniforms && line.startsWith("uniform")) {
                    // uniform variable
                    var args = line.split("\\s+");
                    uniforms.put(args[2].replace(";", ""), args[1]);
                }
            });
        }
        return content.toString();
    }

    public Map<String, Integer> getAttributes() {
        return attributes;
    }

    public Map<String, String> getUniforms() {
        return uniforms;
    }

    public Shader getVertexShader() {
        return vertexShader;
    }

    public Shader getFragmentShader() {
        return fragmentShader;
    }

    public void use() {
        glUseProgram(this.id);
    }

    public int getUniformLocation(String name) {
        var location = glGetUniformLocation(this.id, name);
        if (location < 0)
            throw new RuntimeException(String.format("Uniform %s has no location in program %d", name, id));
        return location;
    }

    public int getAttribLocation(String name) {
        var location = glGetAttribLocation(this.id, name);
        if (location < 0)
            throw new RuntimeException(String.format("MeshAttribute %s has no location in program %d", name, id));
        return location;
    }

    public void writeBool(String name, boolean value) {
        glUniform1i(getUniformLocation(name), value ? 1 : 0);
    }

    public void writeInt(String name, int value) {
        glUniform1i(getUniformLocation(name), value);
    }

    public void writeTexture(String name, Texture tex) {
        glUniform1i(getUniformLocation(name), tex.id);
    }

    public void writeFloat(String name, float value) {
        glUniform1f(getUniformLocation(name), value);
    }

    public void writeVec2(String name, Vector2 value) {
        glUniform2f(getUniformLocation(name), value.x, value.y);
    }

    public void writeVec2(String name, float x, float y) {
        glUniform2f(getUniformLocation(name), x, y);
    }

    public void writeVec3(String name, float x, float y, float z) {
        glUniform3f(getUniformLocation(name), x, y, z);
    }

    public void writeVec3(String name, Vector3 value) {
        glUniform3f(getUniformLocation(name), value.x, value.y, value.z);
    }

    public void writeVec4(String name, float x, float y, float z, float w) {
        glUniform4f(getUniformLocation(name), x, y, z, w);
    }

    public void writeVec4(String name, Vector4 value) {
        glUniform4f(getUniformLocation(name), value.x, value.y, value.z, value.w);
    }

    public void writeVec4(String name, Color color) {
        glUniform4f(getUniformLocation(name), (float) color.getRed(), (float) color.getGreen(), (float) color.getBlue(), (float) color.getOpacity());
    }

    public void writeMat4(String name, Matrix4 mat) {
        glUniformMatrix4fv(getUniformLocation(name), true, mat.getData());
    }
}
