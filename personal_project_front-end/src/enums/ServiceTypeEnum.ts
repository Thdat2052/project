export enum ServiceTypeEnum {
  WATER = "WATER",
  ELECTRIC = "ELECTRIC",
  OTHER = "OTHER",
}

export function renderServiceType(type: ServiceTypeEnum): string {
  switch (type) {
  case ServiceTypeEnum.WATER:
    return "Nước";
  case ServiceTypeEnum.ELECTRIC:
    return "Điện";
  case ServiceTypeEnum.OTHER:
    return "Khác";
  default:
    return "Không xác định";
  }
}
