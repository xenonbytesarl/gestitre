package cm.xenonbyte.gestitre.application.admin;

import cm.xenonbyte.gestitre.application.admin.dto.ActivateAccountRequest;
import cm.xenonbyte.gestitre.application.admin.dto.ActivateUserResponse;
import cm.xenonbyte.gestitre.application.admin.dto.CreateUserViewRequest;
import cm.xenonbyte.gestitre.application.admin.dto.CreateUserViewResponse;
import cm.xenonbyte.gestitre.application.admin.dto.LoginRequest;
import cm.xenonbyte.gestitre.application.admin.dto.LoginResponse;
import cm.xenonbyte.gestitre.application.admin.dto.ResendVerificationCodeRequest;
import cm.xenonbyte.gestitre.application.admin.dto.VerifyCodeResponse;
import cm.xenonbyte.gestitre.application.admin.dto.VerifyCodeRequest;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;

/**
 * @author bamk
 * @version 1.0
 * @since 24/11/2024
 */
public interface UserApplicationAdapter {
    @Nonnull @Valid CreateUserViewResponse createUser(@Nonnull @Valid CreateUserViewRequest createUserViewRequest);

    @Nonnull @Valid LoginResponse login(@Nonnull @Valid LoginRequest loginRequest);

    @Nonnull @Valid
    VerifyCodeResponse verifyCode(@Nonnull @Valid VerifyCodeRequest verifyCodeRequest);

    @Nonnull @Valid LoginResponse resendMfaVerification(@Nonnull @Valid ResendVerificationCodeRequest resendVerificationCodeRequest);

    @Nonnull @Valid ActivateUserResponse activateUser(@Nonnull @Valid ActivateAccountRequest activateAccountRequest);
}
