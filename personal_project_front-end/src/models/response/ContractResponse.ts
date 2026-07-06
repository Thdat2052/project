import { ContractStatusEnum } from "@/enums/ContractStatusEnum";
import { ServiceModel } from "../ServiceModel";
import { UserModel } from "../UserModel";

export type ContractResponse = {
  id?: number;
  deposit: number;
  monthlyRent: number;
  userId: number;
  userName: string;
  roomId: number;
  roomNumber: string;
  startDate: string;
  endDate: string;
  status: ContractStatusEnum;
  members: UserModel[];
  services: ServiceModel[];
};
