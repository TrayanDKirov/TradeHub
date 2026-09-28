package bg.sofia.uni.fmi.tdkirov.tradehub.exception.user;

import bg.sofia.uni.fmi.tdkirov.tradehub.exception.TradeHubException;

public class UserException extends TradeHubException {
    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }
}
