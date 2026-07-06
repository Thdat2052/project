import { ServiceRoomStatusEnum } from "@/enums/ServiceRoomStatusEnum";
import { ServiceTypeEnum } from "@/enums/ServiceTypeEnum";

export type ServiceModel = {
  id?: number;
  name: string;
  price: number;
  quantity?: number;
  isActive?: boolean;
  status?: ServiceRoomStatusEnum;
  serviceType?: ServiceTypeEnum;
};

export const defaultServiceModel: ServiceModel = {
  name: "",
  price: 0,
  quantity: 1,
  isActive: true,
  status: ServiceRoomStatusEnum.ACTIVE,
  serviceType: ServiceTypeEnum.OTHER,
};
