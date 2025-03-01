import {CertificateTemplateModel} from "@/pages/company/certificatetemplate/CertificateTemplateModel.ts";
import {PageModel} from "@/shared/model/pageModel.ts";
import {SuccessResponseModel} from "@/shared/model/successResponseModel.ts";
import {FindParamModel} from "@/shared/model/findParamModel.ts";
import {API_JSON_HEADER} from "@/shared/constant/globalConstant.ts";
import api from "@/core/Api.ts";

const findCertificateTemplates = async (findParam: FindParamModel): Promise<SuccessResponseModel<PageModel<CertificateTemplateModel>>> => {
    return await api.get('/certificate-templates',
        {
            params: {...findParam},
            headers: API_JSON_HEADER
        });
}



const certificateTemplateService = {
    findCertificateTemplates
}

export default certificateTemplateService;