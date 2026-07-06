import { ApiBaseUrls } from "@/utils/urlUtils";
import { BaseApi } from "../apiBase";

export type RevenueStatResponse = {
  year: number;
  month: number;
  quarter: number;
  totalRevenue: number;
};

export class RevenueApi {
  static baseUrl = `${ApiBaseUrls.server}/inventories`;

  static getRevenue() {
    return BaseApi.getData<RevenueStatResponse[]>(`${this.baseUrl}/revenue`);
  }

  static getRevenueByMonth(month: number, year: number) {
    return BaseApi.getData<RevenueStatResponse>(
      `${this.baseUrl}/revenue/month`,
      { month: month, year: year }
    );
  }

  static getRevenueByQuarter(month: number, year: number) {
    return BaseApi.getData<RevenueStatResponse>(
      `${this.baseUrl}/revenue/quarter`,
      { month: month, year: year }
    );
  }

  static getRevenueByYear(year: number) {
    return BaseApi.getData<RevenueStatResponse>(
      `${this.baseUrl}/revenue/year`,
      { year: year }
    );
  }
}
