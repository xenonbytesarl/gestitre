package cm.xenonbyte.gestitre.application.shareholder;

import cm.xenonbyte.gestitre.application.shareholder.dto.CreateShareHolderResponseView;
import cm.xenonbyte.gestitre.application.shareholder.dto.CreateShareHolderViewRequest;
import cm.xenonbyte.gestitre.application.shareholder.dto.FindShareHolderByIdResponseView;
import cm.xenonbyte.gestitre.application.shareholder.dto.FindShareHoldersPageInfoViewResponse;
import cm.xenonbyte.gestitre.application.shareholder.dto.FindShareHoldersResponseView;
import cm.xenonbyte.gestitre.application.shareholder.dto.RepresentativeView;
import cm.xenonbyte.gestitre.application.shareholder.dto.SuccessorView;
import cm.xenonbyte.gestitre.application.shareholder.dto.UpdateShareHolderResponseView;
import cm.xenonbyte.gestitre.application.shareholder.dto.UpdateShareShareHolderRequestView;
import cm.xenonbyte.gestitre.domain.common.vo.Email;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.common.vo.Phone;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.shareholder.ShareHolder;
import cm.xenonbyte.gestitre.domain.shareholder.vo.Representative;
import cm.xenonbyte.gestitre.domain.shareholder.vo.Successor;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * @author bamk
 * @version 1.0
 * @since 05/02/2025
 */
@Mapper(
        componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface ShareHolderViewMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "name", target="name.text.value")
    @Mapping(source = "accountNumber", target="accountNumber.text.value")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.shareholder.vo.AccountType.valueOf(createShareHolderViewRequest.getAccountType().name()))", target="accountType")
    @Mapping(source = "taxResidence", target="taxResidence.text.value")
    @Mapping(expression = "java(createShareHolderViewRequest.getInitialBalance() == null? null: cm.xenonbyte.gestitre.domain.common.vo.Money.of(createShareHolderViewRequest.getInitialBalance()))", target="initialBalance")
    @Mapping(expression = "java(createShareHolderViewRequest.getBankAccountNumber() == null || createShareHolderViewRequest.getBankAccountNumber().isEmpty()? null: cm.xenonbyte.gestitre.domain.shareholder.vo.BankAccountNumber.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(createShareHolderViewRequest.getBankAccountNumber())))", target="bankAccountNumber")
    @Mapping(expression = "java(createShareHolderViewRequest.getAdministrator() == null || createShareHolderViewRequest.getAdministrator().isEmpty()? null: cm.xenonbyte.gestitre.domain.shareholder.vo.Administrator.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(createShareHolderViewRequest.getAdministrator())))", target="administrator")
    @Mapping(expression = "java(createShareHolderViewRequest.getEmail() == null || createShareHolderViewRequest.getEmail().isEmpty()? null: cm.xenonbyte.gestitre.domain.common.vo.Email.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(createShareHolderViewRequest.getEmail())))", target="email")
    @Mapping(expression = "java(createShareHolderViewRequest.getPhone() == null || createShareHolderViewRequest.getPhone().isEmpty()? null: cm.xenonbyte.gestitre.domain.common.vo.Phone.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(createShareHolderViewRequest.getPhone())))", target="phone")
    @Mapping(expression = "java(createShareHolderViewRequest.getCity() == null || createShareHolderViewRequest.getCity().isEmpty()? null: cm.xenonbyte.gestitre.domain.common.vo.City.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(createShareHolderViewRequest.getCity())))", target="city")
    @Mapping(expression = "java(createShareHolderViewRequest.getZipCode() == null || createShareHolderViewRequest.getZipCode().isEmpty()? null: cm.xenonbyte.gestitre.domain.common.vo.ZipCode.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(createShareHolderViewRequest.getZipCode())))", target="zipCode")
    @Mapping(expression = "java(createShareHolderViewRequest.getShareHolderType() == null? null: cm.xenonbyte.gestitre.domain.shareholder.vo.ShareHolderType.valueOf(createShareHolderViewRequest.getShareHolderType().name()))", target="shareHolderType")
    @Mapping(expression = "java(createShareHolderViewRequest.getActive() == null? null: cm.xenonbyte.gestitre.domain.common.vo.Active.with(createShareHolderViewRequest.getActive()))", target="active")
    @Mapping(source = "representative", qualifiedByName = "representativeViewRequestToRepresentative", target = "representative")
    @Mapping(source = "successor", qualifiedByName = "successorViewRequestToSuccessor", target = "successor")
    @Mapping(expression = "java(createShareHolderViewRequest.getCreatedDate().withZoneSameInstant(java.time.ZoneOffset.UTC))", target="createdDate")
    @Nonnull ShareHolder toShareHolder(@Nonnull @Valid CreateShareHolderViewRequest createShareHolderViewRequest);

    @Named("representativeViewRequestToRepresentative")
    default Representative toRepresentative(RepresentativeView representativeView) {
        if(representativeView == null) {
            return null;
        }

        if(representativeView.getEmail() != null && !representativeView.getEmail().isEmpty()) {
            return Representative.of(
                    Name.of(Text.of(representativeView.getName())),
                    Email.of(Text.of(representativeView.getEmail())),
                    Phone.of(Text.of(representativeView.getPhone()))
            );
        }

        return Representative.of(Name.of(Text.of(representativeView.getName())), Phone.of(Text.of(representativeView.getPhone())));
    }

    @Named("successorViewRequestToSuccessor")
    default Successor toSuccessor(SuccessorView successorView) {
        if(successorView == null) {
            return null;
        }

        if(successorView.getEmail() != null && !successorView.getEmail().isEmpty()) {
            return Successor.of(
                    Name.of(Text.of(successorView.getName())),
                    Email.of(Text.of(successorView.getEmail())),
                    Phone.of(Text.of(successorView.getPhone()))
            );
        }

        return Successor.of(Name.of(Text.of(successorView.getName())), Phone.of(Text.of(successorView.getPhone())));
    }


    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "name.text.value", target = "name")
    @Mapping(source = "accountNumber.text.value", target = "accountNumber")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.application.shareholder.dto.AccountTypeView.valueOf(shareHolder.getAccountType().name()))", target = "accountType")
    @Mapping(source = "taxResidence.text.value", target = "taxResidence")
    @Mapping(expression = "java(shareHolder.getInitialBalance() == null? null: shareHolder.getInitialBalance().value())", target="initialBalance")
    @Mapping(expression = "java(shareHolder.getBankAccountNumber() == null || shareHolder.getBankAccountNumber().text().value().isEmpty()? null: shareHolder.getBankAccountNumber().text().value())", target="bankAccountNumber")
    @Mapping(expression = "java(shareHolder.getAdministrator() == null || shareHolder.getAdministrator().text().value().isEmpty()? null: shareHolder.getAdministrator().text().value())", target="administrator")
    @Mapping(expression = "java(shareHolder.getEmail() == null || shareHolder.getEmail().text().value().isEmpty()? null: shareHolder.getEmail().text().value())", target="email")
    @Mapping(expression = "java(shareHolder.getPhone() == null || shareHolder.getPhone().text().value().isEmpty()? null: shareHolder.getPhone().text().value())", target="phone")
    @Mapping(expression = "java(shareHolder.getCity() == null || shareHolder.getCity().text().value().isEmpty()? null: shareHolder.getCity().text().value())", target="city")
    @Mapping(expression = "java(shareHolder.getZipCode() == null || shareHolder.getZipCode().text().value().isEmpty()? null: shareHolder.getZipCode().text().value())", target="zipCode")
    @Mapping(expression = "java(shareHolder.getShareHolderType() == null? null: cm.xenonbyte.gestitre.application.shareholder.dto.ShareHolderTypeView.valueOf(shareHolder.getShareHolderType().name()))", target="shareHolderType")
    @Mapping(expression = "java(shareHolder.getActive() == null? null: shareHolder.getActive().value())", target="active")
    @Mapping(source = "representative", qualifiedByName = "representativeToRepresentativeView", target = "representative")
    @Mapping(source = "successor", qualifiedByName = "successorToSuccessorView", target = "successor")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.context.TimezoneContext.current() == null? null: shareHolder.getCreatedDate().withZoneSameInstant(java.time.ZoneId.of(cm.xenonbyte.gestitre.domain.context.TimezoneContext.current().getName())))", target="createdDate")
    @Mapping(source = "tenantId.value", target="tenantId")
    @Nonnull @Valid
    CreateShareHolderResponseView toCreateShareHolderViewResponse(ShareHolder shareHolder);

    @Named("representativeToRepresentativeView")
    default RepresentativeView toRepresentativeView(Representative representative) {
        if(representative == null) {
            return null;
        }
        if(representative.email() != null) {
            return RepresentativeView.builder()
                    .name(representative.name().text().value())
                    .email(representative.email().text().value())
                    .phone(representative.phone().text().value())
                    .build();
        }

        return RepresentativeView.builder()
                .name(representative.name().text().value())
                .phone(representative.phone().text().value())
                .build();
    }

    @Named("successorToSuccessorView")
    default SuccessorView toSuccessorView(Successor successor) {
        if(successor == null) {
            return null;
        }
        if(successor.email() != null) {
            return SuccessorView.builder()
                    .name(successor.name().text().value())
                    .email(successor.email().text().value())
                    .phone(successor.phone().text().value())
                    .build();
        }

        return SuccessorView.builder()
                .name(successor.name().text().value())
                .phone(successor.phone().text().value())
                .build();
    }

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "first", target = "first")
    @Mapping(source = "last", target = "last")
    @Mapping(source = "pageSize", target = "pageSize")
    @Mapping(source = "totalPages", target = "totalPages")
    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(source = "elements", qualifiedByName = "toFindShareHolderViewResponses", target = "elements")
    @Nonnull FindShareHoldersPageInfoViewResponse toFindShareHoldersPageInfoViewResponse(@Nonnull PageInfo<ShareHolder> shareHolderPageInfo);


    @Named("toFindShareHolderViewResponses")
    @Nonnull @Valid List<FindShareHoldersResponseView> toFindShareHolderViewResponses(@Nonnull List<ShareHolder> shareHolders);


    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "name.text.value", target = "name")
    @Mapping(source = "accountNumber.text.value", target = "accountNumber")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.application.shareholder.dto.AccountTypeView.valueOf(shareHolder.getAccountType().name()))", target = "accountType")
    @Mapping(source = "taxResidence.text.value", target = "taxResidence")
    @Mapping(expression = "java(shareHolder.getInitialBalance() == null? null: shareHolder.getInitialBalance().value())", target="initialBalance")
    @Mapping(expression = "java(shareHolder.getBankAccountNumber() == null || shareHolder.getBankAccountNumber().text().value().isEmpty()? null: shareHolder.getBankAccountNumber().text().value())", target="bankAccountNumber")
    @Mapping(expression = "java(shareHolder.getAdministrator() == null || shareHolder.getAdministrator().text().value().isEmpty()? null: shareHolder.getAdministrator().text().value())", target="administrator")
    @Mapping(expression = "java(shareHolder.getEmail() == null || shareHolder.getEmail().text().value().isEmpty()? null: shareHolder.getEmail().text().value())", target="email")
    @Mapping(expression = "java(shareHolder.getPhone() == null || shareHolder.getPhone().text().value().isEmpty()? null: shareHolder.getPhone().text().value())", target="phone")
    @Mapping(expression = "java(shareHolder.getCity() == null || shareHolder.getCity().text().value().isEmpty()? null: shareHolder.getCity().text().value())", target="city")
    @Mapping(expression = "java(shareHolder.getZipCode() == null || shareHolder.getZipCode().text().value().isEmpty()? null: shareHolder.getZipCode().text().value())", target="zipCode")
    @Mapping(expression = "java(shareHolder.getShareHolderType() == null? null: cm.xenonbyte.gestitre.application.shareholder.dto.ShareHolderTypeView.valueOf(shareHolder.getShareHolderType().name()))", target="shareHolderType")
    @Mapping(expression = "java(shareHolder.getActive() == null? null: shareHolder.getActive().value())", target="active")
    @Mapping(source = "representative", qualifiedByName = "representativeToRepresentativeView", target = "representative")
    @Mapping(source = "successor", qualifiedByName = "successorToSuccessorView", target = "successor")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.context.TimezoneContext.current() == null? null: shareHolder.getCreatedDate().withZoneSameInstant(java.time.ZoneId.of(cm.xenonbyte.gestitre.domain.context.TimezoneContext.current().getName())))", target="createdDate")
    @Mapping(source = "tenantId.value", target="tenantId")
    @Nonnull @Valid FindShareHoldersResponseView toFindShareHolderViewResponse(ShareHolder shareHolder);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id", target="id.value")
    @Mapping(source = "name", target="name.text.value")
    @Mapping(source = "accountNumber", target="accountNumber.text.value")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.shareholder.vo.AccountType.valueOf(updateShareHolderViewRequest.getAccountType().name()))", target="accountType")
    @Mapping(source = "taxResidence", target="taxResidence.text.value")
    @Mapping(expression = "java(updateShareHolderViewRequest.getInitialBalance() == null? null: cm.xenonbyte.gestitre.domain.common.vo.Money.of(updateShareHolderViewRequest.getInitialBalance()))", target="initialBalance")
    @Mapping(expression = "java(updateShareHolderViewRequest.getBankAccountNumber() == null || updateShareHolderViewRequest.getBankAccountNumber().isEmpty()? null: cm.xenonbyte.gestitre.domain.shareholder.vo.BankAccountNumber.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(updateShareHolderViewRequest.getBankAccountNumber())))", target="bankAccountNumber")
    @Mapping(expression = "java(updateShareHolderViewRequest.getAdministrator() == null || updateShareHolderViewRequest.getAdministrator().isEmpty()? null: cm.xenonbyte.gestitre.domain.shareholder.vo.Administrator.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(updateShareHolderViewRequest.getAdministrator())))", target="administrator")
    @Mapping(expression = "java(updateShareHolderViewRequest.getEmail() == null || updateShareHolderViewRequest.getEmail().isEmpty()? null: cm.xenonbyte.gestitre.domain.common.vo.Email.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(updateShareHolderViewRequest.getEmail())))", target="email")
    @Mapping(expression = "java(updateShareHolderViewRequest.getPhone() == null || updateShareHolderViewRequest.getPhone().isEmpty()? null: cm.xenonbyte.gestitre.domain.common.vo.Phone.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(updateShareHolderViewRequest.getPhone())))", target="phone")
    @Mapping(expression = "java(updateShareHolderViewRequest.getCity() == null || updateShareHolderViewRequest.getCity().isEmpty()? null: cm.xenonbyte.gestitre.domain.common.vo.City.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(updateShareHolderViewRequest.getCity())))", target="city")
    @Mapping(expression = "java(updateShareHolderViewRequest.getZipCode() == null || updateShareHolderViewRequest.getZipCode().isEmpty()? null: cm.xenonbyte.gestitre.domain.common.vo.ZipCode.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(updateShareHolderViewRequest.getZipCode())))", target="zipCode")
    @Mapping(expression = "java(updateShareHolderViewRequest.getShareHolderType() == null? null: cm.xenonbyte.gestitre.domain.shareholder.vo.ShareHolderType.valueOf(updateShareHolderViewRequest.getShareHolderType().name()))", target="shareHolderType")
    @Mapping(expression = "java(updateShareHolderViewRequest.getActive() == null? null: cm.xenonbyte.gestitre.domain.common.vo.Active.with(updateShareHolderViewRequest.getActive()))", target="active")
    @Mapping(source = "representative", qualifiedByName = "representativeViewRequestToRepresentative", target = "representative")
    @Mapping(source = "successor", qualifiedByName = "successorViewRequestToSuccessor", target = "successor")
    @Mapping(expression = "java(updateShareHolderViewRequest.getCreatedDate().withZoneSameInstant(java.time.ZoneOffset.UTC))", target="createdDate")
    @Nonnull ShareHolder toShareHolder(@Nonnull @Valid UpdateShareShareHolderRequestView updateShareHolderViewRequest);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "name.text.value", target = "name")
    @Mapping(source = "accountNumber.text.value", target = "accountNumber")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.application.shareholder.dto.AccountTypeView.valueOf(shareHolder.getAccountType().name()))", target = "accountType")
    @Mapping(source = "taxResidence.text.value", target = "taxResidence")
    @Mapping(expression = "java(shareHolder.getInitialBalance() == null? null: shareHolder.getInitialBalance().value())", target="initialBalance")
    @Mapping(expression = "java(shareHolder.getBankAccountNumber() == null || shareHolder.getBankAccountNumber().text().value().isEmpty()? null: shareHolder.getBankAccountNumber().text().value())", target="bankAccountNumber")
    @Mapping(expression = "java(shareHolder.getAdministrator() == null || shareHolder.getAdministrator().text().value().isEmpty()? null: shareHolder.getAdministrator().text().value())", target="administrator")
    @Mapping(expression = "java(shareHolder.getEmail() == null || shareHolder.getEmail().text().value().isEmpty()? null: shareHolder.getEmail().text().value())", target="email")
    @Mapping(expression = "java(shareHolder.getPhone() == null || shareHolder.getPhone().text().value().isEmpty()? null: shareHolder.getPhone().text().value())", target="phone")
    @Mapping(expression = "java(shareHolder.getCity() == null || shareHolder.getCity().text().value().isEmpty()? null: shareHolder.getCity().text().value())", target="city")
    @Mapping(expression = "java(shareHolder.getZipCode() == null || shareHolder.getZipCode().text().value().isEmpty()? null: shareHolder.getZipCode().text().value())", target="zipCode")
    @Mapping(expression = "java(shareHolder.getShareHolderType() == null? null: cm.xenonbyte.gestitre.application.shareholder.dto.ShareHolderTypeView.valueOf(shareHolder.getShareHolderType().name()))", target="shareHolderType")
    @Mapping(expression = "java(shareHolder.getActive() == null? null: shareHolder.getActive().value())", target="active")
    @Mapping(source = "representative", qualifiedByName = "representativeToRepresentativeView", target = "representative")
    @Mapping(source = "successor", qualifiedByName = "successorToSuccessorView", target = "successor")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.context.TimezoneContext.current() == null? null: shareHolder.getCreatedDate().withZoneSameInstant(java.time.ZoneId.of(cm.xenonbyte.gestitre.domain.context.TimezoneContext.current().getName())))", target="createdDate")
    @Mapping(expression = "java(shareHolder.getTenantId().getValue() == null? cm.xenonbyte.gestitre.domain.context.TenantContext.current(): shareHolder.getId().getValue())", target="tenantId")
    @Nonnull @Valid UpdateShareHolderResponseView toUpdateShareHolderViewResponse(@Nonnull ShareHolder shareHolder);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "name.text.value", target = "name")
    @Mapping(source = "accountNumber.text.value", target = "accountNumber")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.application.shareholder.dto.AccountTypeView.valueOf(shareHolder.getAccountType().name()))", target = "accountType")
    @Mapping(source = "taxResidence.text.value", target = "taxResidence")
    @Mapping(expression = "java(shareHolder.getInitialBalance() == null? null: shareHolder.getInitialBalance().value())", target="initialBalance")
    @Mapping(expression = "java(shareHolder.getBankAccountNumber() == null || shareHolder.getBankAccountNumber().text().value().isEmpty()? null: shareHolder.getBankAccountNumber().text().value())", target="bankAccountNumber")
    @Mapping(expression = "java(shareHolder.getAdministrator() == null || shareHolder.getAdministrator().text().value().isEmpty()? null: shareHolder.getAdministrator().text().value())", target="administrator")
    @Mapping(expression = "java(shareHolder.getEmail() == null || shareHolder.getEmail().text().value().isEmpty()? null: shareHolder.getEmail().text().value())", target="email")
    @Mapping(expression = "java(shareHolder.getPhone() == null || shareHolder.getPhone().text().value().isEmpty()? null: shareHolder.getPhone().text().value())", target="phone")
    @Mapping(expression = "java(shareHolder.getCity() == null || shareHolder.getCity().text().value().isEmpty()? null: shareHolder.getCity().text().value())", target="city")
    @Mapping(expression = "java(shareHolder.getZipCode() == null || shareHolder.getZipCode().text().value().isEmpty()? null: shareHolder.getZipCode().text().value())", target="zipCode")
    @Mapping(expression = "java(shareHolder.getShareHolderType() == null? null: cm.xenonbyte.gestitre.application.shareholder.dto.ShareHolderTypeView.valueOf(shareHolder.getShareHolderType().name()))", target="shareHolderType")
    @Mapping(expression = "java(shareHolder.getActive() == null? null: shareHolder.getActive().value())", target="active")
    @Mapping(source = "representative", qualifiedByName = "representativeToRepresentativeView", target = "representative")
    @Mapping(source = "successor", qualifiedByName = "successorToSuccessorView", target = "successor")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.context.TimezoneContext.current() == null? null: shareHolder.getCreatedDate().withZoneSameInstant(java.time.ZoneId.of(cm.xenonbyte.gestitre.domain.context.TimezoneContext.current().getName())))", target="createdDate")
    @Mapping(source = "tenantId.value", target="tenantId")
    @Nonnull @Valid
    FindShareHolderByIdResponseView toFindShareHolderByIdViewResponse(ShareHolder shareHolder);
}
