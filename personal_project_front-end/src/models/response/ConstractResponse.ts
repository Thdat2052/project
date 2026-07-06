import { ContractStatusEnum } from "@/enums/ContractStatusEnum";
import { ContractModel } from "../ContractModel";
import { ServiceModel } from "../ServiceModel";
import { UserModel } from "../UserModel";

export type ConstractResponse = ContractModel & {
  roomName: string;
  roomNumber: string;
  fullName: string;
  status?: ContractStatusEnum;
  services: ServiceModel[];
  members: UserModel[];
};
