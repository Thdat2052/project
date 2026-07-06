import { RoomStatusEnum } from "@/enums/RoomStatusEnum";
import {
  defaultPagingQueryConditionRequest,
  PagingQueryConditionRequest,
} from "./PagingQueryConditionRequest";

export type RoomConditionRequest = PagingQueryConditionRequest & {
  status?: RoomStatusEnum;
};

export const defaultRoomConditionRequest: RoomConditionRequest = {
  ...defaultPagingQueryConditionRequest,
};
