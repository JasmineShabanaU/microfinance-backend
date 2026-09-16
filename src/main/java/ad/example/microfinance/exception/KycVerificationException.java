package ad.example.microfinance.exception;

public class KycVerificationException extends RuntimeException {
    public KycVerificationException(String message) {
        super(message);
    }
}
