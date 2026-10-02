package bg.sofia.uni.fmi.tdkirov.tradehub.exception.shop;

import bg.sofia.uni.fmi.tdkirov.tradehub.exception.TradeHubException;

public class ShopException extends TradeHubException {
    public ShopException(String message) {
        super(message);
    }

    public ShopException(String message, Throwable cause) {
        super(message, cause);
    }
}
