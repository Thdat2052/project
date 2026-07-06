import React, { useEffect, useState, useMemo } from "react";
import { Table, Typography, Button, Card, Tooltip, Space, Input } from "antd";
import { EyeOutlined } from "@ant-design/icons"; // Import Ant Design icon
import type { InvoiceInformationDetailResponse } from "@/models/response/InvoiceInformationDetailResponse";
import InvoiceDetailModal from "./InvoiceDetailModal"; // Assuming this modal is styled
import type { TableColumnsType } from "antd";
import { InvoiceApi } from "@/apis/apps/InvoiceApi";
import { InvoiceStatusEnum } from "@/enums/InvoiceStatusEnum";

const { Text, Title } = Typography;

const STATUS_MAP: Record<InvoiceStatusEnum, string> = {
  [InvoiceStatusEnum.PENDING]: "Chờ thanh toán",
  [InvoiceStatusEnum.PAID]: "Đã thanh toán",
  [InvoiceStatusEnum.CANCELLED]: "Đã hủy",
  [InvoiceStatusEnum.UNPAID]: "Chưa thanh toán",
  [InvoiceStatusEnum.OVERDUE]: "Quá hạn",
};
const InvoicePage: React.FC = () => {
  const [invoiceData, setInvoiceData] = useState<
    InvoiceInformationDetailResponse[]
  >([]);
  const [selectedInvoice, setSelectedInvoice] =
    useState<InvoiceInformationDetailResponse | null>(null);
  const [searchText, setSearchText] = useState<string>("");

  const fetchInvoiceData = () => {
    InvoiceApi.getMe().then((data) => {
      setInvoiceData(data);
    });
  };

  useEffect(() => {
    fetchInvoiceData();
  }, []);

  const filteredInvoiceData = useMemo(() => {
    const lowerCaseSearchText = searchText.toLowerCase();
    return invoiceData.filter((invoice) => {
      return (
        (invoice.invoiceInformation?.roomName ?? "")
          .toLowerCase()
          .includes(lowerCaseSearchText) ||
        (invoice.invoiceInformation?.username ?? "")
          .toLowerCase()
          .includes(lowerCaseSearchText) ||
        STATUS_MAP[invoice.detail?.invoiceStatus ?? InvoiceStatusEnum.PENDING]
          .toLowerCase()
          .includes(lowerCaseSearchText) ||
        (invoice.invoiceInformation?.monthlyRent?.toString() ?? "")
          .toLowerCase()
          .includes(lowerCaseSearchText) ||
        (invoice.detail?.invoiceCreated
          ? new Date(invoice.detail.invoiceCreated).toLocaleDateString("vi-VN")
          : ""
        )
          .toLowerCase()
          .includes(lowerCaseSearchText) ||
        (invoice.totalMoney?.toString() ?? "")
          .toLowerCase()
          .includes(lowerCaseSearchText)
      );
    });
  }, [searchText, invoiceData]);

  const columns: TableColumnsType<InvoiceInformationDetailResponse> = useMemo(
    () => [
      {
        title: "Phòng",
        dataIndex: ["invoiceInformation", "roomName"],
        key: "roomName",
        sorter: (a, b) =>
          (a.invoiceInformation?.roomName ?? "").localeCompare(
            b.invoiceInformation?.roomName ?? ""
          ),
      },
      {
        title: "Người thuê",
        dataIndex: ["invoiceInformation", "username"],
        key: "username",
        sorter: (a, b) =>
          (a.invoiceInformation?.username ?? "").localeCompare(
            b.invoiceInformation?.username ?? ""
          ),
      },
      {
        title: "Trạng thái",
        dataIndex: ["detail", "invoiceStatus"],
        key: "invoiceStatus",
        render: (value?: InvoiceStatusEnum) => {
          return (
            <Text
              type={
                value === InvoiceStatusEnum.PAID
                  ? "success"
                  : value === InvoiceStatusEnum.CANCELLED
                  ? "danger"
                  : "warning"
              }
            >
              {STATUS_MAP[value ?? InvoiceStatusEnum.PENDING]}
            </Text>
          );
        },
      },
      {
        title: "Tiền thuê",
        dataIndex: ["invoiceInformation", "monthlyRent"],
        key: "monthlyRent",
        align: "right",
        render: (value?: number) => (value !== null ? `${value}₫` : "N/A"),
        sorter: (a, b) =>
          (a.invoiceInformation?.monthlyRent ?? 0) -
          (b.invoiceInformation?.monthlyRent ?? 0),
      },
      {
        title: "Ngày lập hóa đơn",
        dataIndex: ["detail", "invoiceCreated"],
        key: "invoiceCreated",
        render: (value?: string) =>
          value ? new Date(value).toLocaleDateString("vi-VN") : "N/A",
        sorter: (a, b) =>
          new Date(a.detail?.invoiceCreated ?? "").getTime() -
          new Date(b.detail?.invoiceCreated ?? "").getTime(),
      },
      {
        title: "Tổng tiền",
        dataIndex: "totalMoney",
        key: "totalMoney",
        align: "right",
        render: (value?: number) => (
          <Text strong>{value !== null ? `${value}₫` : "N/A"}</Text>
        ),
        sorter: (a, b) => (a.totalMoney ?? 0) - (b.totalMoney ?? 0),
      },
      {
        title: "Hành động",
        key: "action",
        align: "center",
        render: (_: any, record: InvoiceInformationDetailResponse) => (
          <Tooltip title="Xem chi tiết hóa đơn">
            <Button
              type="text"
              icon={<EyeOutlined style={{ color: "#1890ff" }} />}
              onClick={() => setSelectedInvoice(record)}
            />
          </Tooltip>
        ),
      },
    ],
    []
  );

  const getRowKey = (record: InvoiceInformationDetailResponse): string => {
    return (
      record.detail?.invoiceId?.toString() ??
      `inv-${record.invoiceInformation?.roomId}-${Math.random()}`
    );
  };

  return (
    <div className="bg-white shadow-lg rounded-lg p-6">
      <div>
        <div className="flex justify-between items-center mb-4">
          <Title level={3}>Danh sách hóa đơn</Title>
          <Input.Search
            value={searchText}
            placeholder="Tìm kiếm hóa đơn..."
            onSearch={(value) => setSearchText(value)}
            onChange={(e) => setSearchText(e.target.value)}
            className="max-w-xs w-full"
          />
        </div>
        <Table
          dataSource={filteredInvoiceData}
          columns={columns}
          rowKey={getRowKey}
          pagination={{
            pageSize: 10,
            showSizeChanger: true,
            pageSizeOptions: ["5", "10", "20", "50"],
            showTotal: (total, range) =>
              `${range[0]}-${range[1]} của ${total} hóa đơn`,
          }}
          bordered
          scroll={{ x: "max-content" }}
          size="middle"
        />
      </div>
      {selectedInvoice && (
        <InvoiceDetailModal
          visible={!!selectedInvoice}
          onClose={() => setSelectedInvoice(null)}
          invoice={selectedInvoice}
        />
      )}
    </div>
  );
};

export default InvoicePage;
