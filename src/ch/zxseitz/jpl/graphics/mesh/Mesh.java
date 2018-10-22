package ch.zxseitz.jpl.graphics.mesh;

import ch.zxseitz.jpl.graphics.programs.Program;
import ch.zxseitz.jpl.utils.Triple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL45.*;

public class Mesh {
  protected int vao, vboIdx;
  protected Map<String, Integer> vbos;
  protected PrimitiveType mode;
  protected int idxLength;

  protected Program program;

  public Mesh(Program program) {
    this.program = program;
    this.vao = glGenVertexArrays();
    glBindVertexArray(this.vao);
    this.vbos = new HashMap<>(5);
  }

  public Program getProgram() {
    return program;
  }

  public int getVao() {
    return vao;
  }

  public int getVboIdx() {
    return vboIdx;
  }

  public int getVbo(String name) {
    return vbos.get(name);
  }

  public void addAll(List<Triple<String, Integer, float[]>> vertices, int[] indices, PrimitiveType mode) {
      this.mode = mode;
      vbos.clear();
      for(var attribute : vertices) {
          var name = attribute.getFirst();
          var size = attribute.getSecond();
          var data = attribute.getThird();
          vbos.put(name, registerFloatArray(name, size, data));
      }
      vboIdx = registerIndexArray(indices);
  }

  protected int registerFloatArray(String name, int size, float[] data) {
    var id = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, id);
    glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
    var location = program.getAttribLocation(name);
    glEnableVertexAttribArray(location);
    glBindBuffer(GL_ARRAY_BUFFER, id);
    glVertexAttribPointer(location, size, GL_FLOAT, false, 0, 0);
    return id;
  }

  protected int registerIndexArray(int[] indices) {
    idxLength = indices.length;
    var id = glGenBuffers();
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
    return id;
  }

  public void render() {
    glBindVertexArray(vao);
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboIdx);
    glDrawElements(mode.type, idxLength, GL_UNSIGNED_INT, 0);
  }
}
