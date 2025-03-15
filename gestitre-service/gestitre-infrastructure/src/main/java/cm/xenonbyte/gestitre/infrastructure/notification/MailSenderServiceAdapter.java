package cm.xenonbyte.gestitre.infrastructure.notification;

import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.notification.Mail;
import cm.xenonbyte.gestitre.domain.notification.MailServer;
import cm.xenonbyte.gestitre.domain.notification.MailTemplate;
import cm.xenonbyte.gestitre.domain.notification.MailTemplateNotFoundException;
import cm.xenonbyte.gestitre.domain.notification.SendMailTechnicalException;
import cm.xenonbyte.gestitre.domain.notification.ports.secondary.MailSenderService;
import io.quarkus.qute.Engine;
import io.quarkus.qute.Template;
import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * @author bamk
 * @version 1.0
 * @since 15/03/2025
 */
@Slf4j
@ApplicationScoped
public final class MailSenderServiceAdapter implements MailSenderService {


    private final Engine engine;

    public MailSenderServiceAdapter(final Engine engine) {
        this.engine = requireNonNull(engine);
    }

    @Override
    public void send(@Nonnull Mail mail, @Nonnull MailTemplate mailTemplate, @Nonnull MailServer mailServer) {
        String content = renderTemplate(mailTemplate, mail.getAttributes());
        Properties mailProperties = configMailServer(mailServer);
        Session sessionInstance = getSessionInstance(mailProperties, mailServer);

        try {
            MimeMessage message = buildEmail(mail, sessionInstance, content);
            Transport.send(message);
        } catch (MessagingException e) {
            log.error("", e);
            throw new SendMailTechnicalException(new String[] {e.getMessage()});
        }
    }

    private static MimeMessage buildEmail(Mail mail, Session sessionInstance, String body) throws MessagingException {
        MimeMessage message = new MimeMessage(sessionInstance);
        message.setFrom(new InternetAddress(mail.getFrom().text().value()));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getTo().text().value()));
        if(mail.getCc() != null && !mail.getCc().text().value().isEmpty()) {
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mail.getCc().text().value()));
        }
        message.setSubject(mail.getSubject().text().value());
        message.setContent(body, "text/html");
        return message;
    }

    private Session getSessionInstance(Properties mailProperties, MailServer mailServer) {
        if (Boolean.TRUE.equals(mailServer.getUseAuth().value())) {
            return Session.getInstance(mailProperties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mailServer.getUsername().text().value(), mailServer.getPassword().text().value());
                }
            });
        }
        return Session.getInstance(mailProperties);
    }

    private Properties configMailServer(MailServer mailServer) {
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.host", mailServer.getHost().text().value());
        mailProperties.put("mail.smtp.port", String.valueOf(mailServer.getPort().value()));
        mailProperties.put("mail.smtp.from", mailServer.getFrom().text().value());
        Boolean useAuth = mailServer.getUseAuth().value();
        if(Boolean.TRUE.equals(useAuth)) {
            mailProperties.put("mail.smtp.auth", "true");
            mailProperties.put("mail.smtp.username", mailServer.getUsername().text().value());
            mailProperties.put("mail.smtp.password", mailServer.getPassword().text().value());
        }
        Boolean useSSL = mailServer.getUseSSL().value();
        if(Boolean.TRUE.equals(useSSL)) {
            mailProperties.put("mail.smtp.starttls.enable", "true");
        }

        return mailProperties;

    }

    private String renderTemplate(MailTemplate mailTemplate, Map<Text, Text> attributes) {
        Template template = engine.getTemplate(mailTemplate.getName().text().value());
        if(template == null) {
            throw new MailTemplateNotFoundException(new String[]{mailTemplate.getName().text().value()});
        }
        Map<String, String> data = attributes.entrySet().parallelStream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().value(),
                        entry -> entry.getValue().value())
                );
        return template.data(data).render();
    }
}
