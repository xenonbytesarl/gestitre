package cm.xenonbyte.gestitre.application.admin;

import cm.xenonbyte.gestitre.application.admin.dto.CreateUserViewRequest;
import cm.xenonbyte.gestitre.application.admin.dto.CreateUserViewResponse;
import cm.xenonbyte.gestitre.application.admin.dto.LoginRequest;
import cm.xenonbyte.gestitre.application.admin.dto.LoginResponse;
import cm.xenonbyte.gestitre.application.admin.dto.ResendVerificationCodeRequest;
import cm.xenonbyte.gestitre.application.admin.dto.VerifyCodeResponse;
import cm.xenonbyte.gestitre.application.admin.dto.VerifyCodeRequest;
import cm.xenonbyte.gestitre.domain.admin.User;
import cm.xenonbyte.gestitre.domain.admin.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.admin.ports.primary.UserService;
import cm.xenonbyte.gestitre.domain.admin.verification.Verification;
import cm.xenonbyte.gestitre.domain.admin.verification.event.VerificationCreatedEvent;
import cm.xenonbyte.gestitre.domain.admin.verification.ports.primary.VerificationService;
import cm.xenonbyte.gestitre.domain.admin.verification.vo.Code;
import cm.xenonbyte.gestitre.domain.admin.verification.vo.Duration;
import cm.xenonbyte.gestitre.domain.admin.verification.vo.VerificationType;
import cm.xenonbyte.gestitre.domain.admin.vo.Password;
import cm.xenonbyte.gestitre.domain.admin.vo.Token;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Email;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

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

    public UserApplicationAdapterService(
            @Nonnull UserService userService,
            @Nonnull VerificationService verificationService,
            @Nonnull UserApplicationViewMapper userApplicationViewMapper
    ) {
        this.userService = Objects.requireNonNull(userService);
        this.verificationService = Objects.requireNonNull(verificationService);
        this.userApplicationViewMapper = Objects.requireNonNull(userApplicationViewMapper);
        this.codeDuration = 300L;
    }

    @Override
    @Nonnull
    public CreateUserViewResponse createUser(@Nonnull CreateUserViewRequest createUserViewRequest)  {
        UserCreatedEvent userCreatedEvent = userService.createUser(
                userApplicationViewMapper.toUser(createUserViewRequest)
        );
        return userApplicationViewMapper.toCreateUserViewResponse(userCreatedEvent.getUser());
    }

    @Override
    @Nonnull
    public LoginResponse login(@Nonnull LoginRequest loginRequest) {
        User user = userService.login(
                Email.of(Text.of(loginRequest.getEmail())),
                Password.of(Text.of(loginRequest.getPassword()))
        );

        if(Boolean.TRUE.equals(user.getUseMfa().value())) {
            VerificationCreatedEvent verificationCreatedEvent = verificationService.createVerification(
                    Verification.builder()
                            .userId(user.getId())
                            .type(VerificationType.MFA)
                            .duration(Duration.of(codeDuration))
                            .email(user.getEmail())
                            .build()
            );
            return LoginResponse.builder()
                    .isMfa(true)
                    .code(verificationCreatedEvent.getVerification().getCode().text().value())
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
    public LoginResponse resendMfaVerification(@Nonnull ResendVerificationCodeRequest resendVerificationCodeRequest) {
        VerificationCreatedEvent verificationCreatedEvent = verificationService.resendVerification(
                Email.of(Text.of(resendVerificationCodeRequest.getEmail())), VerificationType.MFA, codeDuration);
        return LoginResponse.builder()
                .isMfa(true)
                .code(verificationCreatedEvent.getVerification().getCode().text().value())
                .build();
    }
}
