package bg.sofia.uni.fmi.tdkirov.tradehub.exception.user;

public class InvalidPasswordException extends UserException {
    public InvalidPasswordException() {
        super("Invalid password. ");
    }

    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException(String message, Throwable cause) {
        super(message);
    }
}
