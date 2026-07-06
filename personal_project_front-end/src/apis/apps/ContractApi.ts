import { ContractInformationRequest } from "@/models/request/ContractInformationRequest";
import { ConstractResponse } from "@/models/response/ConstractResponse";
import { ApiBaseUrls } from "@/utils/urlUtils";
import { BaseApi } from "../apiBase";
import { ContractResponse } from "@/models/response/ContractResponse";

export class ContractApi {
  static baseUrl = `${ApiBaseUrls.server}/contracts`;

  static createContract(body: ContractInformationRequest) {
    return BaseApi.postData<any>(`${this.baseUrl}`, body);
  }

  static getContractInfo(roomId: number) {
    return BaseApi.getData<ConstractResponse>(
      `${this.baseUrl}/detail/rooms/` + roomId
    );
  }
  static getMyContracts() {
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
