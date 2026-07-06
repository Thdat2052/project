import React, { useEffect, useMemo, useState } from "react";
import {
  MaintenanceReportApi,
  MaintenanceStatusReportModel,
} from "@/apis/apps/MaintenanceReportApi";
import {
  Breadcrumb,
  Card,
  Col,
  DatePicker,
  Row,
  Table,
  Typography,
} from "antd";
import { Link } from "react-router-dom";
import { formatCurrency } from "@/utils/currencyUtils";
import { notify } from "@/utils/notification";
import dayjs from "dayjs"; // Assuming dayjs is used with Ant Design DatePicker
import { formatDate } from "@/utils/dateUtils"; // Assuming a date formatting utility exists

const { Title } = Typography;
const { RangePicker } = DatePicker;

const listLinkInBreadcrumb = [
  {
    title: <Link to="/">Dashboard</Link>,
  },
  {
    title: "Báo cáo bảo trì",
  },
];

const MaintenanceReportPage: React.FC = () => {
  const [reportData, setReportData] = useState<MaintenanceStatusReportModel[]>(
    []
  );
  const [selectedDateRange, setSelectedDateRange] = useState<
    [dayjs.Dayjs | null, dayjs.Dayjs | null]
  >([dayjs().startOf("month"), dayjs().endOf("month")]);

  useEffect(() => {
    if (selectedDateRange && selectedDateRange[0] && selectedDateRange[1]) {
      const startDate = selectedDateRange[0].toDate();
      const endDate = selectedDateRange[1].toDate();

      MaintenanceReportApi.getRoomUtilityUsageReport(startDate, endDate) // Using the provided API method name
        .then((response) => {
          if (Array.isArray(response)) {
            setReportData(response);
          } else {
            setReportData([]); // Handle non-array response
          }
        })
        .catch((error) => {
          notify.error("Không thể tải dữ liệu báo cáo bảo trì");
          console.error(error);
          setReportData([]); // Set to empty on error
        });
    } else {
      setReportData([]); // Clear data if date range is not selected
    }
  }, [selectedDateRange]);

  const handleDateRangeChange = (
    dates: [dayjs.Dayjs | null, dayjs.Dayjs | null] | null
  ) => {
    setSelectedDateRange(dates || [null, null]);
  };

  const columns = useMemo(
    () => [
      {
        title: "ID Phòng",
        dataIndex: "roomId",
        key: "roomId",
      },
      {
        title: "Tên Phòng",
        dataIndex: "roomName",
        key: "roomName",
      },
      {
        title: "ID Bảo trì",
        dataIndex: "maintenanceId",
        key: "maintenanceId",
      },
      {
        title: "Trạng thái",
        dataIndex: "maintenanceStatus",
        key: "maintenanceStatus",
      },
      {
        title: "Ngày yêu cầu",
        dataIndex: "requestDate",
        key: "requestDate",
        render: (date: string) =>
          date ? dayjs(date).format("YYYY-MM-DD HH:mm") : "N/A", // Parse and format date string
      },
      {
        title: "Ngày hoàn thành",
        dataIndex: "requestDoneDate",
        key: "requestDoneDate",
        render: (date: string) =>
          date ? dayjs(date).format("YYYY-MM-DD HH:mm") : "N/A", // Parse and format date string, handle null/empty
      },
      {
        title: "Tổng phí",
        dataIndex: "totalFee",
        key: "totalFee",
        render: (value: number) => formatCurrency(value),
      },
    ],
    []
  );

  return (
    <div className="p-6 bg-gray-100 min-h-screen">
      <div className="mb-6">
        <Breadcrumb items={listLinkInBreadcrumb} />
      </div>

      <div className="mb-6 items-center flex justify-between">
        <Title level={2} className="mt-4 text-gray-800">
          Báo cáo bảo trì
        </Title>
        <RangePicker
          value={selectedDateRange}
          onChange={handleDateRangeChange}
          size="large"
        />
      </div>

      {reportData.length === 0 && (
        <Row gutter={[24, 24]} className="mb-6">
          <Col span={24}>
            <Card className="shadow-md rounded-lg p-6 text-center text-gray-500">
              Không có dữ liệu báo cáo bảo trì cho khoảng thời gian này.
            </Card>
          </Col>
        </Row>
      )}

      {reportData.length > 0 && (
        <Card className="shadow-md rounded-lg">
          <Table
            columns={columns}
            dataSource={reportData}
            rowKey={(record) => record.maintenanceId} // Assuming maintenanceId is unique
            pagination={false}
            locale={{ emptyText: "Không có dữ liệu báo cáo bảo trì" }}
          />
        </Card>
      )}
    </div>
  );
};

export default MaintenanceReportPage;
