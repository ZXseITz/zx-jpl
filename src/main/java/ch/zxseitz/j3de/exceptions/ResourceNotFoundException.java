package ch.zxseitz.j3de.exceptions;

public class ResourceNotFoundException extends J3deException {
    public ResourceNotFoundException(String path) {
        super("No resource was found: " + path);
    }
}
