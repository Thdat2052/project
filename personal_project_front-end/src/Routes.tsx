import React from "react";
import { Route } from "react-router-dom";
import RequireRole from "./components/auth/RequireAuth";
import { RoleEnum } from "./enums/RoleEnum";
import Home from "./screens/admin/dashboard/Home";
import InvoiceManagementScreen from "./screens/admin/entity-management/invoice/InvoiceManagementScreen";
import MaintenanceRequestScreen from "./screens/admin/entity-management/maintenanceRequest";
import RoomUserManagementScreen from "./screens/admin/entity-management/room-user/RoomUserManagementScreen";
import RoomManagementScreen from "./screens/admin/entity-management/RoomManagementScreen";
import ServiceManagementScreen from "./screens/admin/entity-management/ServiceManagementScreen";
import UtilityWaterScreen from "./screens/admin/entity-management/UtilityWaterScreen";
import MaintenanceReportPage from "./screens/admin/reports/MaintenanceReportPage";
import RevenueStatResponsePage from "./screens/admin/reports/RevenueStatResponsePage";
import RoomReportPage from "./screens/admin/reports/RoomReportPage";
import WaterMonitor from "./screens/admin/sensor/WaterMonitor";
import UserAdminScreen from "./screens/admin/user/UserAdminScreen";
import ContractPage from "./screens/me/contracts";
import ContractDetailPage from "./screens/me/contracts/ContractDetail";
import UtilityIndexPage from "./screens/me/contracts/UtilityIndexPage";
import InvoicePage from "./screens/me/invoices";
import ProfilePage from "./screens/me/profile";
import { RouteUrls } from "./utils/urlUtils";
import CheckoutRequestManagementScreen from "./screens/admin/entity-management/CheckoutRequestManagementScreen";

export interface RouteConfig {
  path: string;
  index: boolean;
  icon?: JSX.Element;
  name?: string;
  element: JSX.Element;
}

const routeConfigs: RouteConfig[] = [
  // Các route cho khách hàng (user)
  {
    path: RouteUrls.userProfile,
    index: false,
    icon: <i className="fas fa-user"></i>,
    name: "Thông tin cá nhân",
    element: (
      <RequireRole allowedRoles={[RoleEnum.USER]}>
        <ProfilePage />
      </RequireRole>
    ),
  },
  {
    path: RouteUrls.contract,
    index: false,
    icon: <i className="fas fa-user"></i>,
    name: "Thông tin hợp đồng",
    element: (
      <RequireRole allowedRoles={[RoleEnum.USER]}>
        <ContractPage />
      </RequireRole>
    ),
  },
  {
    path: RouteUrls.contractDetail,
    index: false,
    icon: <i className="fas fa-contract"></i>,
    name: "Thông tin hợp đồng chi tiết",
    element: (
      <RequireRole allowedRoles={[RoleEnum.USER]}>
        <ContractDetailPage />
      </RequireRole>
    ),
  },
  {
    path: RouteUrls.utilityIndexDetail,
    index: false,
    icon: <i className="fas fa-contract"></i>,
    name: "Thông tin chỉ số điện nước chi tiết",
    element: (
      <RequireRole allowedRoles={[RoleEnum.USER]}>
        <UtilityIndexPage />
      </RequireRole>
    ),
  },
  {
    path: RouteUrls.invoice,
    index: false,
    icon: <i className="fas fa-file-invoice"></i>,
    name: "Hóa đơn",
    element: (
      <RequireRole allowedRoles={[RoleEnum.USER]}>
        <InvoicePage />
      </RequireRole>
    ),
  },
  {
    path: RouteUrls.qualityMonitoring,
    index: false,
    icon: <i className="fas fa-water"></i>,
    name: "Giám sát chất lượng nước",
    element: (
      <RequireRole allowedRoles={[RoleEnum.USER]}>
        <>Giám sát chất lượng nước</>
      </RequireRole>
    ),
  },

  {
    path: RouteUrls.bills,
    index: false,
    icon: <i className="fas fa-file-invoice-dollar"></i>,
    name: "Hóa đơn tiền nhà",
    element: (
      <RequireRole allowedRoles={[RoleEnum.USER]}>
        <>Hóa đơn tiền nhà</>
      </RequireRole>
    ),
  },
  {
    path: RouteUrls.roomInfo,
    index: false,
    name: "Xem thông tin nhà trọ",
    element: (
      <RequireRole allowedRoles={[RoleEnum.USER]}>
        <>Xem thông tin nhà trọ</>
      </RequireRole>
    ),
  },
  {
    path: RouteUrls.roomFacilities,
    index: false,
    name: " Xem tiện nghi trong phòng",
    element: (
      <RequireRole allowedRoles={[RoleEnum.USER]}>
        <> Xem tiện nghi trong phòng</>
      </RequireRole>
    ),
  },

  {
    path: RouteUrls.electricityWater,
    index: false,
    name: "Quản lý điện - nước",
    element: (
      <RequireRole allowedRoles={[RoleEnum.USER]}>
        <>Quản lý điện - nước</>
      </RequireRole>
    ),
  },
  {
    path: RouteUrls.roomCheckout,
    index: false,
    name: "Thông báo trả phòng",
    element: (
      <RequireRole allowedRoles={[RoleEnum.USER]}>
        <>Thông báo trả phòng</>
      </RequireRole>
    ),
  },
  // Các route cho admin
  {
    path: RouteUrls.dashboard,
    index: false,
    icon: <i className="fas fa-tachometer-alt"></i>,
    name: "Trang chủ",
    element: (
      <RequireRole allowedRoles={[RoleEnum.ADMIN]}>
        <Home></Home>
      </RequireRole>
    ),
  },
  {
    path: RouteUrls.manageRooms,
    index: false,
    icon: <i className="fas fa-cogs"></i>,
    name: "Quản lý nhà trọ",
    element: (
      <RequireRole allowedRoles={[RoleEnum.ADMIN]}>
        <RoomManagementScreen></RoomManagementScreen>
      </RequireRole>
    ),
  },
  {
    path: RouteUrls.checkoutRequests,
    index: false,
    icon: <i className="fas fa-cogs"></i>,
    name: "Quản lý yêu cầu trả phòng",
    element: (
      <RequireRole allowedRoles={[RoleEnum.ADMIN]}>
        <CheckoutRequestManagementScreen />
      </RequireRole>
    ),
  },
  {
    path: RouteUrls.addUserToRooms,
    index: false,
    element: (
      <RequireRole allowedRoles={[RoleEnum.ADMIN]}>
        <RoomUserManagementScreen></RoomUserManagementScreen>
      </RequireRole>
    ),
  },
  {
    path: RouteUrls.manageElectricityWater,
    index: false,
    icon: <i className="fas fa-bolt"></i>,
    name: "Quản lý điện - nước",
    element: (
      <RequireRole allowedRoles={[RoleEnum.ADMIN]}>
        <UtilityWaterScreen />
      </RequireRole>
    ),
  },

  {
    path: RouteUrls.manageServices,
    index: false,
    name: "Quản lý dịch vụ",
    element: (
      <RequireRole allowedRoles={[RoleEnum.ADMIN]}>
        <ServiceManagementScreen />
      </RequireRole>
    ),
  },
  {
    path: RouteUrls.manageBills,
    index: false,
    name: "Tính tiền, Quản lý hóa đơn",
    element: (
      <RequireRole allowedRoles={[RoleEnum.ADMIN]}>
        <InvoiceManagementScreen></InvoiceManagementScreen>
      </RequireRole>
    ),
  },
  {
    path: RouteUrls.maintenanceRequests,
    index: false,
    name: "Quản lý bảo trì",
    element: (
      <RequireRole allowedRoles={[RoleEnum.ADMIN]}>
        <MaintenanceRequestScreen />
      </RequireRole>
    ),
  },
  {
    path: RouteUrls.revenueReports,
    index: false,
    name: "Báo cáo doanh thu",
    element: (
      <RequireRole allowedRoles={[RoleEnum.ADMIN]}>
        <RevenueStatResponsePage />
      </RequireRole>
    ),
  },
  {
    path: RouteUrls.waterReports,
    index: false,
    name: "Báo cáo điện nước",
    element: (
      <RequireRole allowedRoles={[RoleEnum.ADMIN]}>
        <RoomReportPage />
      </RequireRole>
    ),
  },
  {
    path: RouteUrls.sensorWaterReports,
    index: false,
    name: "Báo cáo tỷ lệ nước bẩn",
    element: (
      <RequireRole allowedRoles={[RoleEnum.ADMIN]}>
        <WaterMonitor />
      </RequireRole>
    ),
  },
  {
    path: RouteUrls.revenueReports,
    index: false,
    name: "Báo cáo và thống kê",
    element: (
      <RequireRole allowedRoles={[RoleEnum.ADMIN]}>
        <> Báo cáo doanh thu</>
      </RequireRole>
    ),
  },
  {
    path: RouteUrls.maintenanceRequestReports,
    index: false,
    name: "Báo cáo tình trạng bảo trì thiết bị",
    element: (
      <RequireRole allowedRoles={[RoleEnum.ADMIN]}>
        <MaintenanceReportPage />
      </RequireRole>
    ),
  },
  {
    path: RouteUrls.manageAccounts,
    index: false,
    name: "Quản lý tài khoản",
    element: (
      <RequireRole allowedRoles={[RoleEnum.ADMIN]}>
        <UserAdminScreen />
      </RequireRole>
    ),
  },
];

export const routeApplications = routeConfigs.map((routeConfig) => (
  <Route
    key={routeConfig.path}
    path={routeConfig.path}
    element={routeConfig.element}
    index={routeConfig.index}
  />
));
