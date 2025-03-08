import {RoleModel} from "@/pages/admin/user/RoleModel.ts";
import {TimezoneEnum} from "@/pages/admin/user/TimezoneEnum.ts";
import {LanguageEnum} from "@/pages/admin/user/LanguageEnum.ts";

export interface UserModel {
    id: string;
    name: string;
    email: string;
    password: string;
    confirmPassword: string;
    roles: Array<RoleModel>;
    companyId: string;
    tenantId?: string;
    timezone: TimezoneEnum;
    useMfa: boolean;
    accountEnabled?: boolean;
    accountExpired?: boolean;
    accountLocked?: boolean;
    credentialExpired?: boolean;
    failedLoginAttempt?: number;
    language: LanguageEnum;

}