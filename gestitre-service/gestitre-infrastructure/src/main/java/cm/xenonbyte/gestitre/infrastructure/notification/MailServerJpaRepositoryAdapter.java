package cm.xenonbyte.gestitre.infrastructure.notification;

import cm.xenonbyte.gestitre.domain.common.vo.MailServerId;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.notification.MailServer;
import cm.xenonbyte.gestitre.domain.notification.ports.secondary.MailServerRepository;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;

/**
 * @author bamk
 * @version 1.0
 * @since 02/12/2024
 */
@Slf4j
@ApplicationScoped
public final class MailServerJpaRepositoryAdapter implements MailServerRepository {

    private final MailServerJpaRepository mailServerJpaRepository;
    private final MailServerJpaMapper mailServerJpaMapper;

    public MailServerJpaRepositoryAdapter(
            @Nonnull MailServerJpaRepository mailServerJpaRepository,
            @Nonnull MailServerJpaMapper mailServerJpaMapper
    ) {
        this.mailServerJpaRepository = Objects.requireNonNull(mailServerJpaRepository);
        this.mailServerJpaMapper = Objects.requireNonNull(mailServerJpaMapper);
    }

    @Nonnull
    @Override
    @Transactional
    public MailServer create(@Nonnull MailServer mailServer) {
        mailServerJpaRepository.persist(mailServerJpaMapper.toMailServerJpa(mailServer));
        return mailServerJpaMapper.toMailServer(
                mailServerJpaRepository.findById(mailServer.getId().getValue())
        );
    }

    @Override
    public Boolean existByName(@Nonnull Name name) {
        return mailServerJpaRepository.existsByName(name.text().value());
    }

    @Override
    public Optional<MailServer> findById(@Nonnull MailServerId mailServerId) {
        return mailServerJpaRepository.findByIdOptional(mailServerId.getValue())
                .map(mailServerJpaMapper::toMailServer);
    }

    @Nonnull
    @Override
    @Transactional
    public MailServer update(@Nonnull MailServerId mailServerId, @Nonnull MailServer newMailServer) {
        return null;
    }
}
