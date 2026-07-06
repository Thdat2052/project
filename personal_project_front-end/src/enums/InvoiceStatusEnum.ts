export enum InvoiceStatusEnum {
  PENDING = "PENDING",
  PAID = "PAID",
  OVERDUE = "OVERDUE",
  CANCELLED = "CANCELLED",
  UNPAID = "UNPAID",
}
export function renderInvoiceStatus(status: InvoiceStatusEnum): string {
  switch (status) {
    case InvoiceStatusEnum.PENDING:
      return "Chờ xử lý";
    case InvoiceStatusEnum.PAID:
      return "Đã thanh toán";
    case InvoiceStatusEnum.OVERDUE:
      return "Quá hạn";
    case InvoiceStatusEnum.CANCELLED:
      return "Đã hủy";
    case InvoiceStatusEnum.UNPAID:
      return "Chưa thanh toán";
    default:
      return "Không xác định";
  }
}
