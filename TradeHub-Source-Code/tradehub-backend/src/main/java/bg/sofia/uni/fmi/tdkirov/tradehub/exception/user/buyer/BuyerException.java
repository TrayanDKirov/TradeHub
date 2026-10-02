package bg.sofia.uni.fmi.tdkirov.tradehub.exception.user.buyer;

import bg.sofia.uni.fmi.tdkirov.tradehub.exception.user.UserException;

public class BuyerException extends UserException {
    public BuyerException(String message) {
        super(message);
    }

    public BuyerException(String message, Throwable cause) {
        super(message, cause);
    }
}