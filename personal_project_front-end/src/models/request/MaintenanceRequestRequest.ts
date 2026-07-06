import { MaintenanceRequestRequestStatusEnum } from "@/enums/MaintenanceRequestRequestStatusEnum";

export type MaintenanceRequestRequest = {
  id?: number;
  serviceRoomId?: number;
  contractId?: number;
  decision?: string;
  status?: MaintenanceRequestRequestStatusEnum;
  requestDate?: Date;
  requestDoneDate?: Date;
  price?: number;
  roomName?: string;
};
