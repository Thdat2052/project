import { AxiosInstance, InternalAxiosRequestConfig } from "axios";

export const setupAuthInterceptor = (axiosInstance: AxiosInstance) => {
  axiosInstance.interceptors.request.use(
    (config: InternalAxiosRequestConfig) => {
      const token = localStorage.getItem("token");
      if (token) {
        config.headers.set("Authorization", `Bearer ${token}`);
      }

      return config;
    }
  );
};
