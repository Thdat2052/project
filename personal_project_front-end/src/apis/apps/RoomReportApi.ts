import { ApiBaseUrls } from "@/utils/urlUtils";
import { BaseApi } from "../apiBase";

export type RoomUtilityUsageReportModel = {
  roomId: number;
  roomName: string;
  totalWaterUsage: number;
  totalElectricUsage: number;
  monthInventory: number;
  yearInventory: number;
};

export class RoomReportApi {
  static baseUrl = `${ApiBaseUrls.server}/reports`;
  static getRoomUtilityUsageReport(startDate: Date, endDate: Date) {
    return BaseApi.getData<RoomUtilityUsageReportModel[]>(
      `${this.baseUrl}/room/utility`,
      { startDate, endDate }
    );
  }
}
