import { RoomManageAction } from "@/enums/RoomManageAction";

const SERVER_BASE_URL = import.meta.env.VITE_SERVER_API_ENDPOINT || "";
const AUTH_BASE_URL = import.meta.env.VITE_AUTH_API_ENDPOINT || "";
const API_PREFIX = "/api";
const API_VERSION = "/v1";
const API_URL_PREFIX = API_PREFIX + API_VERSION;
// url for api
export const ApiBaseUrls = {
  server: SERVER_BASE_URL + API_URL_PREFIX,
  auth: AUTH_BASE_URL + API_URL_PREFIX,
};

// url for route
export const RouteUrls = {
  // Các route chính
  home: "/",
  login: "/login",
  register: "/register",
  // forgotPassword: "/forgot-password",
  unauthorized: "/unauthorized",
  error: "/error",

  // Chức năng khách hàng (Người thuê trọ)
  userProfile: "/profile", // Quản lý thông tin cá nhân
  qualityMonitoring: "/water-quality", // Giám sát chất lượng nước
  roomInfo: "/room-info", // Xem thông tin nhà trọ
  roomFacilities: "/room-facilities", // Xem tiện nghi trong phòng
  electricityWater: "/electricity-water", // Quản lý điện - nước
  bills: "/bills", // Hóa đơn tiền nhà
  roomCheckout: "/checkout", // Thông báo trả phòng
  contract: "/contract", // Quản lý hợp đồng
  contractDetail: "/contract/:id", // Chi tiết hợp đồng
  utilityIndex: "/utility-index", // Quản lý chỉ số điện nước
  utilityIndexDetail: "/utility-index/:id", // Chi tiết chỉ số điện nước
  invoice: "/invoice", // Quản lý hóa đơn

  // Chức năng admin (Quản lý)
  dashboard: "/admin/dashboard", // Trang chủ Admin
  manageRooms: "/admin/manage-rooms", // Quản lý nhà trọ
  addUserToRooms: "/admin/manage-rooms/:id/users", // Thêm mới user vào trong phòng
  checkoutRequests: "/admin/checkout-requests", // Quản lý yêu cầu checkout
  manageElectricityWater: "/admin/manage-electricity-water", // Quản lý điện - nước
  manageServices: "/admin/manage-services", // Quản lý dịch vụ
  manageBills: "/admin/manage-bills", // Tính tiền, Quản lý hóa đơn
  maintenanceRequests: "/admin/maintenance-requests", // Quản lý bảo trì
  revenueReports: "/admin/revenue/reports", // Báo cáo doanh thu
  waterReports: "/admin/water/reports", // Báo cáo điện nước
  maintenanceRequestReports: "/admin/maintenance-request/reports", // Báo cáo tình trạng bảo trì thiết bị
  manageAccounts: "/admin/manage-accounts", // Quản lý tài khoản admin
  manageUsers: "/admin/manage-users", // Quản lý người dùng
  sensorWaterReports: "/admin/sensor/water/reports", // Xem báo cáo tỷ lệ điện nước
};

export const replaceRoomIdInUrl = (
  url: string,
  roomId: number,
  action: RoomManageAction
): string => {
  const baseUrl = url.replace(":id", roomId.toString());
  return `${baseUrl}?action=${encodeURIComponent(action)}`;
};

export const toQueryParams = <T extends object>(params: T): URLSearchParams => {
  const searchParams = new URLSearchParams();
  Object.entries(params).forEach(([key, value]) => {
    if (
      value !== undefined &&
      value !== null &&
      value !== "" &&
      !(Array.isArray(value) && value.length === 0)
    ) {
      searchParams.append(
        key,
        Array.isArray(value) ? value.join(",") : String(value)
      );
    }
  });
  return searchParams;
};
