package cm.xenonbyte.gestitre.domain.security.adapter;

import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.security.ports.primary.PasswordEncryptService;
import cm.xenonbyte.gestitre.domain.security.vo.Password;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public final class PasswordInMemoryService implements PasswordEncryptService {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int KEY_SIZE = 256; // bits
    private static final int IV_SIZE = 12; // bytes
    private static final int TAG_LENGTH_BIT = 128;

    @Override
    public Password encrypt(Password password) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);

        // Generate IV (Initialization Vector) for GCM
        byte[] iv = new byte[IV_SIZE];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        GCMParameterSpec gcmSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
        cipher.init(Cipher.ENCRYPT_MODE, generateSecretKey(), gcmSpec);

        byte[] encryptedBytes = cipher.doFinal(password.text().value().getBytes(StandardCharsets.UTF_8));

        // Combine IV and encrypted part for storage
        byte[] encryptedDataWithIv = new byte[iv.length + encryptedBytes.length];
        System.arraycopy(iv, 0, encryptedDataWithIv, 0, iv.length);
        System.arraycopy(encryptedBytes, 0, encryptedDataWithIv, iv.length, encryptedBytes.length);
        return Password.of(Text.of(Base64.getEncoder().encodeToString(encryptedDataWithIv)));
    }

    public static SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(KEY_SIZE);
        return keyGen.generateKey();
    }
}
