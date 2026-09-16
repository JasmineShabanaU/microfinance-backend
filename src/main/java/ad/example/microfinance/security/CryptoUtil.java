package ad.example.microfinance.security;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Component
public class CryptoUtil {

    private static final String AES_KEY_STRING = "MicroFinanceSecKeyForAES256Req15"; // 32 chars = 256 bits
    private static final String AES_ALGO = "AES";

    public static String hashAadhaar(String aadhaar) {
        if (aadhaar == null || aadhaar.isBlank()) return "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(aadhaar.trim().getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing Aadhaar", e);
        }
    }

    public static String encrypt(String value) {
        if (value == null || value.isBlank()) return "";
        try {
            SecretKeySpec secretKey = new SecretKeySpec(AES_KEY_STRING.getBytes(StandardCharsets.UTF_8), AES_ALGO);
            Cipher cipher = Cipher.getInstance(AES_ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            return value;
        }
    }

    public static String decrypt(String encryptedValue) {
        if (encryptedValue == null || encryptedValue.isBlank()) return "";
        try {
            SecretKeySpec secretKey = new SecretKeySpec(AES_KEY_STRING.getBytes(StandardCharsets.UTF_8), AES_ALGO);
            Cipher cipher = Cipher.getInstance(AES_ALGO);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return encryptedValue;
        }
    }
}
