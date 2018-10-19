package ch.zxseitz.jpl.framework.mesh;

import ch.zxseitz.jpl.framework.config.Program;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL45.*;

public class Mesh {
  public enum PrimitiveType {
    POINTS(GL_POINTS),
    LINES(GL_LINES),
    LINE_LOOP(GL_LINE_LOOP),
    TRIANGLES(GL_TRIANGLES),
    TRIANGLE_STRIP(GL_TRIANGLE_STRIP),
    TRIANGLE_FAN(GL_TRIANGLE_FAN);

    public final int type;
    PrimitiveType(int type) {this.type = type;}
  }

  private int vao, vboIdx;
  private Map<String, Integer> vbos;
  private PrimitiveType mode;
  private int idxLength;

  private Program program;

  public Mesh(Program program) {
    this.program = program;
    this.vao = glCreateVertexArrays();
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

  //TODO: improve method
  public void addAll(Pair<Pair<String, Integer>, float[]>[] vertices, int[] indices, PrimitiveType mode) {
    this.mode = mode;
    vbos.clear();
    for(var pair : vertices) {
      var sv = pair.getKey();
      var name = sv.getKey();
      var size = sv.getValue();
      var data = pair.getValue();
      vbos.put(name, registerFloatArray(name, size, data));
    }
    vboIdx = registerIndexArray(indices);
  }

  private int registerFloatArray(String name, int size, float[] data) {
    var id = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, id);
    glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
    glEnableVertexAttribArray(program.getLocation(name));
    glBindBuffer(GL_ARRAY_BUFFER, id);
    glVertexAttribPointer(program.getLocation(name), size, GL_FLOAT, false, 0, 0);
    return id;
  }

  private int registerIndexArray(int[] indices) {
    idxLength = indices.length;
    var id = glGenBuffers();
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
    return id;
  }

  public void render() {
    glBindVertexArray(vao);
    glBindBuffer(GL_ARRAY_BUFFER, vboIdx);
    glDrawElements(mode.type, idxLength, GL_UNSIGNED_INT, 0);
  }
}
