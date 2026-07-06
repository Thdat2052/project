import { BaseModel } from "./Basemodel";
export type ContractModel = BaseModel & {
  roomId: number;
  userId: number;
  startDate: string;
  endDate?: string;
  deposit: number;
  monthlyRent: number;
};

export const initialContract: ContractModel = {
  roomId: 0,
  userId: 0,
  startDate: "",
  endDate: "",
  deposit: 0,
  monthlyRent: 0,
};
