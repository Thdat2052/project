import { ApiBaseUrls } from "@/utils/urlUtils";
import { BaseApi } from "../apiBase";
import { UserResponse } from "@/models/response/UserResponse";
import { PageResponse } from "@/models/response/PageResponse";

export class UserApi {
  static baseUrl = `${ApiBaseUrls.server}/users`;

  static getMe() {
    return BaseApi.getData<UserResponse>(`${this.baseUrl}/me`);
  }

  static getAll(params: Record<string, any>) {
    return BaseApi.getData<PageResponse<UserResponse>>(
      `${this.baseUrl}/paging`,
      params
    );
  }
  static save(data: UserResponse) {
    return BaseApi.postData<UserResponse>(`${this.baseUrl}`, data);
  }
  static delete(id: string) {
    return BaseApi.deleteData(`${this.baseUrl}/${id}`);
  }
  
  static updateMe(data: UserResponse) {
    return BaseApi.putData<UserResponse>(`${this.baseUrl}/me`, data);
  }
}
