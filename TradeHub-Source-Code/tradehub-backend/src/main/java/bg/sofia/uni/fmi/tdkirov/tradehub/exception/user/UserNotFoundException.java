package bg.sofia.uni.fmi.tdkirov.tradehub.exception.user;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(String username, int dummy) {
        super("User with username " + username + " was not found. ");
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
