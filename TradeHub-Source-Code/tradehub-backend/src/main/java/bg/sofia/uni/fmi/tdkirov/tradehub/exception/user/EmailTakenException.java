package bg.sofia.uni.fmi.tdkirov.tradehub.exception.user;

public class EmailTakenException extends UserException {
    public EmailTakenException(String email, int dummy) {
        super("Email " + email + " is already taken. ");
    }

    public EmailTakenException(String message) {
        super(message);
    }

    public EmailTakenException(String message, Throwable cause) {
        super(message, cause);
    }
}