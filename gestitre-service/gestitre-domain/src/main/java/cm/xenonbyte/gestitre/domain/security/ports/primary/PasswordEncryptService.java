package cm.xenonbyte.gestitre.domain.security.ports.primary;

import cm.xenonbyte.gestitre.domain.security.vo.Password;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public interface PasswordEncryptService {
    Password encrypt(Password password) throws Exception;
}
