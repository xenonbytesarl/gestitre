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