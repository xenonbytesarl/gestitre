package cm.xenonbyte.gestitre.application.admin;

import cm.xenonbyte.gestitre.application.admin.dto.CreateUserViewRequest;
import cm.xenonbyte.gestitre.application.admin.dto.CreateUserViewResponse;
import cm.xenonbyte.gestitre.domain.admin.event.UserCreatedEvent;
import cm.xenonbyte.gestitre.domain.admin.ports.primary.UserService;
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
    public CreateUserViewResponse createUser(@Nonnull CreateUserViewRequest createUserViewRequest) throws Exception {
        UserCreatedEvent userCreatedEvent = userService.createUser(
                userApplicationViewMapper.toUser(createUserViewRequest)
        );
        return userApplicationViewMapper.toCreateUserViewResponse(userCreatedEvent.getUser());
    }
}
