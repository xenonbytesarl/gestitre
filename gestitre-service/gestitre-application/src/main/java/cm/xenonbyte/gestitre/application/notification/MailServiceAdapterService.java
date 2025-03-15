package cm.xenonbyte.gestitre.application.notification;

import cm.xenonbyte.gestitre.domain.notification.ports.primary.MailService;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.requireNonNull;

/**
 * @author bamk
 * @version 1.0
 * @since 15/03/2025
 */
@Slf4j
@ApplicationScoped
public final class MailServiceAdapterService implements MailServiceAdapter {

    private final MailService mailService;

    public MailServiceAdapterService(MailService mailService) {
        this.mailService = requireNonNull(mailService);
    }

    @Override
    @Scheduled(every="5s")
    public void sendMails() {
        mailService.sendMails();
    }
}
