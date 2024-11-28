package cm.xenonbyte.gestitre.application.admin;

import cm.xenonbyte.gestitre.application.admin.dto.CreateUserViewRequest;
import cm.xenonbyte.gestitre.application.admin.dto.CreateUserViewResponse;
import cm.xenonbyte.gestitre.application.admin.dto.LoginRequest;
import cm.xenonbyte.gestitre.application.admin.dto.LoginResponse;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;

/**
 * @author bamk
 * @version 1.0
 * @since 24/11/2024
 */
public interface UserApplicationAdapter {
    @Nonnull @Valid CreateUserViewResponse createUser(@Nonnull @Valid CreateUserViewRequest createUserViewRequest);

    LoginResponse login(@Nonnull @Valid LoginRequest loginRequest);
}
