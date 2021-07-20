package ch.zxseitz.j3de.graphics.scene.components;

import ch.zxseitz.j3de.J3deException
import ch.zxseitz.j3de.graphics.mesh.Mesh;
import ch.zxseitz.j3de.graphics.scene.Actor;
import ch.zxseitz.j3de.math.Matrix4;
import ch.zxseitz.j3de.utils.ErrorUtils;

import org.lwjgl.opengl.GL45.*;

class MeshComponent(private val mesh: Mesh?): IComponent {
     override fun update(delta: Double, actor: Actor, t: Matrix4) {
        if (mesh != null) {
            val program = mesh.program;
            //todo implement scene program to reduce program switch
            program.use();
            //todo generalize
            program.writeUniform("P", actor.scene.camera.projection);
            program.writeUniform("T", t);
            val texture = mesh.texture;
            if (texture != null) {
                program.writeUniform("tex", texture.id);
            }

            // render mesh
            glBindVertexArray(mesh.vao);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.ebo);
            glDrawRangeElementsBaseVertex( mesh.mode.id, mesh.start, mesh.end,
                    mesh.count(), GL_UNSIGNED_INT, 0, mesh.vertexStart);

            val error = glGetError();
            if (error != GL_NO_ERROR) {
                throw J3deException(ErrorUtils.getErrorInfo(error));
            }
        }
    }
}
