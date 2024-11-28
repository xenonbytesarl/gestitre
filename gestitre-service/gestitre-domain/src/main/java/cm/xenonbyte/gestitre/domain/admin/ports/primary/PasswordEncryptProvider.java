package cm.xenonbyte.gestitre.domain.admin.ports.primary;

import cm.xenonbyte.gestitre.domain.admin.vo.Password;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public interface PasswordEncryptProvider {
    Password encrypt(Password password);

    Boolean checkCredentials(Password password, Password encryptedPassword);
}
