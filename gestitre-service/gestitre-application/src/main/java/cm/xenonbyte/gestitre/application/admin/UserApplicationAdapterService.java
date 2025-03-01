package cm.xenonbyte.gestitre.application.admin;

import cm.xenonbyte.gestitre.application.admin.dto.ActivateAccountRequest;
import cm.xenonbyte.gestitre.application.admin.dto.ActivateUserResponse;
import cm.xenonbyte.gestitre.application.admin.dto.CreateUserViewRequest;
import cm.xenonbyte.gestitre.application.admin.dto.CreateUserViewResponse;
import cm.xenonbyte.gestitre.application.admin.dto.FindUserByIdViewResponse;
import cm.xenonbyte.gestitre.application.admin.dto.LoginRequest;
import cm.xenonbyte.gestitre.application.admin.dto.LoginResponse;
import cm.xenonbyte.gestitre.application.admin.dto.RefreshTokenResponse;
import cm.xenonbyte.gestitre.application.admin.dto.ResendVerificationCodeRequest;
import cm.xenonbyte.gestitre.application.admin.dto.ResetPasswordRequest;
import cm.xenonbyte.gestitre.application.admin.dto.SearchUserPageInfoViewResponse;
import cm.xenonbyte.gestitre.application.admin.dto.SendResetPasswordCodeRequest;
import cm.xenonbyte.gestitre.application.admin.dto.UpdateUserViewRequest;
import cm.xenonbyte.gestitre.application.admin.dto.UpdateUserViewResponse;
import cm.xenonbyte.gestitre.application.admin.dto.VerifyCodeRequest;
import cm.xenonbyte.gestitre.application.admin.dto.VerifyCodeResponse;
import cm.xenonbyte.gestitre.domain.admin.User;
import cm.xenonbyte.gestitre.domain.admin.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.admin.event.UserUpdatedEvent;
import cm.xenonbyte.gestitre.domain.admin.ports.primary.UserService;
import cm.xenonbyte.gestitre.domain.admin.vo.Token;
import cm.xenonbyte.gestitre.domain.common.verification.Verification;
import cm.xenonbyte.gestitre.domain.common.verification.event.VerificationCreatedEvent;
import cm.xenonbyte.gestitre.domain.common.verification.ports.primary.VerificationService;
import cm.xenonbyte.gestitre.domain.common.verification.vo.Duration;
import cm.xenonbyte.gestitre.domain.common.verification.vo.Url;
import cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationType;
import cm.xenonbyte.gestitre.domain.common.vo.Code;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Keyword;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoDirection;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoField;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoPage;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfoSize;
import cm.xenonbyte.gestitre.domain.common.vo.Password;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.common.vo.UserId;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Objects;
import java.util.UUID;

import static cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationType.ACCOUNT;
import static cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationType.PASSWORD;

/**
 * @author bamk
 * @version 1.0
 * @since 24/11/2024
 */
@Slf4j
@ApplicationScoped
public final class UserApplicationAdapterService implements UserApplicationAdapter {

    private final UserService userService;
    private final VerificationService verificationService;
    private final UserApplicationViewMapper userApplicationViewMapper;
    private final Long codeDuration;
    private final Long urlDuration;
    private final String baseUrl;

    public UserApplicationAdapterService(
            @Nonnull UserService userService,
            @Nonnull VerificationService verificationService,
            @Nonnull UserApplicationViewMapper userApplicationViewMapper,
            @ConfigProperty(name = "verification.code.duration") Long codeDuration,
            @ConfigProperty(name = "verification.url.duration") Long urlDuration,
            @ConfigProperty(name = "verification.url.baseUrl") String baseUrl
    ) {
        this.userService = Objects.requireNonNull(userService);
        this.verificationService = Objects.requireNonNull(verificationService);
        this.userApplicationViewMapper = Objects.requireNonNull(userApplicationViewMapper);
        this.codeDuration = codeDuration;
        this.urlDuration = urlDuration;
        this.baseUrl = baseUrl;
    }

    @Override
    @Nonnull
    @Transactional
    public CreateUserViewResponse createUser(@Nonnull CreateUserViewRequest createUserViewRequest)  {
        UserCreatedEvent userCreatedEvent = userService.createUser(
                userApplicationViewMapper.toUser(createUserViewRequest)
        );
        return userApplicationViewMapper.toCreateUserViewResponse(userCreatedEvent.getUser());
    }

    @Override
    @Nonnull
    @Transactional
    public LoginResponse login(@Nonnull LoginRequest loginRequest) {
        User user = userService.login(
                Code.of(Text.of(loginRequest.getTenantCode())),
                Email.of(Text.of(loginRequest.getEmail())),
                Password.of(Text.of(loginRequest.getPassword()))
        );

        if(Boolean.TRUE.equals(user.getUseMfa().value())) {
            VerificationCreatedEvent verification = verificationService.createVerification(
                    Verification.builder()
                            .userId(user.getId())
                            .type(VerificationType.MFA)
                            .duration(Duration.of(codeDuration))
                            .email(user.getEmail())
                            .build()
            );
            //TODO remove this for security reason
            log.info(">>>>> Verification code: {}", verification.getVerification().getCode().text().value());
            return LoginResponse.builder()
                    .isMfa(true)
                    .build();
        }
        Token token = userService.generateToken(user);

        return LoginResponse.builder()
                .accessToken(token.accessToken().value())
                .refreshToken(token.refreshToken().value())
                .isMfa(false)
                .build();
    }

    @Nonnull
    @Override
    @Transactional
    public VerifyCodeResponse verifyCode(@Nonnull VerifyCodeRequest verifyCodeRequest) {
        User user = verificationService.verifyCode(
                Code.of(Text.of(verifyCodeRequest.getCode())), Email.of(Text.of(verifyCodeRequest.getEmail())));
        Token token = userService.generateToken(user);
        return VerifyCodeResponse.builder()
                .accessToken(token.accessToken().value())
                .refreshToken(token.refreshToken().value())
                .build();
    }

    @Nonnull
    @Override
    @Transactional
    public LoginResponse resendMfaVerification(@Nonnull ResendVerificationCodeRequest resendVerificationCodeRequest) {
        verificationService.resendVerification(Email.of(Text.of(resendVerificationCodeRequest.getEmail())), VerificationType.MFA, codeDuration);
        return LoginResponse.builder()
                .isMfa(true)
                .build();
    }

    @Nonnull
    @Override
    @Transactional
    public ActivateUserResponse activateUser(@Nonnull ActivateAccountRequest activateAccountRequest) {
        User user = verificationService.verifyCode(Code.of(Text.of(activateAccountRequest.getCode())), ACCOUNT);
        return userApplicationViewMapper.toActivateUserResponse(userService.activateUser(user));
    }

    @Override
    @Transactional
    public void resetPassword(@Nonnull ResetPasswordRequest resetPasswordRequest) {
        User user = verificationService.verifyCode(Code.of(Text.of(resetPasswordRequest.getCode())), PASSWORD);
        userService.resetPassword(
                Password.of(Text.of(resetPasswordRequest.getOldPassword())),
                Password.of(Text.of(resetPasswordRequest.getNewPassword())),
                user
        );
    }

    @Override
    @Transactional
    public void sendResetPasswordCode(SendResetPasswordCodeRequest sendResetPasswordCodeRequest) {
        User user = userService.findUserByEmail(Email.of(Text.of(sendResetPasswordCodeRequest.getEmail())));
        verificationService.createVerification(
                Verification.builder()
                        .userId(user.getId())
                        .type(PASSWORD)
                        .url(Url.of(Text.of(baseUrl)))
                        .duration(Duration.of(urlDuration))
                        .email(user.getEmail())
                        .build()
        );
    }

    @Nonnull
    @Override
    @Transactional
    public UpdateUserViewResponse updateUser(UUID userId, @Nonnull UpdateUserViewRequest updateUserViewRequest) {
        UserUpdatedEvent userUpdatedEvent = userService.updateUser(new UserId(userId), userApplicationViewMapper.toUser(updateUserViewRequest));
        return userApplicationViewMapper.toUpdateUserViewResponse(userUpdatedEvent.getUser());
    }


    @Nonnull
    @Override
    public FindUserByIdViewResponse findUserById(UUID userId) {
        return userApplicationViewMapper.toFindUserByIdViewResponse(userService.findUserById(new UserId(userId)));
    }

    @Nonnull
    @Override
    public SearchUserPageInfoViewResponse searchUsers(Integer page, Integer size, String field, String direction, String keyword) {
        return userApplicationViewMapper.toSearchUsersPageInfoViewResponse(
                userService.searchUsers(
                        PageInfoPage.of(page),
                        PageInfoSize.of(size),
                        PageInfoField.of(Text.of(field)),
                        PageInfoDirection.valueOf(direction),
                        Keyword.of(Text.of(keyword))
                )
        );
    }

    @Nonnull
    @Override
    public RefreshTokenResponse refreshAccessToken(String refreshToken) {
        refreshToken = refreshToken.replace("Bearer ", "");
        Text accessToken = userService.refreshAccessToken(Text.of(refreshToken));
        return RefreshTokenResponse.builder()
                .accessToken(accessToken.value())
                .refreshToken(refreshToken)
                .build();
    }
}
