package bg.sofia.uni.fmi.tdkirov.tradehub.exception.shopitem;

public class ShopItemNotFound extends ShopItemException {
    public ShopItemNotFound(String message) {
        super(message);
    }

    public ShopItemNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}