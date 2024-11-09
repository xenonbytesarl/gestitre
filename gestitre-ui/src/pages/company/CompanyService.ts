import {CompanyModel} from "@/pages/company/CompanyModel.ts";
import {SuccessResponseModel} from "@/shared/model/successResponseModel.ts";
import axios from "axios";
import {API_BASE_URL, API_FORM_DATA_HEADER, API_JSON_HEADER} from "@/shared/constant/globalConstant.ts";

const createCompany = async (company: CompanyModel, logo: File, stamp: File): Promise<SuccessResponseModel<CompanyModel>> => {
    const data = new FormData();
    data.append("logo", logo);
    data.append("stamp", stamp);
    data.append("createCompanyViewRequest", new Blob([JSON.stringify(company)], {type: 'application/json'}));
    return await axios.post(API_BASE_URL + '/companies', data, {headers: API_FORM_DATA_HEADER});
}

const findCompanyById = async (companyId: string): Promise<SuccessResponseModel<CompanyModel>> => {
    return await axios.get(API_BASE_URL + `/catalog/company/${companyId}`,
        {
            headers: API_JSON_HEADER
        });
}

const companyService = {
  createCompany,
    findCompanyById
};

export default companyService;