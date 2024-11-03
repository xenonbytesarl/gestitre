package cm.xenonbyte.gestitre.infrastructure.company;

import cm.xenonbyte.gestitre.domain.company.entity.Company;
import jakarta.annotation.Nonnull;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * @author bamk
 * @version 1.0
 * @since 03/11/2024
 */
@Mapper(
        componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface CompanyJpaMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "companyName.text.value", target = "companyName")
    @Mapping(source = "companyManagerName.text.value", target = "companyManagerName")
    @Mapping(expression = "java(LicenceJpa.valueOf(company.getLicence().name()))", target = "licenceJpa")
    @Mapping(expression = "java(LegalFormJpa.valueOf(company.getLegalForm().name()))", target = "legalFormJpa")
    @Mapping(expression = "java(company.getAddress().street() == null || company.getAddress().street().text().value().isEmpty() ? null: company.getAddress().street().text().value())", target = "addressJpa.street")
    @Mapping(expression = "java(company.getAddress().city().text().value())", target = "addressJpa.city")
    @Mapping(expression = "java(company.getAddress().zipCode().text().value())", target = "addressJpa.zipCode")
    @Mapping(expression = "java(company.getAddress().country().text().value())", target = "addressJpa.country")
    @Mapping(expression = "java(company.getContact().phone() == null || company.getContact().phone().text().value().isEmpty() ? null: company.getContact().phone().text().value())", target = "contactJpa.phone")
    @Mapping(expression = "java(company.getContact().fax() == null || company.getContact().fax().text().value().isEmpty() ? null: company.getContact().fax().text().value())", target = "contactJpa.fax")
    @Mapping(expression = "java(company.getContact().email().text().value())", target = "contactJpa.email")
    @Mapping(expression = "java(company.getContact().name().text().value())", target = "contactJpa.name")
    @Mapping(expression = "java(company.getActivity() == null || company.getActivity().text().value().isEmpty()? null: company.getActivity().text().value())", target = "activity")
    @Mapping(expression = "java(company.getRegistrationNumber() == null || company.getRegistrationNumber().text().value().isEmpty()? null: company.getRegistrationNumber().text().value())", target = "registrationNumber")
    @Mapping(expression = "java(company.getWebSiteUrl() == null || company.getWebSiteUrl().text().value().isEmpty()? null: company.getWebSiteUrl().text().value())", target = "webSiteUrl")
    @Mapping(expression = "java(company.getIsinCode() == null || company.getIsinCode().text().value().isEmpty()? null: company.getIsinCode().text().value())", target = "isinCode")
    @Mapping(expression = "java(company.getTaxNumber() == null || company.getTaxNumber().text().value().isEmpty()? null: company.getTaxNumber().text().value())", target = "taxNumber")
    @Mapping(expression = "java(company.getLogoFilename().text().value())", target = "logoFilename")
    @Mapping(expression = "java(company.getStampFilename().text().value())", target = "stampFilename")
    @Mapping(expression = "java(company.getGrossDividendStockUnit() == null? null: company.getGrossDividendStockUnit().amount().value())", target = "grossDividendStockUnit")
    @Mapping(expression = "java(company.getNominalValue() == null? null: company.getNominalValue().amount().value())", target = "nominalValue")
    @Mapping(expression = "java(company.getStockQuantity() == null? null: company.getStockQuantity().value())", target = "stockQuantity")
    @Mapping(source = "active.value", target="active")
    @Mapping(expression = "java(company.getCertificateTemplateId() == null? null : cm.xenonbyte.gestitre.infrastructure.company.certificatetemplate.CertificateTemplateJpa.builder().id(company.getCertificateTemplateId().getValue()).build())", target = "certificateTemplateJpa")
    @Nonnull CompanyJpa toCompanyJpa(@Nonnull Company company);


    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id.value", source = "id")
    @Mapping(target = "companyName.text.value", source = "companyName")
    @Mapping(target = "companyManagerName.text.value", source = "companyManagerName")
    @Mapping(target = "licence", expression = "java(cm.xenonbyte.gestitre.domain.company.vo.Licence.valueOf(companyJpa.getLicenceJpa().name()))")
    @Mapping(target = "legalForm", expression = "java(cm.xenonbyte.gestitre.domain.company.vo.LegalForm.valueOf(companyJpa.getLegalFormJpa().name()))")
    @Mapping(target = "address.street", expression = "java(companyJpa.getAddressJpa().getStreet() == null || companyJpa.getAddressJpa().getStreet().isEmpty() ? null: cm.xenonbyte.gestitre.domain.company.vo.address.Street.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(companyJpa.getAddressJpa().getStreet())))")
    @Mapping(target = "address.city", expression = "java(cm.xenonbyte.gestitre.domain.company.vo.address.City.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(companyJpa.getAddressJpa().getCity())))")
    @Mapping(target = "address.zipCode", expression = "java(cm.xenonbyte.gestitre.domain.company.vo.address.ZipCode.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(companyJpa.getAddressJpa().getZipCode())))")
    @Mapping(target = "address.country", expression = "java(cm.xenonbyte.gestitre.domain.company.vo.address.Country.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(companyJpa.getAddressJpa().getCountry())))")
    @Mapping(target = "contact.phone", expression = "java(companyJpa.getContactJpa().getPhone() == null || companyJpa.getContactJpa().getPhone().isEmpty() ? null: cm.xenonbyte.gestitre.domain.company.vo.contact.Phone.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(companyJpa.getContactJpa().getPhone())))")
    @Mapping(target = "contact.fax", expression = "java(companyJpa.getContactJpa().getFax() == null || companyJpa.getContactJpa().getFax().isEmpty() ? null: cm.xenonbyte.gestitre.domain.company.vo.contact.Fax.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(companyJpa.getContactJpa().getFax())))")
    @Mapping(target = "contact.email", expression = "java(cm.xenonbyte.gestitre.domain.company.vo.contact.Email.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(companyJpa.getContactJpa().getEmail())))")
    @Mapping(target = "contact.name", expression = "java(cm.xenonbyte.gestitre.domain.common.vo.Name.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(companyJpa.getContactJpa().getName())))")
    @Mapping(target = "activity", expression = "java(companyJpa.getActivity() == null || companyJpa.getActivity().isEmpty()? null: cm.xenonbyte.gestitre.domain.company.vo.Activity.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(companyJpa.getActivity())))")
    @Mapping(target = "registrationNumber", expression = "java(companyJpa.getRegistrationNumber() == null || companyJpa.getRegistrationNumber().isEmpty()? null: cm.xenonbyte.gestitre.domain.company.vo.RegistrationNumber.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(companyJpa.getRegistrationNumber())))")
    @Mapping(target = "webSiteUrl", expression = "java(companyJpa.getWebSiteUrl() == null || companyJpa.getWebSiteUrl().isEmpty()? null: cm.xenonbyte.gestitre.domain.company.vo.WebSiteUrl.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(companyJpa.getWebSiteUrl())))")
    @Mapping(target = "isinCode", expression = "java(companyJpa.getIsinCode() == null || companyJpa.getIsinCode().isEmpty()? null: cm.xenonbyte.gestitre.domain.company.vo.IsinCode.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(companyJpa.getIsinCode())))")
    @Mapping(target = "taxNumber", expression = "java(companyJpa.getTaxNumber() == null || companyJpa.getTaxNumber().isEmpty()? null: cm.xenonbyte.gestitre.domain.company.vo.TaxNumber.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(companyJpa.getTaxNumber())))")
    @Mapping(target = "logoFilename", expression = "java(cm.xenonbyte.gestitre.domain.common.vo.Filename.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(companyJpa.getLogoFilename())))")
    @Mapping(target = "stampFilename", expression = "java(cm.xenonbyte.gestitre.domain.common.vo.Filename.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(companyJpa.getStampFilename())))")
    @Mapping(target = "grossDividendStockUnit", expression = "java(companyJpa.getGrossDividendStockUnit() == null? null: cm.xenonbyte.gestitre.domain.company.vo.GrossDividendStockUnit.of(cm.xenonbyte.gestitre.domain.common.vo.Money.of(companyJpa.getGrossDividendStockUnit())))")
    @Mapping(target = "nominalValue", expression = "java(companyJpa.getNominalValue() == null? null: cm.xenonbyte.gestitre.domain.company.vo.NominalValue.of(cm.xenonbyte.gestitre.domain.common.vo.Money.of(companyJpa.getNominalValue())))")
    @Mapping(target = "stockQuantity", expression = "java(companyJpa.getStockQuantity() == null? null: cm.xenonbyte.gestitre.domain.company.vo.Quantity.of(companyJpa.getStockQuantity()))")
    @Mapping(target = "active.value", source="active")
    @Mapping(target = "certificateTemplateId", expression = "java(companyJpa.getCertificateTemplateJpa() == null? null : new cm.xenonbyte.gestitre.domain.company.vo.CertificateTemplateId(companyJpa.getCertificateTemplateJpa().getId()))")
    @Nonnull Company toCompany(@Nonnull CompanyJpa companyJpa);

    void copyNewToOldCompanyJpa(@Nonnull CompanyJpa newCompanyJpa, @Nonnull @MappingTarget CompanyJpa oldCompanyJpa);
}
