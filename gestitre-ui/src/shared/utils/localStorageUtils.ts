import {LoginResponseModel} from "@/pages/admin/auth/LoginResponseModel.ts";
import {
    ACCESS_TOKEN,
    EMAIL,
    IS_AUTHENTICATED,
    LAST_VISITED_URL,
    REFRESH_TOKEN,
    TENANT_CODE
} from "../constant/globalConstant";
import {VerifyCodeRequestModel} from "@/pages/admin/auth/VerifyCodeRequestModel.ts";
import {ProfileModel} from "@/pages/admin/user/ProfileModel.ts";
import {LanguageEnum} from "@/pages/admin/user/LanguageEnum.ts";


export const persistAuthenticationInformation = (accessToken: string, refreshToken: string)=> {
    cleanAuthenticationInformation();
    localStorage.setItem(ACCESS_TOKEN, accessToken);
    localStorage.setItem(REFRESH_TOKEN, refreshToken);
    localStorage.setItem(IS_AUTHENTICATED, "true");
}


export const persistMfaInformation = (email: string, tenantCode: string) => {
    localStorage.setItem(EMAIL, email);
    localStorage.setItem(TENANT_CODE, tenantCode);
}

export const cleanMfaInformation = () => {
    if(localStorage.getItem(EMAIL) !== null) {
        localStorage.removeItem(EMAIL);
    }
    if(localStorage.getItem(TENANT_CODE) != null) {
        localStorage.removeItem(TENANT_CODE);
    }
}

export const cleanAuthenticationInformation = () => {
    if(localStorage.getItem(ACCESS_TOKEN) !== null) {
        localStorage.removeItem(ACCESS_TOKEN);
    }
    if(localStorage.getItem(REFRESH_TOKEN) != null) {
        localStorage.removeItem(REFRESH_TOKEN);
    }
    if(localStorage.getItem(IS_AUTHENTICATED) != null) {
        localStorage.removeItem(IS_AUTHENTICATED);
    }

}

export const recoverAuthenticationInformation = (): LoginResponseModel => {
    const accessToken = localStorage.getItem(ACCESS_TOKEN) ?? '';
    const refreshToken = localStorage.getItem(REFRESH_TOKEN) ?? '';
    return {accessToken, refreshToken, isMfa: false};
}

export const recoverMfaInformation = (): VerifyCodeRequestModel => {
    const email = localStorage.getItem(EMAIL) ?? '';
    const tenantCode = localStorage.getItem(TENANT_CODE) ?? '';
    return {email, tenantCode, code: ''};
}

export const persistLastVisitedUrl = (url: string) => {
    localStorage.setItem(LAST_VISITED_URL, url);
}


export const recoverLastVisitedUrl = (): string => {
    return localStorage.getItem(LAST_VISITED_URL) || "/dashboard";
}

export const cleanLastVisitedUrl = () => {
    if(localStorage.getItem(LAST_VISITED_URL)) {
        localStorage.removeItem(LAST_VISITED_URL);
    }
}

const COMPANY_ID = "companyId";
const TENANT_ID = "tenantId";
const LANGUAGE = "language";
const NAME = "name";
export const persistProfileInformation = (profile: ProfileModel)=> {
    localStorage.setItem(COMPANY_ID, profile.companyId);
    localStorage.setItem(TENANT_ID, profile.tenantId);
    localStorage.setItem(LANGUAGE, profile.language);
    localStorage.setItem(NAME, profile.name);
}

export const cleanProfileInformation = () => {
    if(localStorage.getItem(COMPANY_ID)) {
        localStorage.removeItem(COMPANY_ID);
    }
    if(localStorage.getItem(TENANT_ID)) {
        localStorage.removeItem(TENANT_ID);
    }
    if(localStorage.getItem(LANGUAGE)) {
        localStorage.removeItem(LANGUAGE);
    }
    if(localStorage.getItem(NAME)) {
        localStorage.removeItem(NAME);
    }
}

export const recoverProfileInformation = (): ProfileModel => {
    const companyId = localStorage.getItem(COMPANY_ID) ?? '';
    const tenantId = localStorage.getItem(TENANT_ID) ?? '';
    const name = localStorage.getItem(NAME) ?? '';
    const language: LanguageEnum = (localStorage.getItem(LANGUAGE)?? 'FR') as LanguageEnum;
    return {companyId, tenantId, name, language};
}