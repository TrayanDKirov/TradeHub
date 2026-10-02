package bg.sofia.uni.fmi.tdkirov.tradehub.exception.file;

import bg.sofia.uni.fmi.tdkirov.tradehub.exception.TradeHubException;

public class FileStorageException extends TradeHubException {
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}