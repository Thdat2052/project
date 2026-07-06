import { ApiBaseUrls } from "@/utils/urlUtils";
import { BaseApi } from "../apiBase";

export type MaintenanceStatusReportModel = {
  roomId: number;
  roomName: string;
  maintenanceId: number;
  maintenanceStatus: string;
  requestDate: string;
  requestDoneDate: string;
  totalFee: number;
};

export class MaintenanceReportApi {
  static baseUrl = `${ApiBaseUrls.server}/reports`;
  static getRoomUtilityUsageReport(startDate: Date, endDate: Date) {
    return BaseApi.getData<MaintenanceStatusReportModel[]>(
      `${this.baseUrl}/maintenance`,
      { startDate, endDate }
    );
  }
}
