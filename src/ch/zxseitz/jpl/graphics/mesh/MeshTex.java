package ch.zxseitz.jpl.graphics.mesh;

import ch.zxseitz.jpl.graphics.programs.Program;
import ch.zxseitz.jpl.graphics.Texture;

public class MeshTex extends Mesh {
    private Texture tex;

    public MeshTex(Program p, Texture tex) {
        super(p);
        if (tex == null) throw new RuntimeException("Texture cannot be null"); //TODO use annotation
        this.tex = tex;
        //TODO check shader attributes
    }

    public Texture getTexture() {
        return tex;
    }
}
