package bg.sofia.uni.fmi.tdkirov.tradehub.exception.shop;

public class ShopAccessDenied extends ShopException {
    public ShopAccessDenied(String message) {
        super(message);
    }

    public ShopAccessDenied(String message, Throwable cause) {
        super(message, cause);
    }
}