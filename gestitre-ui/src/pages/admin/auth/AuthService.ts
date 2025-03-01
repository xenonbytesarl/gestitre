import {LoginRequestModel} from "@/pages/admin/auth/LoginRequestModel.ts";
import {SuccessResponseModel} from "@/shared/model/successResponseModel.ts";
import {API_JSON_HEADER, API_JSON_WITH_TIME_ZONE_HEADER} from "@/shared/constant/globalConstant.ts";
import {VerifyCodeRequestModel} from "@/pages/admin/auth/VerifyCodeRequestModel.ts";
import {LoginResponseModel} from "@/pages/admin/auth/LoginResponseModel.ts";
import {VerifyCodeResponseModel} from "@/pages/admin/auth/VerifyCodeResponseModel.ts";
import api from "@/core/Api.ts";


const login = async (loginRequest: LoginRequestModel): Promise<SuccessResponseModel<LoginResponseModel>> => {
    return await api.post('/users/auth/token', loginRequest, {
        headers: {...API_JSON_WITH_TIME_ZONE_HEADER, 'X-Gestitre-Tenant-Code': loginRequest.tenantCode}
    });
}

const verifyCode = async (verifyCodeRequest: VerifyCodeRequestModel): Promise<SuccessResponseModel<VerifyCodeResponseModel>> => {
    return await api.post('/users/auth/verify-code', verifyCodeRequest, {
        headers: {...API_JSON_WITH_TIME_ZONE_HEADER, 'X-Gestitre-Tenant-Code': verifyCodeRequest.tenantCode}
    });
}

const refreshToken = async(): Promise<SuccessResponseModel<VerifyCodeResponseModel>> => {

    return await api.get('/users/auth/refresh-token', {
        headers: API_JSON_HEADER
    });
}
const authServices = {
    login,
    verifyCode,
    refreshToken
};

export default authServices;