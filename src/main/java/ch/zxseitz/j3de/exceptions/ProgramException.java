package ch.zxseitz.j3de.exceptions;

import ch.zxseitz.j3de.graphics.core.Program;

public class ProgramException extends J3deException {
    private final Program program;

    public ProgramException(String message, Program program) {
        super(message);
        this.program = program;
    }

    public Program getProgram() {
        return program;
    }
}
