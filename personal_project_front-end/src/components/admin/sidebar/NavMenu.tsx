import { RoleEnum } from "@/enums/RoleEnum";
import { RouteUrls } from "@/utils/urlUtils";
import React, { ReactNode } from "react";
import {
  AiFillCalculator,
  AiFillDashboard,
  AiFillFileText,
  AiFillHome,
  AiFillMedicineBox,
  AiFillThunderbolt,
  AiOutlineAppstore,
  AiOutlineContacts,
  AiOutlineHistory,
  AiOutlineSelect,
  AiOutlineUsergroupAdd,
  AiTwotoneSnippets,
} from "react-icons/ai";
import { FaWater } from "react-icons/fa";
import { NavLink, useLocation } from "react-router-dom";
import SidebarLinkGroup from "./SidebarLinkGroup";
export type NavItem = {
  to: string;
  label: string;
  icon: ReactNode;
  isGroup?: boolean;
  groupPath?: string;
  childrens?: NavItem[];
  role: RoleEnum;
};

const navItems: NavItem[] = [
  // for User
  {
    to: RouteUrls.userProfile,
    label: "Quản lý thông tin cá nhân",
    icon: <AiOutlineUsergroupAdd size={25} />,
    isGroup: false,
    role: RoleEnum.USER,
  },
  {
    to: RouteUrls.contract,
    label: "Quản lý thông tin hợp đồng",
    icon: <AiOutlineContacts size={25} />,
    isGroup: false,
    role: RoleEnum.USER,
  },
  {
    to: RouteUrls.invoice,
    label: "Quản lý thông tin hóa đơn",
    icon: <AiOutlineHistory size={25} />,
    isGroup: false,
    role: RoleEnum.USER,
  },

  // for Admin
  {
    to: RouteUrls.dashboard,
    label: "Trang chủ",
    icon: <AiFillHome size={25} />,
    isGroup: false,
    role: RoleEnum.ADMIN,
  },
  {
    to: RouteUrls.manageRooms,
    label: "Quản lý nhà trọ",
    icon: <AiOutlineAppstore size={25} />,
    isGroup: false,
    role: RoleEnum.ADMIN,
  },
  {
    to: RouteUrls.manageElectricityWater,
    label: "Quản lý điện - nước",
    icon: <FaWater size={25} />,
    isGroup: false,
    role: RoleEnum.ADMIN,
  },
  {
    to: RouteUrls.manageServices,
    label: "Quản lý dịch vụ",
    icon: <AiTwotoneSnippets size={25} />,
    isGroup: false,
    role: RoleEnum.ADMIN,
  },
  {
    to: RouteUrls.manageBills,
    label: "Tính tiền",
    icon: <AiFillCalculator size={25} />,
    isGroup: false,
    role: RoleEnum.ADMIN,
  },
  {
    to: RouteUrls.maintenanceRequests,
    label: "Quản lý bảo trì",
    icon: <AiFillMedicineBox size={25} />,
    isGroup: false,
    role: RoleEnum.ADMIN,
  },
  {
    to: RouteUrls.checkoutRequests,
    label: "Quản lý yêu cầu trả phòng",
    icon: <AiFillMedicineBox size={25} />,
    isGroup: false,
    role: RoleEnum.ADMIN,
  },
  {
    to: RouteUrls.manageAccounts,
    label: "Quản lý tài khoản",
    icon: <AiOutlineUsergroupAdd size={25} />,
    isGroup: false,
    role: RoleEnum.ADMIN,
  },
  {
    to: "#",
    label: "Báo cáo và thống kê",
    icon: <AiFillDashboard size={25} />,
    isGroup: true,
    role: RoleEnum.ADMIN,
    groupPath: "reports",
    childrens: [
      {
        to: RouteUrls.revenueReports,
        label: "Báo cáo doanh thu",
        icon: <AiFillFileText size={25} />,
        isGroup: false,
        role: RoleEnum.ADMIN,
      },
      {
        to: RouteUrls.waterReports,
        label: "Báo cáo điện nước",
        icon: <AiFillThunderbolt size={25} />,
        isGroup: false,
        role: RoleEnum.ADMIN,
      },
      {
        to: RouteUrls.sensorWaterReports,
        label: "Báo cáo tỷ lệ nước bẩn",
        icon: <AiFillMedicineBox size={25} />,
        isGroup: false,
        role: RoleEnum.ADMIN,
      },
      {
        to: RouteUrls.maintenanceRequestReports,
        label: "Báo cáo tình trạng bảo trì thiết bị",
        icon: <AiOutlineSelect size={25} />,
        isGroup: false,
        role: RoleEnum.ADMIN,
      },
    ],
  },
];

interface NavMenuProps {
  userRole: RoleEnum;
  sidebarExpanded: boolean;
  onSetSidebarExpanded: (value: boolean) => void;
}

const NavMenu: React.FC<NavMenuProps> = ({
  userRole,
  sidebarExpanded,
  onSetSidebarExpanded,
}) => {
  const location = useLocation();
  const { pathname } = location;
  return (
    <ul>
      {navItems
        .filter((item) => item.role === userRole)
        .map((item, index) =>
          !item.isGroup ? (
            <li key={index}>
              <NavLink
                to={item.to}
                className="group relative flex items-center gap-2.5 rounded-sm py-2 px-4 font-medium text-bodydark1 duration-300 ease-in-out hover:bg-graydark dark:hover:bg-meta-4 bg-graydark dark:bg-meta-4"
                style={({ isActive }) => ({
                  color: isActive
                    ? "red"
                    : "rgb(222 228 238 / var(--tw-text-opacity))",
                })}
              >
                {item.icon}
                {item.label}
              </NavLink>
            </li>
          ) : (
            <SidebarLinkGroup
              // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
              activeCondition={pathname.includes(item.groupPath ?? "")}
            >
              {(handleClick, open) => {
                return (
                  <React.Fragment>
                    <NavLink
                      to="#"
                      style={() => ({
                        color: pathname.includes(item.groupPath ?? "")
                          ? "red"
                          : "rgb(222 228 238 / var(--tw-text-opacity))",
                      })}
                      className={`group relative flex items-center gap-2.5 rounded-sm py-2 px-4 font-medium
                       text-bodydark1 duration-300 ease-in-out hover:bg-graydark dark:hover:bg-meta-4 
                      bg-graydark dark:bg-meta-4
                    `}
                      onClick={(e) => {
                        e.preventDefault();
                        sidebarExpanded
                          ? handleClick()
                          : onSetSidebarExpanded(true);
                      }}
                    >
                      {item.icon}
                      {item.label}
                      <svg
                        className={`absolute right-4 top-1/2 -translate-y-1/2 fill-current ${
                          open && "rotate-180"
                        }`}
                        width="20"
                        height="20"
                        viewBox="0 0 20 20"
                        fill="none"
                        xmlns="http://www.w3.org/2000/svg"
                      >
                        <path
                          fillRule="evenodd"
                          clipRule="evenodd"
                          d="M4.41107 6.9107C4.73651 6.58527 5.26414 6.58527 5.58958 6.9107L10.0003 11.3214L14.4111 6.91071C14.7365 6.58527 15.2641 6.58527 15.5896 6.91071C15.915 7.23614 15.915 7.76378 15.5896 8.08922L10.5896 13.0892C10.2641 13.4147 9.73651 13.4147 9.41107 13.0892L4.41107 8.08922C4.08563 7.76378 4.08563 7.23614 4.41107 6.9107Z"
                          fill=""
                        />
                      </svg>
                    </NavLink>
                    {/* <!-- Dropdown Menu Start --> */}
                    <div
                      className={`translate transform overflow-hidden ${
                        !open && "hidden"
                      }`}
                    >
                      <ul className="mt-4 mb-5.5 flex flex-col gap-2.5 pl-6">
                        {item.childrens?.map((item, index) => (
                          <li key={index}>
                            <NavLink
                              to={item.to}
                              className="group relative flex items-center gap-2.5 rounded-sm py-2 px-4 font-medium text-bodydark1 duration-300 ease-in-out hover:bg-graydark dark:hover:bg-meta-4 bg-graydark dark:bg-meta-4"
                              style={({ isActive }) => ({
                                color: isActive
                                  ? "red"
                                  : "rgb(222 228 238 / var(--tw-text-opacity))",
                              })}
                            >
                              {item.icon}
                              {item.label}
                            </NavLink>
                          </li>
                        ))}
                      </ul>
                    </div>
                    {/* <!-- Dropdown Menu End --> */}
                  </React.Fragment>
                );
              }}
            </SidebarLinkGroup>
          )
        )}
    </ul>
  );
};

export default NavMenu;
