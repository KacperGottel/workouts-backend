package pl.kacperg.workoutsbackend.exercise.exception;

public class ExerciseAlreadyExistsException extends Exception {
    public ExerciseAlreadyExistsException(String message) {
        super(message);
    }
}
