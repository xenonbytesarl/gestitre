package cm.xenonbyte.gestitre.infrastructure.admin;

import at.favre.lib.crypto.bcrypt.BCrypt;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.admin.ports.primary.PasswordEncryptProvider;
import cm.xenonbyte.gestitre.domain.admin.vo.Password;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

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
    public Password encrypt(Password password) throws Exception {
        return Password.of(Text.of(BCrypt.withDefaults().hashToString(SALT, password.text().value().toCharArray())));
    }
}
