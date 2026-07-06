import { MaintenanceRequestRequestStatusEnum } from "@/enums/MaintenanceRequestRequestStatusEnum";

export type MaintenanceRequestResponse = {
  id?: number;
  serviceRoomId: number;
  contractId: number;
  decision: string;
  status: MaintenanceRequestRequestStatusEnum;
  requestDate: Date;
  requestDoneDate?: Date;
  totalFee?: number;
  note?: string;
  roomNumber?: string;
};
