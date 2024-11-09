import {CompanyModel} from "@/pages/company/CompanyModel.ts";
import {SuccessResponseModel} from "@/shared/model/successResponseModel.ts";
import axios from "axios";
import {API_BASE_URL, API_FORM_DATA_HEADER, API_JSON_HEADER} from "@/shared/constant/globalConstant.ts";
import {FindParamModel} from "@/shared/model/findParamModel.ts";
import {SearchParamModel} from "@/shared/model/searchParamModel.ts";
import {PageModel} from "@/shared/model/pageModel.ts";

const createCompany = async (company: CompanyModel, logo: File, stamp: File): Promise<SuccessResponseModel<CompanyModel>> => {
    const data = new FormData();
    data.append("logo", logo);
    data.append("stamp", stamp);
    data.append("createCompanyViewRequest", new Blob([JSON.stringify(company)], {type: 'application/json'}));
    return await axios.post(API_BASE_URL + '/companies', data, {headers: API_FORM_DATA_HEADER});
}

const findCompanyById = async (companyId: string): Promise<SuccessResponseModel<CompanyModel>> => {
    return await axios.get(API_BASE_URL + `/companies/${companyId}`,
        {
            headers: API_JSON_HEADER
        });
}

const findCompanies = async (findParam: FindParamModel): Promise<SuccessResponseModel<PageModel<CompanyModel>>> => {
    return await axios.get(API_BASE_URL + '/companies',
        {
            params: {...findParam},
            headers: API_JSON_HEADER
        });
}

const searchCompanies = async (searchParam: SearchParamModel): Promise<SuccessResponseModel<PageModel<CompanyModel>>> => {
    return await axios.get(API_BASE_URL + '/companies/search',
        {
            params: {...searchParam},
            headers: API_JSON_HEADER
        });
}

const companyService = {
    createCompany,
    findCompanyById,
    findCompanies,
    searchCompanies
};

export default companyService;