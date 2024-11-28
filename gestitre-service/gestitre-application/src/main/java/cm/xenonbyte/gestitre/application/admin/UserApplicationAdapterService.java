package cm.xenonbyte.gestitre.application.admin;

import cm.xenonbyte.gestitre.application.admin.dto.CreateUserViewRequest;
import cm.xenonbyte.gestitre.application.admin.dto.CreateUserViewResponse;
import cm.xenonbyte.gestitre.application.admin.dto.LoginRequest;
import cm.xenonbyte.gestitre.application.admin.dto.LoginResponse;
import cm.xenonbyte.gestitre.domain.admin.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.admin.ports.primary.UserService;
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
    private final UserApplicationViewMapper userApplicationViewMapper;

    public UserApplicationAdapterService(
            @Nonnull UserService userService,
            @Nonnull UserApplicationViewMapper userApplicationViewMapper) {
        this.userService = Objects.requireNonNull(userService);
        this.userApplicationViewMapper = Objects.requireNonNull(userApplicationViewMapper);
    }

    @Nonnull
    @Override
    public CreateUserViewResponse createUser(@Nonnull CreateUserViewRequest createUserViewRequest)  {
        UserCreatedEvent userCreatedEvent = userService.createUser(
                userApplicationViewMapper.toUser(createUserViewRequest)
        );
        return userApplicationViewMapper.toCreateUserViewResponse(userCreatedEvent.getUser());
    }

    @Override
    public LoginResponse login(@Nonnull LoginRequest loginRequest) {
        Token token = userService.login(
                Email.of(Text.of(loginRequest.getEmail())),
                Password.of(Text.of(loginRequest.getPassword()))
        );
        return LoginResponse.builder()
                .accessToken(token.accessToken().value())
                .refreshToken(token.refreshToken().value())
                .build();
    }
}
