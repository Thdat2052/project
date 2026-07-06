import { UtilityIndexResponse } from "@/models/response/UtilityIndexResponse";
import { ApiBaseUrls } from "@/utils/urlUtils";
import { BaseApi } from "../apiBase";

export class UtilityIndexApi {
  static baseUrl = `${ApiBaseUrls.server}/utility-indexes`;

  static findByContractId(contractId: number) {
    return BaseApi.getData<UtilityIndexResponse[]>(
      `${this.baseUrl}/${contractId}/contract`
    );
  }
}
