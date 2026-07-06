import { renderInvoiceStatus } from "@/enums/InvoiceStatusEnum";
import { InvoiceServiceInfoDetailModel } from "@/models/InvoiceServiceInfoDetailModel";
import { formatCurrency } from "@/utils/currencyUtils";
import React from "react";

type Props = {
  invoice: InvoiceServiceInfoDetailModel;
};

export const InvoiceDetail: React.FC<Props> = ({ invoice }) => {
  return (
    <div
      style={{
        padding: "16px",
        maxWidth: "600px",
        border: "1px solid #ccc",
        borderRadius: "8px",
      }}
    >
      <h2>
        {" "}
        <strong>Chi tiết Hóa đơn </strong>
      </h2>
      <p>
        <strong>Khách hàng:</strong> {invoice.fullName}
      </p>
      <p>
        <strong>Phòng:</strong> {invoice.roomId}
      </p>
      <p>
        <strong>Giá phòng cho thuê:</strong> {formatCurrency(invoice.roomPrice)}
      </p>
      <p>
        <strong>Tháng tính:</strong> {invoice.monthCal}/{invoice.yearCal}
      </p>
      <p>
        <strong>Ngày tạo hóa đơn:</strong>{" "}
        {new Date(invoice.invoiceCreated).toLocaleString()}
      </p>
      <p>
        <strong>Trạng thái:</strong> {renderInvoiceStatus(invoice.status)}
      </p>

      <h3>Dịch vụ sử dụng</h3>
      <table style={{ width: "100%", borderCollapse: "collapse" }}>
        <thead>
          <tr>
            <th style={{ borderBottom: "1px solid #ddd", textAlign: "left" }}>
              Dịch vụ
            </th>
            <th style={{ borderBottom: "1px solid #ddd" }}>Đơn giá</th>
            <th style={{ borderBottom: "1px solid #ddd" }}>Số lượng</th>
            <th style={{ borderBottom: "1px solid #ddd" }}>Thành tiền</th>
          </tr>
        </thead>
        <tbody>
          {invoice.serviceInfos.map((service) => {
            const total = service.priceService * service.quantityService;
            return (
              <tr key={service.serviceId}>
                <td>{service.nameService}</td>
                <td style={{ textAlign: "center" }}>
                  {formatCurrency(service.priceService)}
                </td>
                <td style={{ textAlign: "center" }}>
                  {service.quantityService}
                </td>
                <td style={{ textAlign: "right" }}>{formatCurrency(total)}</td>
              </tr>
            );
          })}
        </tbody>
      </table>

      <h3 style={{ textAlign: "right", marginTop: "20px" }}>
        Tổng tiền: {formatCurrency(invoice.totalInvoice)}
      </h3>
    </div>
  );
};
