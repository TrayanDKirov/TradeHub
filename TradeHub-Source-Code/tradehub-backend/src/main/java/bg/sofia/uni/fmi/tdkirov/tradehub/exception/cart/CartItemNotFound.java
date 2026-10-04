package bg.sofia.uni.fmi.tdkirov.tradehub.exception.cart;

public class CartItemNotFound extends CartException {
    public CartItemNotFound(String message) {
        super(message);
    }

    public CartItemNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}