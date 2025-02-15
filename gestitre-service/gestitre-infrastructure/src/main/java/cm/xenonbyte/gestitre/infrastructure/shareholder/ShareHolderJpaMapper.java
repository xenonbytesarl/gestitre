package cm.xenonbyte.gestitre.infrastructure.shareholder;

import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.Phone;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.shareholder.ShareHolder;
import cm.xenonbyte.gestitre.domain.shareholder.vo.Representative;
import cm.xenonbyte.gestitre.domain.shareholder.vo.Successor;
import jakarta.annotation.Nonnull;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

/**
 * @author bamk
 * @version 1.0
 * @since 04/02/2025
 */
@Mapper(
        componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface ShareHolderJpaMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "name.text.value", target = "name")
    @Mapping(source = "accountNumber.text.value", target = "accountNumber")
    @Mapping(expression = "java(AccountTypeJpa.valueOf(shareHolder.getAccountType().name()))", target = "accountTypeJpa")
    @Mapping(source = "taxResidence.text.value", target = "taxResidence")
    @Mapping(source = "initialBalance.value", target = "initialBalance")
    @Mapping(expression = "java(shareHolder.getBankAccountNumber() == null || shareHolder.getBankAccountNumber().text().value().isEmpty()? null: shareHolder.getBankAccountNumber().text().value())", target = "bankAccountNumber")
    @Mapping(expression = "java(shareHolder.getAdministrator() == null || shareHolder.getAdministrator().text().value().isEmpty()? null: shareHolder.getAdministrator().text().value())", target = "administrator")
    @Mapping(expression = "java(shareHolder.getEmail() == null || shareHolder.getEmail().text().value().isEmpty()? null: shareHolder.getEmail().text().value())", target = "email")
    @Mapping(expression = "java(shareHolder.getPhone() == null || shareHolder.getPhone().text().value().isEmpty()? null: shareHolder.getPhone().text().value())", target = "phone")
    @Mapping(expression = "java(shareHolder.getCity() == null || shareHolder.getCity().text().value().isEmpty()? null: shareHolder.getCity().text().value())", target = "city")
    @Mapping(expression = "java(shareHolder.getZipCode() == null || shareHolder.getZipCode().text().value().isEmpty()? null: shareHolder.getZipCode().text().value())", target = "zipCode")
    @Mapping(expression = "java(shareHolder.getShareHolderType() == null? null: ShareHolderTypeJpa.valueOf(shareHolder.getShareHolderType().name()))", target = "shareHolderTypeJpa")
    @Mapping(source = "representative", qualifiedByName = "representativeToRepresentativeJpa", target = "representativeJpa")
    @Mapping(source = "successor", qualifiedByName = "successorToSuccessorJpa", target = "successorJpa")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "active.value", target="active")
    @Nonnull ShareHolderJpa toShareHolderJpa(@Nonnull ShareHolder shareHolder);

    @Named("representativeToRepresentativeJpa")
    default RepresentativeJpa toRepresentativeJpa(Representative representative) {
        if(representative == null) {
            return null;
        }
        if(representative.email() != null && !representative.email().text().value().isEmpty()) {
            return RepresentativeJpa.builder()
                    .name(representative.name().text().value())
                    .email(representative.email().text().value())
                    .phone(representative.phone().text().value())
                    .build();
        }

        return RepresentativeJpa.builder()
                .name(representative.name().text().value())
                .phone(representative.phone().text().value())
                .build();
    }

    @Named("successorToSuccessorJpa")
    default SuccessorJpa toSuccessorJpa(Successor successor) {
        if(successor == null) {
            return null;
        }
        if(successor.email() != null && !successor.email().text().value().isEmpty()) {
            return SuccessorJpa.builder()
                    .name(successor.name().text().value())
                    .email(successor.email().text().value())
                    .phone(successor.phone().text().value())
                    .build();
        }

        return SuccessorJpa.builder()
                .name(successor.name().text().value())
                .phone(successor.phone().text().value())
                .build();
    }


    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id.value", source = "id")
    @Mapping(target = "name.text.value", source = "name")
    @Mapping(target = "accountNumber.text.value", source = "accountNumber")
    @Mapping(target = "accountType", expression = "java(cm.xenonbyte.gestitre.domain.shareholder.vo.AccountType.valueOf(shareHolderJpa.getAccountTypeJpa().name()))")
    @Mapping(target = "taxResidence.text.value", source = "taxResidence")
    @Mapping(target = "initialBalance.value", source = "initialBalance")
    @Mapping(target = "bankAccountNumber", expression = "java(shareHolderJpa.getBankAccountNumber() == null || shareHolderJpa.getBankAccountNumber().isEmpty()? null: cm.xenonbyte.gestitre.domain.shareholder.vo.BankAccountNumber.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(shareHolderJpa.getBankAccountNumber())))")
    @Mapping(target = "administrator", expression = "java(shareHolderJpa.getAdministrator() == null || shareHolderJpa.getAdministrator().isEmpty()? null: cm.xenonbyte.gestitre.domain.shareholder.vo.Administrator.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(shareHolderJpa.getAdministrator())))")
    @Mapping(target = "email", expression = "java(shareHolderJpa.getEmail() == null || shareHolderJpa.getEmail().isEmpty()? null: cm.xenonbyte.gestitre.domain.common.vo.Email.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(shareHolderJpa.getEmail())))")
    @Mapping(target = "phone", expression = "java(shareHolderJpa.getPhone() == null || shareHolderJpa.getPhone().isEmpty()? null: cm.xenonbyte.gestitre.domain.common.vo.Phone.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(shareHolderJpa.getPhone())))")
    @Mapping(target = "city", expression = "java(shareHolderJpa.getCity() == null || shareHolderJpa.getCity().isEmpty()? null: cm.xenonbyte.gestitre.domain.common.vo.City.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(shareHolderJpa.getCity())))")
    @Mapping(target = "zipCode", expression = "java(shareHolderJpa.getZipCode() == null || shareHolderJpa.getZipCode().isEmpty()? null: cm.xenonbyte.gestitre.domain.common.vo.ZipCode.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(shareHolderJpa.getZipCode())))")
    @Mapping(target = "shareHolderType", expression = "java(shareHolderJpa.getShareHolderTypeJpa() == null? null: cm.xenonbyte.gestitre.domain.shareholder.vo.ShareHolderType.valueOf(shareHolderJpa.getShareHolderTypeJpa().name()))")
    @Mapping(target = "representative", qualifiedByName = "representativeJpaToRepresentative", source = "representativeJpa")
    @Mapping(target = "successor", qualifiedByName = "successorJpaToSuccessor", source = "successorJpa")
    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(target = "tenantId.value", source = "tenantId")
    @Mapping(target="active.value", source = "active")
    @Nonnull ShareHolder toShareHolder(@Nonnull ShareHolderJpa shareHolderJpa);

    @Named("representativeJpaToRepresentative")
    default Representative toRepresentative(RepresentativeJpa representativeJpa) {
        if(representativeJpa == null) {
            return null;
        }

        if(representativeJpa.getEmail() != null && !representativeJpa.getEmail().isEmpty()) {
            return Representative.of(
                    Name.of(Text.of(representativeJpa.getName())),
                    Email.of(Text.of(representativeJpa.getEmail())),
                    Phone.of(Text.of(representativeJpa.getPhone()))
            );
        }

        return Representative.of(Name.of(Text.of(representativeJpa.getName())), Phone.of(Text.of(representativeJpa.getPhone())));
    }

    @Named("successorJpaToSuccessor")
    default Successor toSuccessor(SuccessorJpa successorJpa) {
        if(successorJpa == null) {
            return null;
        }

        if(successorJpa.getEmail() != null && !successorJpa.getEmail().isEmpty()) {
            return Successor.of(
                    Name.of(Text.of(successorJpa.getName())),
                    Email.of(Text.of(successorJpa.getEmail())),
                    Phone.of(Text.of(successorJpa.getPhone()))
            );
        }

        return Successor.of(Name.of(Text.of(successorJpa.getName())), Phone.of(Text.of(successorJpa.getPhone())));
    }

    void copyNewToOldShareHolderMapper(@Nonnull ShareHolderJpa newShareHolderJpa, @Nonnull @MappingTarget ShareHolderJpa oldShareHolderJpa);
}
