package bg.sofia.uni.fmi.tdkirov.tradehub.exception;

public class TradeHubException extends RuntimeException {
    public TradeHubException(String message) {
        super(message);
    }

    public TradeHubException(String message, Throwable cause) {
        super(message, cause);
    }
}
