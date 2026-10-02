package bg.sofia.uni.fmi.tdkirov.tradehub.exception.shop;

public class ShopNotFound extends ShopException {
    public ShopNotFound(String message) {
        super(message);
    }

    public ShopNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}