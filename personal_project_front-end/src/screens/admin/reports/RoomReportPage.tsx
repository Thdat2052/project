import React, { useEffect, useMemo, useState } from "react";
import {
  RoomReportApi,
  RoomUtilityUsageReportModel,
} from "@/apis/apps/RoomReportApi";
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
import dayjs from "dayjs";

const { Title } = Typography;
const { RangePicker } = DatePicker;

const listLinkInBreadcrumb = [
  {
    title: <Link to="/">Dashboard</Link>,
  },
  {
    title: "Báo cáo sử dụng tiện ích phòng",
  },
];

const RoomReportPage: React.FC = () => {
  const [reportData, setReportData] = useState<RoomUtilityUsageReportModel[]>(
    []
  );
  const [selectedDateRange, setSelectedDateRange] = useState<
    [dayjs.Dayjs | null, dayjs.Dayjs | null]
  >([dayjs().startOf("month"), dayjs().endOf("month")]);

  useEffect(() => {
    if (selectedDateRange && selectedDateRange[0] && selectedDateRange[1]) {
      const startDate = selectedDateRange[0].toDate();
      const endDate = selectedDateRange[1].toDate();

      RoomReportApi.getRoomUtilityUsageReport(startDate, endDate).then(
        (response) => {
          if (Array.isArray(response)) {
            setReportData(response);
          } else {
            setReportData([]);
          }
        }
      );
    } else {
      setReportData([]);
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
        title: "Tổng sử dụng nước",
        dataIndex: "totalWaterUsage",
        key: "totalWaterUsage",
        render: (value: number) => `${value} m³`,
      },
      {
        title: "Tổng sử dụng điện",
        dataIndex: "totalElectricUsage",
        key: "totalElectricUsage",
        render: (value: number) => `${value} kWh`,
      },
      {
        title: "Tháng",
        dataIndex: "monthInventory",
        key: "monthInventory",
      },
      {
        title: "Năm",
        dataIndex: "yearInventory",
        key: "yearInventory",
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
          Báo cáo sử dụng tiện ích phòng
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
              Không có dữ liệu báo cáo cho khoảng thời gian này.
            </Card>
          </Col>
        </Row>
      )}

      {reportData.length > 0 && (
        <Card className="shadow-md rounded-lg">
          <Table
            columns={columns}
            dataSource={reportData}
            rowKey={(record) => record.roomId}
            pagination={false}
            locale={{ emptyText: "Không có dữ liệu báo cáo" }}
          />
        </Card>
      )}
    </div>
  );
};

export default RoomReportPage;
