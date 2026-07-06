import { InvoiceServiceInfoDetailModel } from "@/models/InvoiceServiceInfoDetailModel";
import { InvoiceInformationDetailResponse } from "@/models/response/InvoiceInformationDetailResponse";
import { ApiBaseUrls } from "@/utils/urlUtils";
import { BaseApi } from "../apiBase";

export class InvoiceApi {
  static baseUrl = `${ApiBaseUrls.server}/invoices`;

  static getMe() {
    return BaseApi.getData<InvoiceInformationDetailResponse[]>(
      `${this.baseUrl}/me`
    );
  }

  static getInvoiceInfoByTime(month: number, year: number) {
    const paramStrings = `?month=${month}&year=${year}`;
    return BaseApi.getData<InvoiceServiceInfoDetailModel[]>(
      `${this.baseUrl}/all-by-time` + paramStrings
    );
  }

  static createOrUpdate(roomId: number, month: number, year: number) {
    const paramStrings = `?roomId=${roomId}&month=${month}&year=${year}`;
    return BaseApi.getData<InvoiceServiceInfoDetailModel[]>(
      `${this.baseUrl}/calculate` + paramStrings
    );
  }

  static deleteById(id: number) {
    return BaseApi.deleteData(`${this.baseUrl}/${id}`);
  }

  static confirmPayment(id: number) {
    return BaseApi.putData(`${this.baseUrl}/${id}/confirm-payment`, {});
  }
}
