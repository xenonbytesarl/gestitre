import {LicenceEnum} from "@/pages/company/LicenceEnum.ts";
import {LegalFormEnum} from "@/pages/company/LegalFormEnum.ts";
import {AddressModel} from "@/pages/company/AddressModel.ts";
import {ContactModel} from "@/pages/company/ContactModel.ts";

export interface CompanyModel {
    id: string;
    companyName: string;
    companyManagerName: string;
    licence: LicenceEnum | "";
    legalForm: LegalFormEnum | "";
    address: AddressModel;
    contact: ContactModel;
    activity: string;
    registrationNumber: string;
    certificateTemplateId: string;
    webSiteUrl: string;
    isinCode: string;
    taxCode: string;
    logoFilename: string;
    encodedLogo: string;
    mimeLogo: string;
    stampFilename: string;
    encodedStamp: string;
    mimeStamp: string;
    grossDividendStockUnit: number;
    nominalValue: number;
    netDividendStock?: number;
    capitalization?: number;
    stockQuantity?: number;
    ircmRetain?: number;
    active?: boolean;
}