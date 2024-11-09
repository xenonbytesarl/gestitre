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
    taxNumber: string;
    logoFilename: string;
    logoEncoded: string;
    logoMimeType: string;
    stampFilename: string;
    stampEncoded: string;
    stampMimeType: string;
    grossDividendStockUnit: number;
    nominalValue: number;
    netDividendStock?: number;
    capitalization?: number;
    stockQuantity?: number;
    ircmRetain?: number;
    endLicenceDate?: Date;
    createdDate?: Date;
    active?: boolean;
}