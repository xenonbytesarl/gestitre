package cm.xenonbyte.gestitre.application.company;

import cm.xenonbyte.gestitre.application.company.dto.AddressView;
import cm.xenonbyte.gestitre.application.company.dto.ContactView;
import cm.xenonbyte.gestitre.application.company.dto.CreateCompanyViewRequest;
import cm.xenonbyte.gestitre.application.company.dto.CreateCompanyViewResponse;
import cm.xenonbyte.gestitre.application.company.dto.FindCompaniesViewResponse;
import cm.xenonbyte.gestitre.application.company.dto.FindCompanyByIdViewResponse;
import cm.xenonbyte.gestitre.application.company.dto.FindCompanyPageInfoViewResponse;
import cm.xenonbyte.gestitre.application.company.dto.SearchCompaniesViewResponse;
import cm.xenonbyte.gestitre.application.company.dto.SearchCompanyPageInfoViewResponse;
import cm.xenonbyte.gestitre.application.company.dto.UpdateCompanyViewRequest;
import cm.xenonbyte.gestitre.application.company.dto.UpdateCompanyViewResponse;
import cm.xenonbyte.gestitre.domain.common.vo.Image;
import cm.xenonbyte.gestitre.domain.common.vo.PageInfo;
import cm.xenonbyte.gestitre.domain.company.entity.Company;
import cm.xenonbyte.gestitre.domain.company.vo.address.Address;
import cm.xenonbyte.gestitre.domain.company.vo.contact.Contact;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * @author bamk
 * @version 1.0
 * @since 04/11/2024
 */
@Mapper(
        componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface CompanyApplicationViewMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "createCompanyViewRequest.companyName", target="companyName.text.value")
    @Mapping(source = "createCompanyViewRequest.companyManagerName", target="companyManagerName.text.value")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.company.vo.Licence.valueOf(createCompanyViewRequest.getLicence()))", target="licence")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.company.vo.LegalForm.valueOf(createCompanyViewRequest.getLegalForm()))", target="legalForm")
    @Mapping(source = "createCompanyViewRequest.address", qualifiedByName = "companyViewToAddress", target = "address")
    @Mapping(source = "createCompanyViewRequest.contact", qualifiedByName = "companyViewToContact", target = "contact")
    @Mapping(expression = "java(createCompanyViewRequest.getActivity() == null || createCompanyViewRequest.getActivity().isEmpty() ? null: cm.xenonbyte.gestitre.domain.company.vo.Activity.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(createCompanyViewRequest.getActivity())))", target="activity")
    @Mapping(expression = "java(createCompanyViewRequest.getRegistrationNumber() == null || createCompanyViewRequest.getRegistrationNumber().isEmpty()? null: cm.xenonbyte.gestitre.domain.company.vo.RegistrationNumber.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(createCompanyViewRequest.getRegistrationNumber())))", target="registrationNumber")
    @Mapping(expression = "java(createCompanyViewRequest.getCertificateTemplateId() == null? null: new cm.xenonbyte.gestitre.domain.company.vo.CertificateTemplateId(createCompanyViewRequest.getCertificateTemplateId()))", target="certificateTemplateId")
    @Mapping(expression = "java(createCompanyViewRequest.getWebSiteUrl() == null || createCompanyViewRequest.getWebSiteUrl().isEmpty()? null: cm.xenonbyte.gestitre.domain.company.vo.WebSiteUrl.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(createCompanyViewRequest.getWebSiteUrl())))", target="webSiteUrl")
    @Mapping(expression = "java(createCompanyViewRequest.getIsinCode() == null || createCompanyViewRequest.getIsinCode().isEmpty()? null: cm.xenonbyte.gestitre.domain.company.vo.IsinCode.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(createCompanyViewRequest.getIsinCode())))", target="isinCode")
    @Mapping(expression = "java(createCompanyViewRequest.getTaxNumber() == null || createCompanyViewRequest.getTaxNumber().isEmpty()? null: cm.xenonbyte.gestitre.domain.company.vo.TaxNumber.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(createCompanyViewRequest.getTaxNumber())))", target="taxNumber")
    @Mapping(expression = "java(logo == null ? null: cm.xenonbyte.gestitre.domain.common.vo.Filename.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(logo.name().value())))", target="logoFilename")
    @Mapping(expression = "java(stamp == null ? null: cm.xenonbyte.gestitre.domain.common.vo.Filename.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(stamp.name().value())))", target="stampFilename")
    @Mapping(expression = "java(createCompanyViewRequest.getGrossDividendStockUnit() == null? null: cm.xenonbyte.gestitre.domain.company.vo.GrossDividendStockUnit.of(cm.xenonbyte.gestitre.domain.common.vo.Money.of(createCompanyViewRequest.getGrossDividendStockUnit())))", target="grossDividendStockUnit")
    @Mapping(expression = "java(createCompanyViewRequest.getNominalValue() == null? null: cm.xenonbyte.gestitre.domain.company.vo.NominalValue.of(cm.xenonbyte.gestitre.domain.common.vo.Money.of(createCompanyViewRequest.getNominalValue())))", target="nominalValue")
    @Nonnull Company toCompany(@Nonnull @Valid CreateCompanyViewRequest createCompanyViewRequest, Image logo, Image stamp);

    @Named("companyViewToAddress")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(expression = "java(addressView.getStreet() == null || addressView.getStreet().isEmpty() ? null: cm.xenonbyte.gestitre.domain.company.vo.address.Street.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(addressView.getStreet())))", target="street")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.common.vo.City.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(addressView.getCity())))", target="city")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.common.vo.ZipCode.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(addressView.getZipCode())))", target="zipCode")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.common.vo.Country.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(addressView.getCountry())))", target="country")
    @Nonnull Address toAddress(@Nonnull @Valid AddressView addressView);

    @Named("companyViewToContact")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(expression = "java(contactView.getPhone() == null || contactView.getPhone().isEmpty()? null: cm.xenonbyte.gestitre.domain.common.vo.Phone.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(contactView.getPhone())))", target="phone")
    @Mapping(expression = "java(contactView.getFax() == null || contactView.getFax().isEmpty()? null: cm.xenonbyte.gestitre.domain.company.vo.contact.Fax.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(contactView.getFax())))", target="fax")
    @Mapping(source = "email", target="email.text.value")
    @Mapping(source = "name", target="name.text.value")
    @Nonnull Contact toContact(@Nonnull @Valid ContactView contactView);


    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "companyName", source="companyName.text.value")
    @Mapping(target = "companyManagerName", source="companyManagerName.text.value")
    @Mapping(target = "licence", expression = "java(company.getLicence().name())")
    @Mapping(target = "legalForm", expression = "java(company.getLegalForm().name())")
    @Mapping(target = "address", qualifiedByName = "companyToAddressView", source = "address")
    @Mapping(target = "contact", qualifiedByName = "companyToContactView", source = "contact")
    @Mapping(target = "activity", expression = "java(company.getActivity() == null || company.getActivity().text().value().isEmpty()? null: company.getActivity().text().value())")
    @Mapping(target = "registrationNumber", expression = "java(company.getRegistrationNumber() == null || company.getRegistrationNumber().text().value().isEmpty()? null: company.getRegistrationNumber().text().value())")
    @Mapping(target = "certificateTemplateId", expression = "java(company.getCertificateTemplateId() == null ? null: company.getCertificateTemplateId().getValue())")
    @Mapping(target = "webSiteUrl", expression = "java(company.getWebSiteUrl() == null || company.getWebSiteUrl().text().value().isEmpty()? null: company.getWebSiteUrl().text().value())")
    @Mapping(target = "isinCode", expression = "java(company.getIsinCode() == null || company.getIsinCode().text().value().isEmpty()? null: company.getIsinCode().text().value())")
    @Mapping(target = "taxNumber", expression = "java(company.getTaxNumber() == null || company.getTaxNumber().text().value().isEmpty()? null: company.getTaxNumber().text().value())")
    @Mapping(target = "logoFilename", expression = "java(company.getLogoFilename() == null? null: company.getLogoFilename().text().value())")
    @Mapping(target = "stampFilename", expression = "java(company.getStampFilename() == null? null: company.getStampFilename().text().value())")
    @Mapping(target = "grossDividendStockUnit", expression = "java(company.getGrossDividendStockUnit() == null? null: company.getGrossDividendStockUnit().amount().value())")
    @Mapping(target = "nominalValue", expression = "java(company.getNominalValue() == null? null: company.getNominalValue().amount().value())")
    @Mapping(target = "netDividendStock", expression = "java(company.getNetDividendStock() == null? null: company.getNetDividendStock().amount().value())")
    @Mapping(target = "ircmRetain", expression = "java(company.getIrcmRetain() == null? null: company.getIrcmRetain().amount().value())")
    @Mapping(target = "capitalization", expression = "java(company.getCapitalization() == null? null: company.getCapitalization().getAmount().value())")
    @Mapping(target = "stockQuantity", expression = "java(company.getStockQuantity() == null? null: company.getStockQuantity().value())")
    @Mapping(target = "endLicenceDate", expression = "java(company.getCreatedDate() != null && company.getLicence()!= null? cm.xenonbyte.gestitre.domain.company.vo.EndLicence.of(company.getCreatedDate(), company.getLicence()).computeEndLicenceDate(): null)")
    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(target = "active", source = "active.value")
    @Nonnull @Valid CreateCompanyViewResponse toCompanyCreateViewResponse(@Nonnull Company company);

    @Named("companyToAddressView")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(expression = "java(address.street() == null || address.street().text().value().isEmpty()? null: address.street().text().value())", target="street")
    @Mapping(source = "city.text.value", target="city")
    @Mapping(source = "zipCode.text.value", target="zipCode")
    @Mapping(source = "country.text.value", target="country")
    @Nonnull @Valid AddressView toAddressView(@Nonnull Address address);

    @Named("companyToContactView")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(expression = "java(contact.phone() == null || contact.phone().text().value().isEmpty()? null: contact.phone().text().value())", target="phone")
    @Mapping(expression = "java(contact.fax() == null || contact.fax().text().value().isEmpty()? null: contact.fax().text().value())", target="fax")
    @Mapping(source = "email.text.value", target="email")
    @Mapping(source = "name.text.value", target="name")
    @Nonnull @Valid ContactView toContactView(@Nonnull Contact contact);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "first", target = "first")
    @Mapping(source = "last", target = "last")
    @Mapping(source = "pageSize", target = "pageSize")
    @Mapping(source = "totalPages", target = "totalPages")
    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(source = "elements", qualifiedByName = "toFindCompanyViewResponses", target = "elements")
    @Nonnull @Valid FindCompanyPageInfoViewResponse toFindCompanyPageInfoViewResponse(@Nonnull PageInfo<Company> companyPageInfo);

    @Named("toFindCompanyViewResponses")
    @Nonnull @Valid List<FindCompaniesViewResponse> toFindCompaniesViewResponses(@Nonnull List<Company> companies);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "companyName", source="companyName.text.value")
    @Mapping(target = "companyManagerName", source="companyManagerName.text.value")
    @Mapping(target = "licence", expression = "java(company.getLicence().name())")
    @Mapping(target = "legalForm", expression = "java(company.getLegalForm().name())")
    @Mapping(target = "address", qualifiedByName = "companyToAddressView", source = "address")
    @Mapping(target = "contact", qualifiedByName = "companyToContactView", source = "contact")
    @Mapping(target = "activity", expression = "java(company.getActivity() == null || company.getActivity().text().value().isEmpty()? null: company.getActivity().text().value())")
    @Mapping(target = "registrationNumber", expression = "java(company.getRegistrationNumber() == null || company.getRegistrationNumber().text().value().isEmpty()? null: company.getRegistrationNumber().text().value())")
    @Mapping(target = "certificateTemplateId", expression = "java(company.getCertificateTemplateId() == null ? null: company.getCertificateTemplateId().getValue())")
    @Mapping(target = "webSiteUrl", expression = "java(company.getWebSiteUrl() == null || company.getWebSiteUrl().text().value().isEmpty()? null: company.getWebSiteUrl().text().value())")
    @Mapping(target = "isinCode", expression = "java(company.getIsinCode() == null || company.getIsinCode().text().value().isEmpty()? null: company.getIsinCode().text().value())")
    @Mapping(target = "taxNumber", expression = "java(company.getTaxNumber() == null || company.getTaxNumber().text().value().isEmpty()? null: company.getTaxNumber().text().value())")
    @Mapping(target = "logoFilename", expression = "java(company.getLogoFilename() == null? null: company.getLogoFilename().text().value())")
    @Mapping(target = "stampFilename", expression = "java(company.getStampFilename() == null? null: company.getStampFilename().text().value())")
    @Mapping(target = "grossDividendStockUnit", expression = "java(company.getGrossDividendStockUnit() == null? null: company.getGrossDividendStockUnit().amount().value())")
    @Mapping(target = "nominalValue", expression = "java(company.getNominalValue() == null? null: company.getNominalValue().amount().value())")
    @Mapping(target = "netDividendStock", expression = "java(company.getNetDividendStock() == null? null: company.getNetDividendStock().amount().value())")
    @Mapping(target = "ircmRetain", expression = "java(company.getIrcmRetain() == null? null: company.getIrcmRetain().amount().value())")
    @Mapping(target = "capitalization", expression = "java(company.getCapitalization() == null? null: company.getCapitalization().getAmount().value())")
    @Mapping(target = "stockQuantity", expression = "java(company.getStockQuantity() == null? null: company.getStockQuantity().value())")
    @Mapping(target = "endLicenceDate", expression = "java(company.getCreatedDate() != null && company.getLicence()!= null? cm.xenonbyte.gestitre.domain.company.vo.EndLicence.of(company.getCreatedDate(), company.getLicence()).computeEndLicenceDate(): null)")
    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(target = "active", source = "active.value")
    @Nonnull @Valid FindCompaniesViewResponse toFindCompaniesViewResponse(@Nonnull Company company);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "first", target = "first")
    @Mapping(source = "last", target = "last")
    @Mapping(source = "pageSize", target = "pageSize")
    @Mapping(source = "totalPages", target = "totalPages")
    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(source = "elements", qualifiedByName = "toSearchCompanyViewResponses", target = "elements")
    @Nonnull @Valid
    SearchCompanyPageInfoViewResponse toSearchCompanyPageInfoViewResponse(@Nonnull PageInfo<Company> companyPageInfo);

    @Named("toSearchCompanyViewResponses")
    @Nonnull @Valid List<SearchCompaniesViewResponse> toSearchCompaniesViewResponses(@Nonnull List<Company> companies);


    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "companyName", source="companyName.text.value")
    @Mapping(target = "companyManagerName", source="companyManagerName.text.value")
    @Mapping(target = "licence", expression = "java(company.getLicence().name())")
    @Mapping(target = "legalForm", expression = "java(company.getLegalForm().name())")
    @Mapping(target = "address", qualifiedByName = "companyToAddressView", source = "address")
    @Mapping(target = "contact", qualifiedByName = "companyToContactView", source = "contact")
    @Mapping(target = "activity", expression = "java(company.getActivity() == null || company.getActivity().text().value().isEmpty()? null: company.getActivity().text().value())")
    @Mapping(target = "registrationNumber", expression = "java(company.getRegistrationNumber() == null || company.getRegistrationNumber().text().value().isEmpty()? null: company.getRegistrationNumber().text().value())")
    @Mapping(target = "certificateTemplateId", expression = "java(company.getCertificateTemplateId() == null ? null: company.getCertificateTemplateId().getValue())")
    @Mapping(target = "webSiteUrl", expression = "java(company.getWebSiteUrl() == null || company.getWebSiteUrl().text().value().isEmpty()? null: company.getWebSiteUrl().text().value())")
    @Mapping(target = "isinCode", expression = "java(company.getIsinCode() == null || company.getIsinCode().text().value().isEmpty()? null: company.getIsinCode().text().value())")
    @Mapping(target = "taxNumber", expression = "java(company.getTaxNumber() == null || company.getTaxNumber().text().value().isEmpty()? null: company.getTaxNumber().text().value())")
    @Mapping(target = "logoFilename", expression = "java(company.getLogoFilename() == null? null: company.getLogoFilename().text().value())")
    @Mapping(target = "stampFilename", expression = "java(company.getStampFilename() == null? null: company.getStampFilename().text().value())")
    @Mapping(target = "grossDividendStockUnit", expression = "java(company.getGrossDividendStockUnit() == null? null: company.getGrossDividendStockUnit().amount().value())")
    @Mapping(target = "nominalValue", expression = "java(company.getNominalValue() == null? null: company.getNominalValue().amount().value())")
    @Mapping(target = "netDividendStock", expression = "java(company.getNetDividendStock() == null? null: company.getNetDividendStock().amount().value())")
    @Mapping(target = "ircmRetain", expression = "java(company.getIrcmRetain() == null? null: company.getIrcmRetain().amount().value())")
    @Mapping(target = "capitalization", expression = "java(company.getCapitalization() == null? null: company.getCapitalization().getAmount().value())")
    @Mapping(target = "stockQuantity", expression = "java(company.getStockQuantity() == null? null: company.getStockQuantity().value())")
    @Mapping(target = "endLicenceDate", expression = "java(company.getCreatedDate() != null && company.getLicence()!= null? cm.xenonbyte.gestitre.domain.company.vo.EndLicence.of(company.getCreatedDate(), company.getLicence()).computeEndLicenceDate(): null)")
    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(target = "active", source = "active.value")
    @Nonnull @Valid SearchCompaniesViewResponse toSearchCompaniesViewResponse(@Nonnull Company company);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "companyName", source="companyName.text.value")
    @Mapping(target = "companyManagerName", source="companyManagerName.text.value")
    @Mapping(target = "licence", expression = "java(company.getLicence().name())")
    @Mapping(target = "legalForm", expression = "java(company.getLegalForm().name())")
    @Mapping(target = "address", qualifiedByName = "companyToAddressView", source = "address")
    @Mapping(target = "contact", qualifiedByName = "companyToContactView", source = "contact")
    @Mapping(target = "activity", expression = "java(company.getActivity() == null || company.getActivity().text().value().isEmpty()? null: company.getActivity().text().value())")
    @Mapping(target = "registrationNumber", expression = "java(company.getRegistrationNumber() == null || company.getRegistrationNumber().text().value().isEmpty()? null: company.getRegistrationNumber().text().value())")
    @Mapping(target = "certificateTemplateId", expression = "java(company.getCertificateTemplateId() == null ? null: company.getCertificateTemplateId().getValue())")
    @Mapping(target = "webSiteUrl", expression = "java(company.getWebSiteUrl() == null || company.getWebSiteUrl().text().value().isEmpty()? null: company.getWebSiteUrl().text().value())")
    @Mapping(target = "isinCode", expression = "java(company.getIsinCode() == null || company.getIsinCode().text().value().isEmpty()? null: company.getIsinCode().text().value())")
    @Mapping(target = "taxNumber", expression = "java(company.getTaxNumber() == null || company.getTaxNumber().text().value().isEmpty()? null: company.getTaxNumber().text().value())")
    @Mapping(target = "logoFilename", expression = "java(company.getLogoFilename() == null? null: company.getLogoFilename().text().value())")
    @Mapping(target = "stampFilename", expression = "java(company.getStampFilename() == null? null: company.getStampFilename().text().value())")
    @Mapping(target = "grossDividendStockUnit", expression = "java(company.getGrossDividendStockUnit() == null? null: company.getGrossDividendStockUnit().amount().value())")
    @Mapping(target = "nominalValue", expression = "java(company.getNominalValue() == null? null: company.getNominalValue().amount().value())")
    @Mapping(target = "netDividendStock", expression = "java(company.getNetDividendStock() == null? null: company.getNetDividendStock().amount().value())")
    @Mapping(target = "ircmRetain", expression = "java(company.getIrcmRetain() == null? null: company.getIrcmRetain().amount().value())")
    @Mapping(target = "capitalization", expression = "java(company.getCapitalization() == null? null: company.getCapitalization().getAmount().value())")
    @Mapping(target = "stockQuantity", expression = "java(company.getStockQuantity() == null? null: company.getStockQuantity().value())")
    @Mapping(target = "endLicenceDate", expression = "java(company.getCreatedDate() != null && company.getLicence()!= null? cm.xenonbyte.gestitre.domain.company.vo.EndLicence.of(company.getCreatedDate(), company.getLicence()).computeEndLicenceDate(): null)")
    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(target = "active", source = "active.value")
    @Nonnull @Valid FindCompanyByIdViewResponse toFindByIdViewResponse(@Nonnull Company company);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "updateCompanyViewRequest.id", target = "id.value")
    @Mapping(source = "updateCompanyViewRequest.companyName", target="companyName.text.value")
    @Mapping(source = "updateCompanyViewRequest.companyManagerName", target="companyManagerName.text.value")
    @Mapping(source = "updateCompanyViewRequest.createdDate", target="createdDate")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.company.vo.Licence.valueOf(updateCompanyViewRequest.getLicence()))", target="licence")
    @Mapping(expression = "java(cm.xenonbyte.gestitre.domain.company.vo.LegalForm.valueOf(updateCompanyViewRequest.getLegalForm()))", target="legalForm")
    @Mapping(source = "updateCompanyViewRequest.address", qualifiedByName = "companyViewToAddress", target = "address")
    @Mapping(source = "updateCompanyViewRequest.contact", qualifiedByName = "companyViewToContact", target = "contact")
    @Mapping(expression = "java(updateCompanyViewRequest.getActivity() == null || updateCompanyViewRequest.getActivity().isEmpty() ? null: cm.xenonbyte.gestitre.domain.company.vo.Activity.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(updateCompanyViewRequest.getActivity())))", target="activity")
    @Mapping(expression = "java(updateCompanyViewRequest.getRegistrationNumber() == null || updateCompanyViewRequest.getRegistrationNumber().isEmpty()? null: cm.xenonbyte.gestitre.domain.company.vo.RegistrationNumber.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(updateCompanyViewRequest.getRegistrationNumber())))", target="registrationNumber")
    @Mapping(expression = "java(updateCompanyViewRequest.getCertificateTemplateId() == null? null: new cm.xenonbyte.gestitre.domain.company.vo.CertificateTemplateId(updateCompanyViewRequest.getCertificateTemplateId()))", target="certificateTemplateId")
    @Mapping(expression = "java(updateCompanyViewRequest.getWebSiteUrl() == null || updateCompanyViewRequest.getWebSiteUrl().isEmpty()? null: cm.xenonbyte.gestitre.domain.company.vo.WebSiteUrl.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(updateCompanyViewRequest.getWebSiteUrl())))", target="webSiteUrl")
    @Mapping(expression = "java(updateCompanyViewRequest.getIsinCode() == null || updateCompanyViewRequest.getIsinCode().isEmpty()? null: cm.xenonbyte.gestitre.domain.company.vo.IsinCode.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(updateCompanyViewRequest.getIsinCode())))", target="isinCode")
    @Mapping(expression = "java(updateCompanyViewRequest.getTaxNumber() == null || updateCompanyViewRequest.getTaxNumber().isEmpty()? null: cm.xenonbyte.gestitre.domain.company.vo.TaxNumber.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(updateCompanyViewRequest.getTaxNumber())))", target="taxNumber")
    @Mapping(expression = "java(updateCompanyViewRequest.getLogoFilename() != null && !updateCompanyViewRequest.getLogoFilename().isEmpty() ? cm.xenonbyte.gestitre.domain.common.vo.Filename.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(updateCompanyViewRequest.getLogoFilename())): logo == null? null: cm.xenonbyte.gestitre.domain.common.vo.Filename.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(logo.name().value())))", target="logoFilename")
    @Mapping(expression = "java(updateCompanyViewRequest.getStampFilename() != null && !updateCompanyViewRequest.getStampFilename().isEmpty()? cm.xenonbyte.gestitre.domain.common.vo.Filename.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(updateCompanyViewRequest.getStampFilename())): stamp == null? null: cm.xenonbyte.gestitre.domain.common.vo.Filename.of(cm.xenonbyte.gestitre.domain.common.vo.Text.of(stamp.name().value())))", target="stampFilename")
    @Mapping(expression = "java(updateCompanyViewRequest.getGrossDividendStockUnit() == null? null: cm.xenonbyte.gestitre.domain.company.vo.GrossDividendStockUnit.of(cm.xenonbyte.gestitre.domain.common.vo.Money.of(updateCompanyViewRequest.getGrossDividendStockUnit())))", target="grossDividendStockUnit")
    @Mapping(expression = "java(updateCompanyViewRequest.getNominalValue() == null? null: cm.xenonbyte.gestitre.domain.company.vo.NominalValue.of(cm.xenonbyte.gestitre.domain.common.vo.Money.of(updateCompanyViewRequest.getNominalValue())))", target="nominalValue")
    @Mapping(source = "updateCompanyViewRequest.active", target = "active.value")
    @Nonnull Company toCompany(@NonNull UpdateCompanyViewRequest updateCompanyViewRequest, Image logo, Image stamp);



    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "companyName", source="companyName.text.value")
    @Mapping(target = "companyManagerName", source="companyManagerName.text.value")
    @Mapping(target = "licence", expression = "java(company.getLicence().name())")
    @Mapping(target = "legalForm", expression = "java(company.getLegalForm().name())")
    @Mapping(target = "address", qualifiedByName = "companyToAddressView", source = "address")
    @Mapping(target = "contact", qualifiedByName = "companyToContactView", source = "contact")
    @Mapping(target = "activity", expression = "java(company.getActivity() == null || company.getActivity().text().value().isEmpty()? null: company.getActivity().text().value())")
    @Mapping(target = "registrationNumber", expression = "java(company.getRegistrationNumber() == null || company.getRegistrationNumber().text().value().isEmpty()? null: company.getRegistrationNumber().text().value())")
    @Mapping(target = "certificateTemplateId", expression = "java(company.getCertificateTemplateId() == null ? null: company.getCertificateTemplateId().getValue())")
    @Mapping(target = "webSiteUrl", expression = "java(company.getWebSiteUrl() == null || company.getWebSiteUrl().text().value().isEmpty()? null: company.getWebSiteUrl().text().value())")
    @Mapping(target = "isinCode", expression = "java(company.getIsinCode() == null || company.getIsinCode().text().value().isEmpty()? null: company.getIsinCode().text().value())")
    @Mapping(target = "taxNumber", expression = "java(company.getTaxNumber() == null || company.getTaxNumber().text().value().isEmpty()? null: company.getTaxNumber().text().value())")
    @Mapping(target = "logoFilename", expression = "java(company.getLogoFilename() == null? null: company.getLogoFilename().text().value())")
    @Mapping(target = "stampFilename", expression = "java(company.getStampFilename() == null? null: company.getStampFilename().text().value())")
    @Mapping(target = "grossDividendStockUnit", expression = "java(company.getGrossDividendStockUnit() == null? null: company.getGrossDividendStockUnit().amount().value())")
    @Mapping(target = "nominalValue", expression = "java(company.getNominalValue() == null? null: company.getNominalValue().amount().value())")
    @Mapping(target = "netDividendStock", expression = "java(company.getNetDividendStock() == null? null: company.getNetDividendStock().amount().value())")
    @Mapping(target = "ircmRetain", expression = "java(company.getIrcmRetain() == null? null: company.getIrcmRetain().amount().value())")
    @Mapping(target = "capitalization", expression = "java(company.getCapitalization() == null? null: company.getCapitalization().getAmount().value())")
    @Mapping(target = "stockQuantity", expression = "java(company.getStockQuantity() == null? null: company.getStockQuantity().value())")
    @Mapping(target = "endLicenceDate", expression = "java(company.getCreatedDate() != null && company.getLicence()!= null? cm.xenonbyte.gestitre.domain.company.vo.EndLicence.of(company.getCreatedDate(), company.getLicence()).computeEndLicenceDate(): null)")
    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(target = "active", source = "active.value")
    @NonNull @Valid UpdateCompanyViewResponse toUpdateCompanyViewResponse(@Nonnull Company company);
}
