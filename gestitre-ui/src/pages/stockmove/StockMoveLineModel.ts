import {StockMoveLineTypeEnum} from "@/pages/stockmove/StockMoveLineTypeEnum.ts";
import {StockMoveLineStateEnum} from "@/pages/stockmove/StockMoveLineStateEnum.ts";

export interface StockMoveLineModel {
    id?: string;
    tenantId?: string;
    reference?: string;
    name: string;
    type: StockMoveLineTypeEnum;
    state: StockMoveLineStateEnum;
    quantity: number;
    createdDate: Date;
    city: string;
    zipCode: string;
    administrator: string;
    shareHolderId: string;
    stockMoveId?: string;
}