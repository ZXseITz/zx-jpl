package ch.zxseitz.jpl.framework.mesh;

import ch.zxseitz.jpl.framework.config.Program;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL45.*;

public abstract class AbstractMesh {
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

  protected int vao, vboIdx;
  protected Map<String, Integer> vbos;
  protected PrimitiveType mode;
  protected int idxLength;

  protected Program program;

  public AbstractMesh(Program program) {
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

  public abstract void addAll(float[][] vertices, int[] indices, PrimitiveType mode);

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
