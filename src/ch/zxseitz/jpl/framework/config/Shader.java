package ch.zxseitz.jpl.framework.config;

import ch.zxseitz.jpl.framework.Utils;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
  public enum ShaderType {
    VERTEX_SHADER,
    FRAGMENT_SHADER
  }

  public final String name;
  public final int id;
  public final ShaderType type;

  public Shader(String name, ShaderType type) {
    this.name = name;
    this.type = type;

    String source = Utils.readFile(String.format("shaders/%s.glsl", name), Utils.utf8);
    var id = glCreateShader(type == ShaderType.VERTEX_SHADER ? GL_VERTEX_SHADER : GL_FRAGMENT_SHADER);
    glCompileShader(id);

    if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
      throw new RuntimeException(String.format("Error creating shader %s\n%s", name,
          glGetShaderInfoLog(id, glGetShaderi(id, GL_INFO_LOG_LENGTH))));
    }

    this.id = id;
  }
}
