import { UtilityIndexModel } from "@/models/UtilityIndexModel";
import { ApiBaseUrls } from "@/utils/urlUtils";
import { BaseApi } from "../apiBase";

export class UtilityWaterApi {
  static baseUrl = `${ApiBaseUrls.server}/utility-indexes`;

  static createOrUpdate(body: UtilityIndexModel) {
    return BaseApi.postData<UtilityIndexModel>(`${this.baseUrl}/save`, body);
  }
  static findAllWithRoomIsRented() {
    return BaseApi.getData<UtilityIndexModel[]>(
      `${this.baseUrl}/all/room/rented`
    );
  }
}
