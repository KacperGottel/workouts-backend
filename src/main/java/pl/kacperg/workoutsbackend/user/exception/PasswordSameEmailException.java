package pl.kacperg.workoutsbackend.user.exception;

public class PasswordSameEmailException  extends Exception {
    public PasswordSameEmailException(String message) {
        super(message);
    }
}
