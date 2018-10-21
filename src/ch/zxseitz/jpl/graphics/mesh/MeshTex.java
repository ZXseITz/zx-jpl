package ch.zxseitz.jpl.graphics.mesh;

import ch.zxseitz.jpl.graphics.Program;
import ch.zxseitz.jpl.graphics.Shader;
import ch.zxseitz.jpl.graphics.Texture;

public class MeshTex extends AbstractMesh {
    private Texture tex;

    public MeshTex(Program p, Texture tex) {
        super(p);
        if (tex == null) throw new RuntimeException("Texture cannot be null"); //TODO use annotation
        this.tex = tex;
        //TODO check shader attributes
    }

    @Override
    public void addAll(float[][] vertices, int[] indices, PrimitiveType mode) {
        this.mode = mode;
        vbos.clear();
        for (int i = 0; i < 4; i++) {
            var attr = Shader.shaderAttributes[i];
            vbos.put(attr.getKey(), registerFloatArray(attr.getKey(), attr.getValue(), vertices[i]));
        }
        vboIdx = registerIndexArray(indices);
    }

    public Texture getTexture() {
        return tex;
    }
}
