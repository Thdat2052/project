export enum ServiceRoomStatusEnum {
  ACTIVE = "ACTIVE",
  SUSPENDED = "SUSPENDED",
  INACTIVE = "INACTIVE",
  UNDER_MAINTENANCE = "UNDER_MAINTENANCE",
  ERROR = "ERROR",
}

export function renderServiceRoomStatus(status: ServiceRoomStatusEnum): string {
  switch (status) {
    case ServiceRoomStatusEnum.ACTIVE:
      return "Đang hoạt động";
    case ServiceRoomStatusEnum.SUSPENDED:
      return "Tạm dừng";
    case ServiceRoomStatusEnum.INACTIVE:
      return "Không hoạt động";
    case ServiceRoomStatusEnum.UNDER_MAINTENANCE:
      return "Đang bảo trì";
    case ServiceRoomStatusEnum.ERROR:
      return "Lỗi";
    default:
      return "Không xác định";
  }
}
