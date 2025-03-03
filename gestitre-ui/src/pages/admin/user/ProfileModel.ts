import {LanguageEnum} from "@/pages/admin/user/LanguageEnum.ts";

export interface ProfileModel {
    companyId: string;
    tenantId: string;
    name: string;
    language: LanguageEnum;
}