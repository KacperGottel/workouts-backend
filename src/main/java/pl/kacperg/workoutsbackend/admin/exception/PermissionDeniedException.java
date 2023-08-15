package pl.kacperg.workoutsbackend.admin.exception;

public class PermissionDeniedException extends Exception {
    public PermissionDeniedException(String message) {
        super(message);
    }
}
