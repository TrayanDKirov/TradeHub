package bg.sofia.uni.fmi.tdkirov.tradehub.exception.user;

public class InvalidRoleException extends UserException {
    public InvalidRoleException(String message) {
        super(message);
    }

    public InvalidRoleException(String message, Throwable cause) {
        super(message, cause);
    }
}