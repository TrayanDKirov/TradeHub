package bg.sofia.uni.fmi.tdkirov.tradehub.exception.shopitem;

import bg.sofia.uni.fmi.tdkirov.tradehub.exception.TradeHubException;

public class ShopItemException extends TradeHubException {
    public ShopItemException(String message) {
        super(message);
    }

    public ShopItemException(String message, Throwable cause) {
        super(message, cause);
    }
}