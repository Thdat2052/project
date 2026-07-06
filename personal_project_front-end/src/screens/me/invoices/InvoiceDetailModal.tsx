import React, { useMemo } from "react";
import { Modal, Typography, QRCode, Descriptions, List, Space } from "antd";
import type { InvoiceInformationDetailResponse } from "@/models/response/InvoiceInformationDetailResponse";

const { Text, Title, Paragraph } = Typography;

interface InvoiceDetailModalProps {
  visible: boolean;
  onClose: () => void;
  invoice: InvoiceInformationDetailResponse | null;
}

const InvoiceDetailModal: React.FC<InvoiceDetailModalProps> = ({
  visible,
  onClose,
  invoice,
}) => {
  const qrValue = useMemo(() => {
    if (!invoice) return ""; // Return empty string if invoice is null
    return `Invoice ID: ${invoice.detail?.invoiceId} | Amount: ${invoice.totalMoney} VND | Tenant: ${invoice.invoiceInformation?.username}`;
  }, [invoice]);

  const infoItems = useMemo(() => {
    if (!invoice) return []; // Return empty array if invoice is null
    return [
      {
        key: "1",
        label: "Phòng",
        children: invoice.invoiceInformation?.roomName || "N/A",
      },
      {
        key: "2",
        label: "Người thuê",
        children: invoice.invoiceInformation?.username || "N/A",
      },
      {
        key: "3",
        label: "Tiền thuê phòng",
        children: (
          <Text strong>{`${
            invoice.invoiceInformation?.monthlyRent?.toLocaleString() ?? 0
          }₫`}</Text>
        ),
      },
    ];
  }, [invoice]);

  const summaryItems = useMemo(() => {
    if (!invoice) return []; // Return empty array if invoice is null
    return [
      {
        key: "1",
        label: "Ngày lập hóa đơn",
        children: invoice.detail?.invoiceCreated
          ? new Date(invoice.detail.invoiceCreated).toLocaleDateString("vi-VN")
          : "N/A",
      },
      {
        key: "3",
        label: (
          <Title level={5} className="m-0">
            Tổng cộng
          </Title>
        ),
        children: (
          <Title level={5} type="danger" className="m-0">{`${
            invoice.totalMoney?.toLocaleString() ?? 0
          }₫`}</Title>
        ),
      },
    ];
  }, [invoice]);

  if (!invoice) return null;

  return (
    <Modal
      open={visible}
      title={
        <Title level={4} className="m-0">
          Chi tiết hóa đơn
        </Title>
      }
      onCancel={onClose}
      footer={null}
      width={800}
      centered
      zIndex={10000}
    >
      <Space direction="vertical" size="middle" className="w-full">
        <Descriptions bordered column={1} size="small" items={infoItems} />

        {invoice.serviceOfRooms && invoice.serviceOfRooms.length > 0 && (
          <div>
            <Title level={5} className="mb-2 mt-2">
              Chi tiết dịch vụ phòng
            </Title>
            <List
              size="small"
              bordered
              dataSource={invoice.serviceOfRooms}
              renderItem={(item) => (
                <List.Item>
                  <List.Item.Meta title={item.serviceName} />
                  <Text strong>{item.totalMoney?.toLocaleString() ?? 0}₫</Text>
                </List.Item>
              )}
              className="invoice-service-list"
            />
          </div>
        )}

        {invoice.serviceMaintenanceOfRooms &&
          invoice.serviceMaintenanceOfRooms.length > 0 && (
            <div>
              <Title level={5} className="mb-2 mt-2">
                Chi tiết dịch vụ bảo trì
              </Title>
              <List
                size="small"
                bordered
                dataSource={invoice.serviceMaintenanceOfRooms}
                renderItem={(item) => (
                  <List.Item>
                    <List.Item.Meta title={item.serviceName} />
                    <Text strong>
                      {item.totalMoney?.toLocaleString() ?? 0}₫
                    </Text>
                  </List.Item>
                )}
                className="invoice-service-list"
              />
            </div>
          )}

        <Descriptions bordered column={1} size="small" items={summaryItems} />

        <div className="text-center mt-4 p-4 bg-gray-100 rounded-lg">
          <Title level={5} className="mb-3">
            Quét mã QR để thanh toán
          </Title>
          <QRCode value={qrValue} size={160} />
          <Paragraph type="secondary" className="mt-3 text-sm max-w-xs mx-auto">
            Sử dụng ứng dụng ngân hàng hoặc ví điện tử hỗ trợ VietQR.
          </Paragraph>
        </div>
      </Space>
    </Modal>
  );
};

export default InvoiceDetailModal;
