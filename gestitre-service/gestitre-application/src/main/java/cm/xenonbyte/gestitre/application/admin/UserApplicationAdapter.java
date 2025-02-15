package cm.xenonbyte.gestitre.application.admin;

import cm.xenonbyte.gestitre.application.admin.dto.ActivateAccountRequest;
import cm.xenonbyte.gestitre.application.admin.dto.ActivateUserResponse;
import cm.xenonbyte.gestitre.application.admin.dto.CreateUserViewRequest;
import cm.xenonbyte.gestitre.application.admin.dto.CreateUserViewResponse;
import cm.xenonbyte.gestitre.application.admin.dto.FindUserByIdViewResponse;
import cm.xenonbyte.gestitre.application.admin.dto.LoginRequest;
import cm.xenonbyte.gestitre.application.admin.dto.LoginResponse;
import cm.xenonbyte.gestitre.application.admin.dto.ResendVerificationCodeRequest;
import cm.xenonbyte.gestitre.application.admin.dto.ResetPasswordRequest;
import cm.xenonbyte.gestitre.application.admin.dto.SearchUserPageInfoViewResponse;
import cm.xenonbyte.gestitre.application.admin.dto.SendResetPasswordCodeRequest;
import cm.xenonbyte.gestitre.application.admin.dto.UpdateUserViewRequest;
import cm.xenonbyte.gestitre.application.admin.dto.UpdateUserViewResponse;
import cm.xenonbyte.gestitre.application.admin.dto.VerifyCodeRequest;
import cm.xenonbyte.gestitre.application.admin.dto.VerifyCodeResponse;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;

import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 24/11/2024
 */
public interface UserApplicationAdapter {
    @Nonnull @Valid CreateUserViewResponse createUser(@Nonnull @Valid CreateUserViewRequest createUserViewRequest);

    @Nonnull @Valid LoginResponse login(@Nonnull @Valid LoginRequest loginRequest);

    @Nonnull @Valid VerifyCodeResponse verifyCode(@Nonnull @Valid VerifyCodeRequest verifyCodeRequest);

    @Nonnull @Valid LoginResponse resendMfaVerification(@Nonnull @Valid ResendVerificationCodeRequest resendVerificationCodeRequest);

    @Nonnull @Valid ActivateUserResponse activateUser(@Nonnull @Valid ActivateAccountRequest activateAccountRequest);

    void resetPassword(@Nonnull @Valid ResetPasswordRequest resetPasswordRequest);

    void sendResetPasswordCode(@Valid SendResetPasswordCodeRequest sendResetPasswordCodeRequest);

    @Nonnull @Valid UpdateUserViewResponse updateUser(UUID userId, @Nonnull UpdateUserViewRequest updateUserViewRequest);

    @Nonnull @Valid FindUserByIdViewResponse findUserById(UUID userId);

    @Nonnull @Valid
    SearchUserPageInfoViewResponse searchUsers(Integer page, Integer size, String field, String direction, String keyword);
}
