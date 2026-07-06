import { LoginRequest } from "@/models/request/LoginRequest";
import { UserResponse } from "@/models/response/UserResponse";
import { ApiBaseUrls } from "@/utils/urlUtils";
import { BaseApi } from "../apiBase";

export class AuthApi {
  static baseAuthUrl = `${ApiBaseUrls.auth}`;
  static baseMainUrl = `${ApiBaseUrls.server}`;
  static login(body: LoginRequest) {
    return BaseApi.postData<string>(`${this.baseAuthUrl}/auth/login`, body);
  }

  static getCurrentUser() {
    return BaseApi.getData<UserResponse>(`${this.baseMainUrl}/users/me`);
  }
  // quên mật khẩu cho admin
  static forgotPassword(email: string) {
    return BaseApi.postData(`${this.baseAuthUrl}/forgot-password`, {
      email,
    });
  }

  static resetPassword(username: string) {
    return BaseApi.getData(`${this.baseMainUrl}/users/reset-password`, {
      username,
    });
  }
}
