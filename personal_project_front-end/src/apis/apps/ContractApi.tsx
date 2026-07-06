import { ContractResponse } from "@/models/response/ContractResponse";
import { ApiBaseUrls } from "@/utils/urlUtils";
import { BaseApi } from "../apiBase";

export class ContractApi {
  static baseUrl = `${ApiBaseUrls.server}/contracts`;

  static getMyContracts(): Promise<ContractResponse[]> {
    return BaseApi.getData<ContractResponse[]>(`${this.baseUrl}/me`);
  }
  static addUserToContract(contractId: number, userIds: number[]) {
    return BaseApi.postData<void>(
      `${this.baseUrl}/${contractId}/users`,
      userIds
    );
  }
  static getById(id: number) {
    return BaseApi.getData<ContractResponse>(`${this.baseUrl}/detail/${id}`);
  }
}
