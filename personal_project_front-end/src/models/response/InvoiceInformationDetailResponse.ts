import { InvoiceStatusEnum } from "@/enums/InvoiceStatusEnum";
import { ServiceTypeEnum } from "@/enums/ServiceTypeEnum";


export type ServiceInRoomModel = {
  maintenanceRequestId: number | null;
  serviceId: number;
  serviceName: string;
  price: number;
  quantity: number;
  totalMoney: number;
};

export type InvoiceInformationModel = ServiceInRoomModel & {
  roomId: number;
  roomName: string;

  contractId: number;
  monthlyRent: number;

  userId: number;
  username: string;

  serviceRoomId?: number;
  serviceType?: ServiceTypeEnum;
}
export type InvoiceModel = {
  roomId?: number;
  contractId: number;
  invoiceId: number;
  userId?: number;
  invoiceStatus?: InvoiceStatusEnum;
  invoiceCreated: string;
};

export type InvoiceInformationDetailResponse = {
  detail: InvoiceModel;
  invoiceInformation: InvoiceInformationModel;
  serviceOfRooms: ServiceInRoomModel[];
  serviceMaintenanceOfRooms: ServiceInRoomModel[];
  totalMoney: number;
};
