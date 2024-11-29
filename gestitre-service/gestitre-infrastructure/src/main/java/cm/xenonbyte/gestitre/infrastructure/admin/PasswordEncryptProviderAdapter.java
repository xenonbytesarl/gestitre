package cm.xenonbyte.gestitre.infrastructure.admin;

import cm.xenonbyte.gestitre.domain.admin.ports.primary.PasswordEncryptProvider;
import cm.xenonbyte.gestitre.domain.common.vo.Password;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import static at.favre.lib.crypto.bcrypt.BCrypt.Result;
import static at.favre.lib.crypto.bcrypt.BCrypt.verifyer;
import static at.favre.lib.crypto.bcrypt.BCrypt.withDefaults;

/**
 * @author bamk
 * @version 1.0
 * @since 24/11/2024
 */
@Slf4j
@ApplicationScoped
public final class PasswordEncryptProviderAdapter implements PasswordEncryptProvider {


    public static final int SALT = 12;

    @Override
    public Password encrypt(Password password) {
        return Password.of(Text.of(withDefaults().hashToString(SALT, password.text().value().toCharArray())));
    }

    @Override
    public Boolean checkCredentials(Password password, Password encryptedPassword) {
        Result result = verifyer().verify(password.text().value().toCharArray(), encryptedPassword.text().value().toCharArray());
        return result.verified;
    }
}
