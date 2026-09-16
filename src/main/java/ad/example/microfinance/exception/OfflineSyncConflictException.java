package ad.example.microfinance.exception;

public class OfflineSyncConflictException extends RuntimeException {
    public OfflineSyncConflictException(String message) {
        super(message);
    }
}
