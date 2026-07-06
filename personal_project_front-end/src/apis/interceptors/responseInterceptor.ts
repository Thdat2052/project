import { notify } from "@/utils/notification";
import { AxiosError, AxiosInstance } from "axios";
export const setupResponseInterceptor = (axiosInstance: AxiosInstance) => {
  axiosInstance.interceptors.response.use(
    (response) => response,
    (error: AxiosError) => {
      const status = error.response?.status;
      const responseData = error.response?.data as {
        statusCode?: number;
        message?: string;
      };
      if (responseData?.statusCode === 500) {
        notify.error(responseData.message || "Internal Server Error");
      } else {
        notify.error("An error occurred");
      }
      if (status === 401) {
        localStorage.removeItem("token");
        window.location.href = "/login";
      }

      return Promise.reject(error);
    }
  );
};
