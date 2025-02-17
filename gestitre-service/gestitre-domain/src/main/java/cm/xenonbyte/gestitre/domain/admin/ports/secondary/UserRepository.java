package cm.xenonbyte.gestitre.domain.admin.ports.secondary;

import cm.xenonbyte.gestitre.domain.admin.User;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.common.vo.UserId;
import jakarta.annotation.Nonnull;

import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public interface UserRepository {
    Boolean existsByEmail(@Nonnull Email email);

    User create(@Nonnull User user);

    Optional<User> findByEmail(@Nonnull Email email);

    Optional<User> findById(@Nonnull UserId userId);

    User update(@Nonnull UserId userId, @Nonnull User newUser);

    PageInfo<User> search(@Nonnull PageInfoPage pageInfoPage, @Nonnull PageInfoSize pageInfoSize, @Nonnull PageInfoField pageInfoField, @Nonnull PageInfoDirection pageInfoDirection, @Nonnull Keyword keyword);
}
