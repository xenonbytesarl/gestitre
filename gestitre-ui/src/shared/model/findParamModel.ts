import {Direction} from "@/shared/constant/directionConstant.ts";

export interface FindParamModel {
  page: number;
  size: number;
  field: string;
  direction: Direction
}
