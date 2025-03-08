import {SearchParamModel} from "@/shared/model/searchParamModel.ts";
import {SuccessResponseModel} from "@/shared/model/successResponseModel.ts";
import {PageModel} from "@/shared/model/pageModel.ts";
import api from "@/core/Api.ts";
import {API_JSON_HEADER} from "@/shared/constant/globalConstant.ts";
import {ShareHolderModel} from "@/pages/shareholder/ShareHolderModel.ts";

const findShareHolderById = async (shareholderId: string): Promise<SuccessResponseModel<ShareHolderModel>> => {
    return await api.get(`/shareholders/${shareholderId}`,
        {
            headers: API_JSON_HEADER
        });
}

const searchShareHolders = async (searchParam: SearchParamModel): Promise<SuccessResponseModel<PageModel<ShareHolderModel>>> => {
    return await api.get('/shareholders',
        {
            params: {...searchParam},
            headers: API_JSON_HEADER
        });
}

const createShareHolder = async (shareholder: ShareHolderModel): Promise<SuccessResponseModel<ShareHolderModel>> => {
    return await api.post('/shareholders', shareholder,
        {
            headers: API_JSON_HEADER
        });
}

const updateShareHolder = async (shareholder: ShareHolderModel): Promise<SuccessResponseModel<ShareHolderModel>> => {
    return await api.put(`/shareholders/${shareholder.id}`, shareholder,
        {
            headers: API_JSON_HEADER
        });
}

const shareHolderService = {
    findShareHolderById,
    searchShareHolders,
    createShareHolder,
    updateShareHolder
};

export default shareHolderService;