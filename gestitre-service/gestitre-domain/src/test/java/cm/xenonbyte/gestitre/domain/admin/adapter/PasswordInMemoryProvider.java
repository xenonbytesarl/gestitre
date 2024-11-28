package cm.xenonbyte.gestitre.domain.admin.adapter;

import cm.xenonbyte.gestitre.domain.admin.ports.primary.PasswordEncryptProvider;
import cm.xenonbyte.gestitre.domain.admin.vo.Password;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public final class PasswordInMemoryProvider implements PasswordEncryptProvider {


    @Override
    public Password encrypt(Password password)  {
        return password;
    }

    @Override
    public Boolean checkCredentials(Password password, Password encryptedPassword) {
        Password encrypt = encrypt(password);
        return encrypt.text().value().matches(encryptedPassword.text().value());
    }
}
