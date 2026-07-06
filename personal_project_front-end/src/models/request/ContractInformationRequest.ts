import { ContractModel } from "../ContractModel";
import { ServiceModel } from "../ServiceModel";
import { UserModel } from "../UserModel";

export type ContractInformationRequest = {
  memberOfRooms: UserModel[];
  roomId: number;
  serviceOfRooms: ServiceModel[];
  contract: ContractModel;
};
