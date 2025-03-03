import {SuccessorModel} from "@/pages/shareholder/SuccessorModel.ts";
import {ShareHolderTypeEnum} from "@/pages/shareholder/ShareHolderTypeEnum.ts";
import {AccountTypeEnum} from "@/pages/shareholder/AccountTypeEnum.ts";
import {RepresentativeModel} from "@/pages/shareholder/RepresentativeModel.ts";

export interface ShareHolderModel {
    id: string;
    tenantId: string;
    name: string;
    accountNumber: string;
    accountType: AccountTypeEnum;
    taxResidence: string;
    initialBalance?: number;
    bankAccountNumber?: string;
    administrator?: string;
    email?: string;
    phone?: string;
    city?: string;
    zipCode?: string;
    shareHolderType?: ShareHolderTypeEnum;
    representative?: RepresentativeModel;
    successor?: SuccessorModel;
    createdDate: Date;
    active: boolean;


}