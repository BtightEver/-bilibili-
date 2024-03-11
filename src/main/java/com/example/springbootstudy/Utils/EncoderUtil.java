package com.example.springbootstudy.Utils;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

public class EncoderUtil {

    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "ThisIsASecretKey";

    public static String encode(String password) {
        try {
            Key secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
            return new String(encryptedBytes, StandardCharsets.ISO_8859_1);
        } catch (Exception e) {
            // 处理异常
            e.printStackTrace();
        }
        return null;
    }

    public static String decode(String encodedPassword) {
        try {
            Key secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedBytes = cipher.doFinal(encodedPassword.getBytes(StandardCharsets.ISO_8859_1));
            return new String(decodedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            // 处理异常
            e.printStackTrace();
        }
        return null;
    }

}