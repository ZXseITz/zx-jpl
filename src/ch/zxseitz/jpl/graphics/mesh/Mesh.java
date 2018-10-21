package ch.zxseitz.jpl.graphics.mesh;

import ch.zxseitz.jpl.graphics.Program;
import ch.zxseitz.jpl.graphics.Shader;

public class Mesh extends AbstractMesh {
    public Mesh(Program p) {
        super(p);
        //TODO check shader attributes
    }

    @Override
    public void addAll(float[][] vertices, int[] indices, PrimitiveType mode) {
        this.mode = mode;
        vbos.clear();
        for (int i = 0; i < 3; i++) {
            var attr = Shader.shaderAttributes[i];
            vbos.put(attr.getKey(), registerFloatArray(attr.getKey(), attr.getValue(), vertices[i]));
        }
        vboIdx = registerIndexArray(indices);
    }
}
