import { InvoiceStatusEnum } from "@/enums/InvoiceStatusEnum";
import { RoomStatusEnum } from "@/enums/RoomStatusEnum";
import { RoomConditionRequest } from "@/models/request/RoomConditionRequest";
import { RoomModel } from "@/models/RoomModel";
import { ApiBaseUrls } from "@/utils/urlUtils";
import { BaseApi } from "../apiBase";

export type UnpaidCustomerResponse = {
  invoiceId: number;
  roomId: number;
  roomName: string;
  roomNumber: string;
  userId: number;
  username: string;
  status: string;
  totalPrice: number;
  paymentDate: Date;
};

export type RoomContractResponse = {
  roomId: number;
  roomName: string;
  roomNumber: string;
  userId: number;
  username: string;
  endDate: Date;
};

export class RoomApi {
  static baseUrl = `${ApiBaseUrls.server}/rooms`;

  static deleteByIdIn(ids: number[]) {
    return BaseApi.deleteData<any>(`${this.baseUrl}`, { ids: ids.join(",") });
  }

  static createOrUpdate(body: RoomModel) {
    return BaseApi.postData<RoomModel>(`${this.baseUrl}`, body);
  }

  static findAllWithPaging(body: RoomConditionRequest) {
    return BaseApi.getData<any>(`${this.baseUrl}/paging`, body);
  }

  static findAllByStatus(status: RoomStatusEnum) {
    return BaseApi.getData<RoomModel[]>(
      `${this.baseUrl}/status?status=${status}`
    );
  }

  static getById(id: number) {
    return BaseApi.getData<RoomModel>(`${this.baseUrl}/` + id);
  }

  static getUnpaidCustomers(status: InvoiceStatusEnum) {
    return BaseApi.getData<UnpaidCustomerResponse[]>(
      `${this.baseUrl}/users/unpaid`,
      { status: status }
    );
  }

  static getActiveContractsInNextDays() {
    return BaseApi.getData<RoomContractResponse[]>(
      `${this.baseUrl}/active-next-days`,
      {
        days: 7,
      }
    );
  }
}
