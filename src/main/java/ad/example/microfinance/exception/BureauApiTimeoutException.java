package ad.example.microfinance.exception;

public class BureauApiTimeoutException extends RuntimeException {
    public BureauApiTimeoutException(String message) {
        super(message);
    }
}
