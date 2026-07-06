import React, { useEffect, useMemo, useState } from "react";
import { RevenueApi, RevenueStatResponse } from "@/apis/apps/RevenueApi";
import {
  Breadcrumb,
  Card,
  Col,
  DatePicker,
  Row,
  Select,
  Statistic,
  Table,
  Typography,
} from "antd";
import { Link } from "react-router-dom";
import { formatCurrency } from "@/utils/currencyUtils";
import ColumnChart from "@/components/admin/chart/ColumnChart";

const { Title } = Typography;
const { Option } = Select;

const listLinkInBreadcrumb = [
  {
    title: <Link to="/">Dashboard</Link>,
  },
  {
    title: "Báo cáo doanh thu",
  },
];

type FilterType = "all" | "month" | "year";

const RevenueStatResponsePage: React.FC = () => {
  const [revenueData, setRevenueData] = useState<RevenueStatResponse[]>([]);
  const [filterType, setFilterType] = useState<FilterType>("all");
  const [selectedDate, setSelectedDate] = useState<Date>(new Date());

  const totalRevenue = useMemo(() => {
    if (
      (filterType === "month" || filterType === "year") &&
      revenueData.length === 1
    ) {
      return revenueData[0].totalRevenue;
    }
    return revenueData.reduce((sum, item) => sum + item.totalRevenue, 0);
  }, [revenueData, filterType]);

  const chartCategories = useMemo(() => {
    const currentYear = new Date().getFullYear();
    if (
      (filterType === "month" || filterType === "year") &&
      revenueData.length === 1
    ) {
      const item = revenueData[0];
      return [
        item.month
          ? `Tháng ${item.month}/${item.year || currentYear}`
          : `Năm ${item.year}`,
      ];
    }
    return revenueData.map((item) =>
      item.month
        ? `Tháng ${item.month}/${item.year || currentYear}`
        : `Năm ${item.year}`
    );
  }, [revenueData, filterType]);

  const chartData = useMemo(() => {
    if (
      (filterType === "month" || filterType === "year") &&
      revenueData.length === 1
    ) {
      return [revenueData[0].totalRevenue];
    }
    return revenueData.map((item) => item.totalRevenue);
  }, [revenueData, filterType]);

  useEffect(() => {
    let apiCall: Promise<RevenueStatResponse | RevenueStatResponse[]>;

    switch (filterType) {
      case "month":
        apiCall = RevenueApi.getRevenueByMonth(
          selectedDate.getMonth() + 1,
          selectedDate.getFullYear()
        );
        break;
      case "year":
        apiCall = RevenueApi.getRevenueByYear(selectedDate.getFullYear());
        break;
      default:
        apiCall = RevenueApi.getRevenue();
        break;
    }

    apiCall.then((response) => {
      if (filterType === "all") {
        const dataArray = response as RevenueStatResponse[];
        setRevenueData(dataArray);
      } else {
        const dataItem = response as RevenueStatResponse;
        if (
          dataItem &&
          typeof dataItem === "object" &&
          dataItem.totalRevenue !== undefined &&
          dataItem.totalRevenue !== null
        ) {
          setRevenueData([dataItem]);
        } else {
          setRevenueData([]);
        }
      }
    });

    // No cleanup needed for this effect
  }, [filterType, selectedDate]); // Dependencies are filterType and selectedDate

  const handleFilterChange = (value: FilterType) => {
    setFilterType(value);
  };

  const handleDateChange = (date: any) => {
    if (date) {
      // Assuming date is a Moment.js or Day.js object from Ant Design DatePicker
      setSelectedDate(date.toDate()); // Convert to a standard JavaScript Date object
    }
  };

  const columns = [
    {
      title: "Năm",
      dataIndex: "year",
      key: "year",
    },
    {
      title: "Tháng",
      dataIndex: "month",
      key: "month",
      render: (month: number) => month || "Tất cả",
    },
    {
      title: "Doanh thu",
      dataIndex: "totalRevenue",
      key: "totalRevenue",
      render: (value: number) => formatCurrency(value),
    },
  ];

  // Determine what date picker to show based on filter type
  const renderDatePicker = () => {
    switch (filterType) {
      case "month":
        return (
          <DatePicker
            picker="month"
            onChange={handleDateChange}
            allowClear={false}
          />
        );
      case "year":
        return (
          <DatePicker
            picker="year"
            onChange={handleDateChange}
            allowClear={false}
          />
        );
      default:
        return null;
    }
  };

  return (
    <div className="p-6 bg-gray-100 min-h-screen">
      <div className="mb-6">
        <Breadcrumb items={listLinkInBreadcrumb} />
        <Title level={2} className="mt-4 text-gray-800">
          Báo cáo doanh thu
        </Title>
      </div>

      <Row gutter={[24, 24]} className="mb-6">
        <Col xs={24} sm={12} md={8}>
          <Card className="shadow-md rounded-lg">
            <Statistic
              title="Tổng doanh thu"
              value={totalRevenue}
              precision={0}
              formatter={(value) => formatCurrency(value as number)}
            />
          </Card>
        </Col>
      </Row>

      <Row gutter={[24, 24]} className="mb-6 items-center">
        <Col xs={24} sm={12} md={6}>
          <Select
            className="w-full"
            value={filterType}
            onChange={handleFilterChange}
            size="large"
          >
            <Option value="all">Tất cả</Option>
            <Option value="month">Theo tháng</Option>
            <Option value="year">Theo năm</Option>
          </Select>
        </Col>
        <Col xs={24} sm={12} md={6}>
          {renderDatePicker()}
        </Col>
      </Row>

      {revenueData.length > 0 ? (
        <Row gutter={[24, 24]} className="mb-6">
          <Col span={24}>
            <Card className="shadow-md rounded-lg">
              <ColumnChart
                dataSeries={chartData}
                categories={chartCategories}
                nameSeries="Doanh thu"
                title="Biểu đồ doanh thu"
                height={350}
              />
            </Card>
          </Col>
        </Row>
      ) : (
        <Row gutter={[24, 24]} className="mb-6">
          <Col span={24}>
            <Card className="shadow-md rounded-lg p-6 text-center text-gray-500">
              Không có dữ liệu doanh thu cho khoảng thời gian này.
            </Card>
          </Col>
        </Row>
      )}

      <Card className="shadow-md rounded-lg">
        <Table
          columns={columns}
          dataSource={revenueData}
          rowKey={(record) =>
            `${record.year}-${record.month || 0}-${record.quarter || 0}`
          }
          pagination={false}
          locale={{ emptyText: "Không có dữ liệu doanh thu" }}
        />
      </Card>
    </div>
  );
};

export default RevenueStatResponsePage;
