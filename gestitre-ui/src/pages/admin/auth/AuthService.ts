import {LoginRequestModel} from "@/pages/admin/auth/LoginRequestModel.ts";
import {SuccessResponseModel} from "@/shared/model/successResponseModel.ts";
import axios from "axios";
import {API_BASE_URL, API_JSON_WITH_TIME_ZONE_HEADER} from "@/shared/constant/globalConstant.ts";
import {VerifyCodeRequestModel} from "@/pages/admin/auth/VerifyCodeRequestModel.ts";

const login = async (loginRequest: LoginRequestModel): Promise<SuccessResponseModel<LoginRequestModel>> => {
    return await axios.post(API_BASE_URL +  '/users/auth/token', loginRequest, {
        headers: {...API_JSON_WITH_TIME_ZONE_HEADER, 'X-Gestitre-Tenant-Code': loginRequest.tenantCode}
    });
}

const verifyCode = async (verifyCodeRequest: VerifyCodeRequestModel): Promise<SuccessResponseModel<VerifyCodeRequestModel>> => {
    return await axios.post(API_BASE_URL +  '/users/auth/verify-code', verifyCodeRequest, {
        headers: {...API_JSON_WITH_TIME_ZONE_HEADER, 'X-Gestitre-Tenant-Code': verifyCodeRequest.tenantCode}
    });
}

const authServices = {
    login,
    verifyCode
};

export default authServices;