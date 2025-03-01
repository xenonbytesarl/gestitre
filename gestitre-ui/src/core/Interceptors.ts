import axiosInstance from "./Api.ts";
import {AUTHORIZATION, BEARER} from "@/shared/constant/globalConstant.ts";
import {logout, persistAuthentication, refreshToken} from "@/pages/admin/auth/AuthSlice.ts";

const Interceptor = (store: any) => {

    axiosInstance.interceptors.request.use(
        (config) => {
            const loginResponse = store.getState().admin.auth.loginResponse;
            // @ts-ignore
            if (!config.url.includes('refresh-token') && loginResponse.accessToken && !config.headers[AUTHORIZATION]) {
                config.headers[AUTHORIZATION] = `${BEARER} ${loginResponse?.accessToken}`;
            }
            // @ts-ignore
            if (config.url.includes('refresh-token') && loginResponse.refreshToken && !config.headers[AUTHORIZATION]) {
                config.headers[AUTHORIZATION] = `${BEARER} ${loginResponse?.refreshToken}`;
            }
            return config;
        },
        (error) => {
            return Promise.reject(error);
        }
    );

    axiosInstance.interceptors.response.use(
        (res) => {
            return res;
        },
        async (err) => {
            const originalConfig = err.config;


                // Access Token was expired in case of 401 and not public url, and it's not a refresh token
                if (err.response.status === 401 && originalConfig.headers[AUTHORIZATION] && !originalConfig.url.includes('refresh-token') && !originalConfig._retry) {
                    originalConfig._retry = true;

                    try {
                        await store.dispatch(refreshToken());
                        const loginResponse = store.getState().admin.auth.loginResponse;
                        await store.dispatch(persistAuthentication({accessToken: loginResponse.accessToken, refreshToken: loginResponse.refreshToken}));
                        originalConfig.headers[AUTHORIZATION] = `${BEARER} ${loginResponse?.accessToken}`;
                        return axiosInstance(originalConfig);
                    } catch (_error) {
                        return Promise.reject(_error);
                    }
                }
            //Refreshing access token return 401. refresh token is expired
            if (err.response.status === 401 && originalConfig.headers[AUTHORIZATION] && originalConfig.url.includes('refresh-token') && !originalConfig._retry) {
                console.log(err);
                await store.dispatch(logout());
            }


            return Promise.reject(err);
        }
    );
};

export default Interceptor;