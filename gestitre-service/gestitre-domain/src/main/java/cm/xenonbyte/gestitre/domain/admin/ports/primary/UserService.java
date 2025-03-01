package cm.xenonbyte.gestitre.domain.admin.ports.primary;

import cm.xenonbyte.gestitre.domain.admin.User;
import cm.xenonbyte.gestitre.domain.admin.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.admin.event.UserUpdatedEvent;
import cm.xenonbyte.gestitre.domain.admin.vo.Token;
import cm.xenonbyte.gestitre.domain.common.vo.Code;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.common.vo.Password;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.common.vo.UserId;
import jakarta.annotation.Nonnull;

/**
 * @author bamk
 * @version 1.0
 * @since 09/11/2024
 */
public interface UserService {
    @Nonnull UserCreatedEvent createUser(@Nonnull User user);

    @Nonnull UserUpdatedEvent updateUser(@Nonnull UserId userId, @Nonnull User newUser);

    @Nonnull User login(@Nonnull Code tenantCode, @Nonnull Email email, @Nonnull Password password);

    @Nonnull Token generateToken(@Nonnull User user);

    @Nonnull User activateUser(User user);

    void resetPassword(@Nonnull Password oldPassword, @Nonnull Password newPassword, @Nonnull User user);

    @Nonnull User findUserByEmail(@Nonnull Email email);

    User findUserById(UserId userId);

    @Nonnull PageInfo<User> searchUsers(PageInfoPage pageInfoPage, PageInfoSize pageInfoSize, PageInfoField pageInfoField, PageInfoDirection pageInfoDirection, Keyword keyword);

    @Nonnull Text refreshAccessToken(Text refreshToken);
}
