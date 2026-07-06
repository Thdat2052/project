import axios from "axios";
import { setupAuthInterceptor } from "./interceptors/authInterceptor";
import { setupResponseInterceptor } from "./interceptors/responseInterceptor";

export const axiosClient = axios.create({
  baseURL: process.env.REACT_APP_API_URL,
  timeout: 10000,
});

setupAuthInterceptor(axiosClient);
setupResponseInterceptor(axiosClient);
