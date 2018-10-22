package ch.zxseitz.jpl.graphics.mesh;

import ch.zxseitz.jpl.graphics.programs.Program;
import ch.zxseitz.jpl.graphics.Texture;

import java.awt.*;

public class MeshFactory {
    private Program p;

    public MeshFactory(Program p) {
        this.p = p;
    }

    public Mesh createCube(float x, float y, float z, Color color) {
        var mesh = new Mesh(p);
        configCube(mesh, x, y, z, color, false);
        return mesh;
    }

    public MeshTex createCubeTex(float x, float y, float z, Color color, Texture tex) {
        var mesh = new MeshTex(p, tex);
        configCube(mesh, x, y, z, color, true);
        return mesh;
    }

    private void configCube(Mesh mesh, float x, float y, float z, Color color, boolean tex) {

    }
}
