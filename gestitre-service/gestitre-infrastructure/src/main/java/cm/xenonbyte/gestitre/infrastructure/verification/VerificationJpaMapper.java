package cm.xenonbyte.gestitre.infrastructure.verification;


import cm.xenonbyte.gestitre.domain.common.verification.Verification;
import jakarta.annotation.Nonnull;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */
@Mapper(
        componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface VerificationJpaMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id.value", target = "id")
    @Mapping(expression = "java(verification.getCode() == null || verification.getCode().text().value().isEmpty()? null: verification.getCode().text().value())", target = "code")
    @Mapping(expression = "java(verification.getUrl() == null || verification.getUrl().text().value().isEmpty()? null: verification.getUrl().text().value())", target = "url")
    @Mapping(source = "expiredAt", target = "expiredAt")
    @Mapping(source = "createdAt", target = "creationAt")
    @Mapping(source = "email.text.value", target = "email")
    @Mapping(expression = "java(verification.getUserId() == null? null: cm.xenonbyte.gestitre.infrastructure.admin.UserJpa.builder().id(verification.getUserId().getValue()).build())", target = "userJpa")
    @Mapping(expression = "java(verification.getMailServerId() == null? null: cm.xenonbyte.gestitre.infrastructure.notification.MailServerJpa.builder().id(verification.getMailServerId().getValue()).build())", target = "mailServerJpa")
    @Mapping(expression = "java(VerificationTypeJpa.valueOf(verification.getType().name()))", target = "type")
    @Mapping(expression = "java(VerificationStatusJpa.valueOf(verification.getStatus().name()))", target = "status")
    @Nonnull VerificationJpa toVerificationJpa(@Nonnull Verification verification);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id.value", source = "id")
    @Mapping(target = "code", expression = "java(verificationJpa.getCode() == null || verificationJpa.getCode().isEmpty()? null: cm.xenonbyte.gestitre.domain.common.vo.Code.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(verificationJpa.getCode())))")
    @Mapping(target = "url", expression = "java(verificationJpa.getUrl() == null || verificationJpa.getUrl().isEmpty()? null: cm.xenonbyte.gestitre.domain.common.verification.vo.Url.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(verificationJpa.getUrl())))")
    @Mapping(target = "expiredAt", source = "expiredAt")
    @Mapping(target = "createdAt", source = "creationAt")
    @Mapping(target = "email.text.value", source = "email")
    @Mapping(target = "userId", expression = "java(verificationJpa.getUserJpa() == null? null: new cm.xenonbyte.gestitre.domain.common.vo.UserId(verificationJpa.getUserJpa().getId()))")
    @Mapping(target = "mailServerId", expression = "java(verificationJpa.getMailServerJpa() == null? null: new cm.xenonbyte.gestitre.domain.common.vo.MailServerId(verificationJpa.getMailServerJpa().getId()))")
    @Mapping(target = "type", expression = "java(cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationType.valueOf(verificationJpa.getType().name()))")
    @Mapping(target = "status", expression = "java(cm.xenonbyte.gestitre.domain.common.verification.vo.VerificationStatus.valueOf(verificationJpa.getStatus().name()))")
    @Nonnull Verification toVerification(@Nonnull VerificationJpa verificationJpa);

    void copyNewToOldVerificationJpa(@Nonnull VerificationJpa newVerificationJpa, @Nonnull @MappingTarget VerificationJpa oldVerificationJpa);
}
