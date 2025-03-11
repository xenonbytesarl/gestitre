import {StockMoveLineModel} from "@/pages/stockmove/StockMoveLineModel.ts";
import {StockTypeNatureEnum} from "@/pages/stockmove/StockTypeNatureEnum.ts";
import {StockMoveTypeEnum} from "@/pages/stockmove/StockMoveTypeEnum.ts";
import {StockMoveStateEnum} from "@/pages/stockmove/StockMoveStateEnum.ts";

export interface StockMoveModel {
    id: string;
    tenantId?: string;
    reference?: string;
    companyName: string;
    isinCode: string;
    nature: StockTypeNatureEnum;
    type: StockMoveTypeEnum;
    state: StockMoveStateEnum;
    quantityCredit: number;
    quantityDebit: number;
    filename: string;
    companyId: string;
    observation: string;
    createdDate: Date | undefined;
    accountingDate?: Date | undefined;
    fileEncoded?: string;
    fileMimeType?: string;
    moveLines: Array<StockMoveLineModel>;
}