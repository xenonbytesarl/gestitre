import {VerifyCodeResponseModel} from "@/pages/admin/auth/VerifyCodeResponseModel.ts";

export interface LoginResponseModel extends VerifyCodeResponseModel{
    isMfa: boolean;
}