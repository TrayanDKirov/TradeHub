package bg.sofia.uni.fmi.tdkirov.tradehub.exception.user.seller;

import bg.sofia.uni.fmi.tdkirov.tradehub.exception.user.UserException;

public class SellerException extends UserException {
    public SellerException(String message) {
        super(message);
    }

    public SellerException(String message, Throwable cause) {
        super(message, cause);
    }
}