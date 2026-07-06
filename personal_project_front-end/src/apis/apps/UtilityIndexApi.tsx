import { PagingQueryConditionRequest } from "@/models/request/PagingQueryConditionRequest";
import { PageResponse } from "@/models/response/PageResponse";
import { UtilityIndexResponse } from "@/models/response/UtilityIndexResponse";
import { UtilityIndexModel } from "@/models/UtilityIndexModel";
import { ApiBaseUrls } from "@/utils/urlUtils";
import { BaseApi } from "../apiBase";

export class UtilityIndexApi {
  static baseUrl = `${ApiBaseUrls.server}/utility-indexes`;

  static findByContractId(contractId: number) {
    return BaseApi.getData<UtilityIndexResponse[]>(
      `${this.baseUrl}/${contractId}/contract`
    );
  }

  static findAllWithPaging(params: PagingQueryConditionRequest) {
    return BaseApi.getData<PageResponse<UtilityIndexResponse>>(
      `${this.baseUrl}/paging`,
      params
    );
  }

  static createOrUpdate(body: UtilityIndexModel) {
    return BaseApi.postData<UtilityIndexModel>(`${this.baseUrl}`, body);
  }

  static deleteById(id: number) {
    return BaseApi.deleteData(`${this.baseUrl}/delete/${id}`);
  }
}
