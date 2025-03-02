import {Role} from "@/pages/admin/user/Role.ts";
import {TimezoneEnum} from "@/pages/admin/user/TimezoneEnum.ts";

export interface UserModel {
    id: string;
    name: string;
    email: string;
    password: string;
    confirmPassword: string;
    roles: Array<Role>;
    companyId: string;
    tenantId?: string;
    timezone: TimezoneEnum;
    useMfa: boolean;
    accountEnabled?: boolean;
    accountExpired?: boolean;
    accountLocked?: boolean;
    credentialExpired?: boolean;
    failedLoginAttempt?: number;

}