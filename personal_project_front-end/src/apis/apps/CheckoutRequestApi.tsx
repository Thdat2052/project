import { ApiBaseUrls } from "@/utils/urlUtils";
import { BaseApi } from "../apiBase";
import { CheckoutRequestRequest } from "@/models/request/CheckoutRequestRequest";
import { PageResponse } from "@/models/response/PageResponse";
import { CheckoutRequestResponse } from "@/models/response/CheckoutRequestResponse";
import { CheckoutRequestStatusEnum } from "@/enums/CheckoutRequestEnum";

export class CheckoutRequestApi {
  static baseUrl = `${ApiBaseUrls.server}/checkout-requests`;

  static create(data: CheckoutRequestRequest) {
    return BaseApi.postData<CheckoutRequestRequest>(
      `${this.baseUrl}/create`,
      data
    );
  }
  static findAll(paging: Record<string, any>) {
    return BaseApi.getData<PageResponse<CheckoutRequestResponse>>(
      `${this.baseUrl}/paging`,
      paging
    );
  }
  static approve(id: number, status: CheckoutRequestStatusEnum) {
    return BaseApi.putData<void>(`${this.baseUrl}/${id}?status=${status}`);
  }
}
