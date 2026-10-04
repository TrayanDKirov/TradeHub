package bg.sofia.uni.fmi.tdkirov.tradehub.exception.cart;

import bg.sofia.uni.fmi.tdkirov.tradehub.exception.TradeHubException;

public class CartException extends TradeHubException {
    public CartException(String message) {
        super(message);
    }

    public CartException(String message, Throwable cause) {
        super(message, cause);
    }
}