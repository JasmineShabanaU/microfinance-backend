package ad.example.microfinance.exception;

public class DisbursementFailedException extends RuntimeException {
    public DisbursementFailedException(String message) {
        super(message);
    }
}
