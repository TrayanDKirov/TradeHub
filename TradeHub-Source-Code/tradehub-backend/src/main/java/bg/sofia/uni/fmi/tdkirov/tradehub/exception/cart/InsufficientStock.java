package bg.sofia.uni.fmi.tdkirov.tradehub.exception.cart;

public class InsufficientStock extends CartException {
    public InsufficientStock(String message) {
        super(message);
    }

    public InsufficientStock(String message, Throwable cause) {
        super(message, cause);
    }
}