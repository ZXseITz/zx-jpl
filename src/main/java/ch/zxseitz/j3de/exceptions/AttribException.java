package ch.zxseitz.j3de.exceptions;

import ch.zxseitz.j3de.graphics.core.Program;

public class AttribException extends ProgramException {
    private final String name;

    public AttribException(String message, Program program, String name) {
        super(message, program);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
