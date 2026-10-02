package bg.sofia.uni.fmi.tdkirov.tradehub.exception.user.buyer;

public class BuyerNotFoundException extends BuyerException {
    public BuyerNotFoundException(String message) {
        super(message);
    }

    public BuyerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
