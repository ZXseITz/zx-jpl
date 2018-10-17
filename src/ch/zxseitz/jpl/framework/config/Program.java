package ch.zxseitz.jpl.framework.config;

import static org.lwjgl.opengl.GL20.*;

public class Program {
  public final int id;
  public final Shader vertexShader, fragmentShader;

  public Program(String vertexShader, String fragmentShader) {
    var id = glCreateProgram();
    this.vertexShader = new Shader(vertexShader, Shader.ShaderType.VERTEX_SHADER);
    this.fragmentShader = new Shader(fragmentShader, Shader.ShaderType.FRAGMENT_SHADER);
    glLinkProgram(id);
    if (glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE) {
      throw new RuntimeException(String.format("Error linking program\n%s",
          glGetProgramInfoLog(glGetProgrami(id, GL_INFO_LOG_LENGTH))));
    }
    this.id = id;
  }

  public void use() {
    glUseProgram(this.id);
  }

  private int getLocation(String name) {
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

  public void writeVec2(String name, float[] value) {
    glUniform2fv(getLocation(name), value);
  }

  public void writeVec3(String name, float[] value) {
    glUniform3fv(getLocation(name), value);
  }

  public void writeVec4(String name, float[] value) {
    glUniform4fv(getLocation(name), value);
  }

  public void writeMat4(String name, float[] value) {
    //TODO: check transpose
    glUniformMatrix4fv(getLocation(name), false, value);
  }
}
