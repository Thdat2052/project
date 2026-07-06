import { InvoiceStatusEnum } from "@/enums/InvoiceStatusEnum";

export type ServiceInfoModel = {
  serviceId: number;
  nameService: string;
  serviceType: string;
  priceService: number;
  quantityService: number;
};

export type InvoiceServiceInfoDetailModel = {
  invoiceId: number;
  invoiceCreated: string;
  monthCal: number;
  yearCal: number;
  totalInvoice: number;
  fullName: string;
  roomId: number;
  status: InvoiceStatusEnum;
  serviceInfos: ServiceInfoModel[];
  roomPrice: number;
};

export const defaultInvoiceServiceInfoDetailModel: InvoiceServiceInfoDetailModel =
  {
    invoiceId: 0,
    invoiceCreated: new Date().toISOString(),
    monthCal: new Date().getMonth() + 1,
    yearCal: new Date().getFullYear(),
    totalInvoice: 0,
    fullName: "",
    roomId: 0,
    status: InvoiceStatusEnum.UNPAID,
    serviceInfos: [],
    roomPrice: 0,
  };
