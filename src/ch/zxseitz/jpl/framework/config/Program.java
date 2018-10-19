package ch.zxseitz.jpl.framework.config;

import ch.zxseitz.jpl.framework.math.Matrix4;
import ch.zxseitz.jpl.framework.math.Vector2;
import ch.zxseitz.jpl.framework.math.Vector3;
import ch.zxseitz.jpl.framework.math.Vector4;
import javafx.scene.paint.Color;

import java.io.File;

import static org.lwjgl.opengl.GL45.*;

public class Program {
  public final int id;
  private final Shader vertexShader, fragmentShader;

  public Program(String vertexShader, String fragmentShader) {
    var id = glCreateProgram();
    this.vertexShader = new Shader(vertexShader, Shader.ShaderType.VERTEX_SHADER);
    glAttachShader(id, this.vertexShader.id);
    this.fragmentShader = new Shader(fragmentShader, Shader.ShaderType.FRAGMENT_SHADER);
    glAttachShader(id, this.fragmentShader.id);
    glLinkProgram(id);
    if (glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE) {
      throw new RuntimeException(String.format("Error linking program\n%s",
          glGetProgramInfoLog(glGetProgrami(id, GL_INFO_LOG_LENGTH))));
    }
    this.id = id;
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

  public int getLocation(String name) {
    return glGetUniformLocation(this.id, name);
  }

  public void writeBool(String name, boolean value) {
    glUniform1i(getLocation(name), value ? 1 : 0);
  }

  public void writeInt(String name, int value) {
    glUniform1i(getLocation(name), value);
  }

  public void writeFloat(String name, float value) {
    glUniform1f(getLocation(name), value);
  }

  public void writeVec2(String name, Vector2 value) {
    glUniform2f(getLocation(name), value.x, value.y);
  }

  public void writeVec2(String name, float x, float y) {
    glUniform2f(getLocation(name), x, y);
  }

  public void writeVec3(String name, float x, float y, float z) {
    glUniform3f(getLocation(name), x, y, z);
  }

  public void writeVec3(String name, Vector3 value) {
    glUniform3f(getLocation(name), value.x, value.y, value.z);
  }

  public void writeVec4(String name, float x, float y, float z, float w) {
    glUniform4f(getLocation(name), x, y, z, w);
  }

  public void writeVec4(String name, Vector4 value) {
    glUniform4f(getLocation(name), value.x, value.y, value.z, value.w);
  }

  public void writeVec4(String name, Color color) {
    glUniform4f(getLocation(name), (float) color.getRed(), (float) color.getGreen(), (float) color.getBlue(), (float) color.getOpacity());
  }

  public void writeMat4(String name, Matrix4 mat) {
    //TODO: check transpose
    glUniformMatrix4fv(getLocation(name), false, mat.data);
  }
}
