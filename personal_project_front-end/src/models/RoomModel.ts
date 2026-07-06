import { RoomStatusEnum } from "@/enums/RoomStatusEnum";
import { BaseModel } from "./Basemodel";

export type RoomModel = BaseModel & {
  id?: number;
  price: number;
  number: string;
  length?: number;
  width?: number;
  status: RoomStatusEnum;
  note?: string;
};

export const defaultRoomModel: RoomModel = {
  price: 0,
  number: "",
  length: 1,
  width: 1,
  status: RoomStatusEnum.AVAILABLE,
  note: "",
};
