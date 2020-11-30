package ch.zxseitz.j3de.graphics.scene.components;

import ch.zxseitz.j3de.exceptions.J3deException;
import ch.zxseitz.j3de.graphics.mesh.Mesh;
import ch.zxseitz.j3de.graphics.scene.Actor;
import ch.zxseitz.j3de.math.Matrix4;

public class MeshComponent implements IComponent {
    private Mesh mesh;

    public MeshComponent(Mesh mesh) {
        this.mesh = mesh;
    }

    @Override
    public void update(double delta, Actor actor, Matrix4 t) throws J3deException {
        if (mesh != null) {
            //todo generalize
            var program = mesh.getProgram();
            //todo implement scene program to reduce program switch
            program.use();
            program.writeUniform("P", actor.getScene().getCamera().getProjection());
            program.writeUniform("T", t);
            mesh.render();
        }
    }
}
