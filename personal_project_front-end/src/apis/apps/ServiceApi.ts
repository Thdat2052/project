import { PagingQueryConditionRequest } from "@/models/request/PagingQueryConditionRequest";
import { ApiBaseUrls } from "@/utils/urlUtils";
import { BaseApi } from "../apiBase";
import { ServiceModel } from "@/models/ServiceModel";

export class ServiceApi {
  static baseUrl = `${ApiBaseUrls.server}/services`;

  static deleteByIdIn(ids: number[]) {
    return BaseApi.deleteData<any>(`${this.baseUrl}`, { ids: ids.join(",") });
  }

  static deleteById(id: number) {
    return BaseApi.deleteData<any>(`${this.baseUrl}/${id}`);
  }
  static createOrUpdate(body: ServiceModel) {
    return BaseApi.postData<ServiceModel>(`${this.baseUrl}`, body);
  }

  static findAllWithPaging(body: PagingQueryConditionRequest) {
    return BaseApi.getData<any>(`${this.baseUrl}/page`, body);
  }
}
