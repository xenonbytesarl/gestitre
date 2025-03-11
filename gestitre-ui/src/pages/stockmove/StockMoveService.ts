import {SuccessResponseModel} from "@/shared/model/successResponseModel.ts";
import api from "@/core/Api.ts";
import {API_FORM_DATA_HEADER, API_JSON_HEADER} from "@/shared/constant/globalConstant.ts";
import {StockMoveModel} from "@/pages/stockmove/StockMoveModel.ts";
import {SearchParamModel} from "@/shared/model/searchParamModel.ts";
import {PageModel} from "@/shared/model/pageModel.ts";


const searchStockMoves = async (searchParam: SearchParamModel): Promise<SuccessResponseModel<PageModel<StockMoveModel>>> => {
    return await api.get('/stock-moves',
        {
            params: {...searchParam},
            headers: API_JSON_HEADER
        });
}

const createStockMove = async (stockMove: StockMoveModel, file: File): Promise<SuccessResponseModel<StockMoveModel>> => {
    const data = new FormData();
    data.append("file", file);
    data.append("createStockMoveViewRequest", new Blob([JSON.stringify(stockMove)], {type: 'application/json'}));
    return await api.post('/stock-moves', data, {headers: API_FORM_DATA_HEADER});
}

const stockMoveService = {
    createStockMove,
    searchStockMoves
};

export default stockMoveService;