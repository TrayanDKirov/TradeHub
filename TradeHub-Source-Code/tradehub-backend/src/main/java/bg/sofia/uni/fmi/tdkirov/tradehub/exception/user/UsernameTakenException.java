package bg.sofia.uni.fmi.tdkirov.tradehub.exception.user;

public class UsernameTakenException extends UserException {
    public UsernameTakenException(String username, int dummy) {
        super("Username " + username + " is already taken. ");
    }

    public UsernameTakenException(String message) {
        super(message);
    }

    public UsernameTakenException(String message, Throwable cause) {
        super(message, cause);
    }
}
