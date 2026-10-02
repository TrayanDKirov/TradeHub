package bg.sofia.uni.fmi.tdkirov.tradehub.exception.user.seller;

public class SellerNotFound extends SellerException {
    public SellerNotFound(String message) {
        super(message);
    }

    public SellerNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
