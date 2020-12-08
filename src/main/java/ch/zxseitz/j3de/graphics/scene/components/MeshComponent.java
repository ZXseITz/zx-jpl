package ch.zxseitz.j3de.graphics.scene.components;

import ch.zxseitz.j3de.exceptions.J3deException;
import ch.zxseitz.j3de.graphics.mesh.Mesh;
import ch.zxseitz.j3de.graphics.scene.Actor;
import ch.zxseitz.j3de.math.Matrix4;
import ch.zxseitz.j3de.utils.ErrorUtils;

import static org.lwjgl.opengl.GL45.*;

public class MeshComponent implements IComponent {
    private Mesh mesh;

    public MeshComponent(Mesh mesh) {
        this.mesh = mesh;
    }

    @Override
    public void update(double delta, Actor actor, Matrix4 t) throws J3deException {
        if (mesh != null) {
            var program = mesh.getProgram();
            //todo implement scene program to reduce program switch
            program.use();
            //todo generalize
            program.writeUniform("P", actor.getScene().getCamera().getProjection());
            program.writeUniform("T", t);
            var texture = mesh.getTexture();
            if (texture != null) {
                program.writeUniform("tex", texture.id);
            }

            // render mesh
            glBindVertexArray(mesh.getVao());
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getEbo());
            glDrawRangeElementsBaseVertex( mesh.getMode().id, mesh.getStart(), mesh.getEnd(),
                    mesh.count(), GL_UNSIGNED_INT, 0, mesh.getVertexStart());

            var error = glGetError();
            if (error != GL_NO_ERROR) {
                throw new J3deException(ErrorUtils.getErrorInfo(error));
            }
        }
    }
}
