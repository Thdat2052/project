import { ApiBaseUrls } from "@/utils/urlUtils";
import { BaseApi } from "../apiBase";
import { MaintenanceRequestRequest } from "@/models/request/MaintenanceRequestRequest";
import { MaintenanceRequestResponse } from "@/models/response/MaintenanceRequestResponse";
import { PageResponse } from "@/models/response/PageResponse";

export class MaintenanceRequestApi {
  static baseUrl = `${ApiBaseUrls.server}/maintenance-requests`;

  static save(data: MaintenanceRequestRequest) {
    return BaseApi.postData<MaintenanceRequestResponse>(
      `${this.baseUrl}`,
      data
    );
  }

  static findAll(paging: Record<string, any>) {
    return BaseApi.getData<PageResponse<MaintenanceRequestResponse>>(
      `${this.baseUrl}/paging`,
      paging
    );
  }

  static update(data: MaintenanceRequestRequest) {
    return BaseApi.putData<MaintenanceRequestResponse>(
      `${this.baseUrl}/${data.id}`,
      data
    );
  }
}
