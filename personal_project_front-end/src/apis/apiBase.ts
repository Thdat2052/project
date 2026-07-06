import { directDispatch } from '@/apps/store';
import { decrement, increment } from '@/features/loading/loadingSlice';
import { Axios, AxiosError, AxiosPromise, AxiosResponse } from 'axios';
import { axiosClient } from './axiosClient';

export type CustomAxiosRequestConfig = {
  skipLoading?: boolean;
  controller?: AbortController;
};

export class BaseApi {
  static request<T = unknown>(
    request: (axios: Axios) => AxiosPromise<T>,
    config: CustomAxiosRequestConfig = {}
  ) {
    const useLoading = config.skipLoading !== true;

    if (useLoading) {
      directDispatch(increment());
    }

    return new Promise<AxiosResponse<T>>((resolve, reject) => {
      return request(axiosClient)
        .then((response) => resolve(response))
        .catch((error: AxiosError) => {
          reject(error);
        });
    }).finally(() => {
      if (useLoading) {
        directDispatch(decrement());
      }
    });
  }

  static async handlerResponseData<T = unknown>(
    response: Promise<AxiosResponse<T, any>>
  ): Promise<T> {
    const result = await response;
    return result.data;
  }

  static get<T = unknown>(
    url: string,
    queryParams?: Record<string, any>,
    config: CustomAxiosRequestConfig = {}
  ) {
    return this.request<T>((axiosInstance) => {
      return axiosInstance.get(url, {
        params: queryParams,
        ...config,
        signal: config.controller?.signal,
      });
    }, config);
  }

  static getData<T = unknown>(
    url: string,
    queryParams?: Record<string, any>,
    config: CustomAxiosRequestConfig = {}
  ) {
    return this.handlerResponseData<T>(this.get<T>(url, queryParams, config));
  }

  static post<T = unknown>(
    url: string,
    body?: unknown,
    queryParams?: Record<string, any>,
    config: CustomAxiosRequestConfig = {}
  ) {
    return this.request<T>((axiosInstance) => {
      return axiosInstance.post(url, body, {
        params: queryParams,
        ...config,
        signal: config.controller?.signal,
      });
    }, config);
  }

  static postData<T = unknown>(
    url: string,
    body?: unknown,
    queryParams?: Record<string, any>,
    config: CustomAxiosRequestConfig = {}
  ) {
    return this.handlerResponseData<T>(
      this.post<T>(url, body, queryParams, config)
    );
  }

  static put<T = unknown>(
    url: string,
    body?: unknown,
    queryParams?: Record<string, any>,
    config: CustomAxiosRequestConfig = {}
  ) {
    return this.request<T>((axiosInstance) => {
      return axiosInstance.put(url, body, {
        params: queryParams,
        ...config,
        signal: config.controller?.signal,
      });
    }, config);
  }

  static putData<T = unknown>(
    url: string,
    body?: unknown,
    queryParams?: Record<string, any>,
    config: CustomAxiosRequestConfig = {}
  ) {
    return this.handlerResponseData<T>(
      this.put<T>(url, body, queryParams, config)
    );
  }

  static patch<T = unknown>(
    url: string,
    body?: unknown,
    queryParams?: Record<string, any>,
    config: CustomAxiosRequestConfig = {}
  ) {
    return this.request<T>((axiosInstance) => {
      return axiosInstance.patch(url, body, {
        params: queryParams,
        ...config,
        signal: config.controller?.signal,
      });
    }, config);
  }

  static patchData<T = unknown>(
    url: string,
    body?: unknown,
    queryParams?: Record<string, any>,
    config: CustomAxiosRequestConfig = {}
  ) {
    return this.handlerResponseData<T>(
      this.patch<T>(url, body, queryParams, config)
    );
  }

  static delete<T = unknown>(
    url: string,
    queryParams?: Record<string, any>,
    config: CustomAxiosRequestConfig = {}
  ) {
    return this.request<T>((axiosInstance) => {
      return axiosInstance.delete(url, {
        params: queryParams,
        ...config,
        signal: config.controller?.signal,
      });
    }, config);
  }

  static deleteData<T = unknown>(
    url: string,
    queryParams?: Record<string, any>,
    config: CustomAxiosRequestConfig = {}
  ) {
    return this.handlerResponseData<T>(
      this.delete<T>(url, queryParams, config)
    );
  }
}
