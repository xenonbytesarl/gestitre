export interface LoginResponseModel {
    accessToken: string;
    refreshToken: null;
    isMfa: boolean;
    code: boolean;
}