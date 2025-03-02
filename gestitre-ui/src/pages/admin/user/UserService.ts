import {SearchParamModel} from "@/shared/model/searchParamModel.ts";
import {SuccessResponseModel} from "@/shared/model/successResponseModel.ts";
import {PageModel} from "@/shared/model/pageModel.ts";
import api from "@/core/Api.ts";
import {API_JSON_HEADER} from "@/shared/constant/globalConstant.ts";
import {UserModel} from "@/pages/admin/user/UserModel.ts";
import {RoleModel} from "@/pages/admin/user/RoleModel.ts";


const findUserById = async (userId: string): Promise<SuccessResponseModel<UserModel>> => {
    return await api.get(`/users/${userId}`,
        {
            headers: API_JSON_HEADER
        });
}

const searchUsers = async (searchParam: SearchParamModel): Promise<SuccessResponseModel<PageModel<UserModel>>> => {
    return await api.get('/users/search',
        {
            params: {...searchParam},
            headers: API_JSON_HEADER
        });
}

const searchRoles = async (searchParam: SearchParamModel): Promise<SuccessResponseModel<PageModel<RoleModel>>> => {
    return await api.get('/users/roles/search',
        {
            params: {...searchParam},
            headers: API_JSON_HEADER
        });
}

const createUser = async (user: UserModel): Promise<SuccessResponseModel<UserModel>> => {
    return await api.post('/users', user,
        {
            headers: API_JSON_HEADER
        });
}

const userService = {
    findUserById,
    searchUsers,
    searchRoles,
    createUser
};

export default userService;