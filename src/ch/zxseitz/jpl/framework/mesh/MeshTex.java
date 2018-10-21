package ch.zxseitz.jpl.framework.mesh;

import ch.zxseitz.jpl.framework.config.Program;
import ch.zxseitz.jpl.framework.config.Shader;
import ch.zxseitz.jpl.framework.config.Texture;

public class MeshTex extends AbstractMesh {
    private Texture tex;

    public MeshTex(Program p, Texture tex) {
        super(p);
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
