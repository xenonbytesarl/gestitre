package cm.xenonbyte.gestitre.domain.security.ports.primary;

import cm.xenonbyte.gestitre.domain.security.vo.Password;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public interface PasswordEncryptProvider {
    Password encrypt(Password password) throws Exception;
}
