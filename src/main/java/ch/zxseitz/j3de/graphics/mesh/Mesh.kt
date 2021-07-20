package ch.zxseitz.j3de.graphics.mesh;


import ch.zxseitz.j3de.graphics.Texture;
import ch.zxseitz.j3de.graphics.core.PrimitiveType;
import ch.zxseitz.j3de.graphics.core.Program;

data class Mesh(val program: Program, val vao: Int, val ebo: Int, val start: Int, val end: Int,
                val vertexStart: Int, val mode: PrimitiveType) {
    var texture: Texture? = null;
    //todo texture

    fun count(): Int {
        return end - start + 1;
    }
}
