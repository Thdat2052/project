/* eslint-disable no-console */
import React, { useEffect, useMemo, useState } from "react";
import {
  Button,
  Card,
  Col,
  Row,
  Statistic,
  Table,
  Typography,
  Space,
  Input,
} from "antd";
import {
  ArrowLeftOutlined,
  ThunderboltOutlined,
  ExperimentOutlined,
} from "@ant-design/icons";
import { UtilityIndexResponse } from "@/models/response/UtilityIndexResponse";
import { useNavigate, useParams } from "react-router-dom";
import type { TableColumnsType } from "antd";
import { UtilityIndexApi } from "@/apis/apps/UtilityIndexApi";
import LineChart from "@/components/admin/chart/LineChart";
import { notify } from "@/utils/notification";

const { Title, Text } = Typography;

const UtilityIndexPage: React.FC = () => {
  const { id: contractIdParam } = useParams<{ id: string }>();
  const [utilityIndex, setUtilityIndexs] = useState<UtilityIndexResponse[]>([]);
  const navigate = useNavigate();
  const [searchText, setSearchText] = useState<string>("");

  const fetchUtilityIndexData = (contractId: number) => {
    UtilityIndexApi.findByContractId(contractId)
      .then((response) => {
        setUtilityIndexs(response);
      })
      .catch((error: any) => {
        notify.error(
          error.message || "Đã xảy ra lỗi khi tải dữ liệu chỉ số điện nước."
        );
      });
  };

  useEffect(() => {
    if (contractIdParam) {
      const parsedId = Number(contractIdParam);
      if (!isNaN(parsedId) && parsedId > 0) {
        fetchUtilityIndexData(parsedId);
      } else {
        notify.error("Mã hợp đồng không hợp lệ.");
        navigate(-1);
      }
    } else {
      notify.error("Không tìm thấy mã hợp đồng.");
      navigate(-1);
    }
  }, [contractIdParam, navigate]);

  const filteredUtilityIndex = useMemo(() => {
    const lowerCaseSearchText = searchText.toLowerCase();
    return utilityIndex.filter((item) => {
      return (
        `${String(item.monthMeasure).padStart(2, "0")}/${item.yearMeasure}`
          .toLowerCase()
          .includes(lowerCaseSearchText) ||
        (item.electricityOldIndex?.toString() ?? "")
          .toLowerCase()
          .includes(lowerCaseSearchText) ||
        (item.electricityNewIndex?.toString() ?? "")
          .toLowerCase()
          .includes(lowerCaseSearchText) ||
        (item.electricUsage?.toString() ?? "")
          .toLowerCase()
          .includes(lowerCaseSearchText) ||
        (item.waterOldIndex?.toString() ?? "")
          .toLowerCase()
          .includes(lowerCaseSearchText) ||
        (item.waterNewIndex?.toString() ?? "")
          .toLowerCase()
          .includes(lowerCaseSearchText) ||
        (item.waterUsage?.toString() ?? "")
          .toLowerCase()
          .includes(lowerCaseSearchText)
      );
    });
  }, [searchText, utilityIndex]);

  const totalElectricUsage = useMemo(
    () =>
      utilityIndex.reduce((sum, item) => sum + (item.electricUsage || 0), 0),
    [utilityIndex]
  );

  const totalWaterUsage = useMemo(
    () => utilityIndex.reduce((sum, item) => sum + (item.waterUsage || 0), 0),
    [utilityIndex]
  );

  const categories = useMemo(
    () =>
      utilityIndex.map(
        (item) =>
          `${item.yearMeasure}-${String(item.monthMeasure).padStart(2, "0")}`
      ),
    [utilityIndex]
  );

  const series = useMemo(
    () => [
      {
        name: "Điện",
        data: utilityIndex.map((item) => item.electricUsage ?? 0),
      },
      {
        name: "Nước",
        data: utilityIndex.map((item) => item.waterUsage ?? 0),
      },
    ],
    [utilityIndex]
  );

  const columns: TableColumnsType<UtilityIndexResponse> = useMemo(
    () => [
      {
        title: "Thời gian",
        key: "time",
        render: (_: any, record: UtilityIndexResponse) =>
          `${String(record.monthMeasure).padStart(2, "0")}/${
            record.yearMeasure
          }`,
        sorter: (a: UtilityIndexResponse, b: UtilityIndexResponse) => {
          if (a.yearMeasure !== b.yearMeasure)
            return a.yearMeasure - b.yearMeasure;
          return a.monthMeasure - b.monthMeasure;
        },
        defaultSortOrder: "ascend",
      },
      {
        title: "Chỉ số điện cũ",
        dataIndex: "electricityOldIndex",
        key: "electricityOldIndex",
        align: "right",
      },
      {
        title: "Chỉ số điện mới",
        dataIndex: "electricityNewIndex",
        key: "electricityNewIndex",
        align: "right",
      },
      {
        title: "Số điện tiêu thụ (kWh)",
        dataIndex: "electricUsage",
        key: "electricUsage",
        align: "right",
      },
      {
        title: "Chỉ số nước cũ",
        dataIndex: "waterOldIndex",
        key: "waterOldIndex",
        align: "right",
      },
      {
        title: "Chỉ số nước mới",
        dataIndex: "waterNewIndex",
        key: "waterNewIndex",
        align: "right",
      },
      {
        title: "Số nước tiêu thụ (m³)",
        dataIndex: "waterUsage",
        key: "waterUsage",
        align: "right",
      },
    ],
    []
  );

  return (
    <div className="p-6">
      <div className="flex items-center justify-between mb-6">
        <Button icon={<ArrowLeftOutlined />} onClick={() => navigate(-1)}>
          Quay lại
        </Button>
        <Title level={3} className="m-0">
          Chi tiết chỉ số điện & nước
        </Title>
      </div>

      <Space direction="vertical" size="large" className="w-full">
        <Row gutter={[16, 16]}>
          <Col xs={24} sm={12}>
            <Card bordered={false} className="shadow-md">
              <Statistic
                title="Tổng số điện đã sử dụng"
                value={totalElectricUsage}
                precision={2}
                prefix={<ThunderboltOutlined />}
                suffix="kWh"
                valueStyle={{ color: "#1890ff" }}
              />
            </Card>
          </Col>
          <Col xs={24} sm={12}>
            <Card bordered={false} className="shadow-md">
              <Statistic
                title="Tổng số nước đã sử dụng"
                value={totalWaterUsage}
                precision={2}
                prefix={<ExperimentOutlined />}
                suffix="m³"
                valueStyle={{ color: "#52c41a" }}
              />
            </Card>
          </Col>
        </Row>

        <Card
          title="Biểu đồ tiêu thụ điện & nước theo tháng"
          bordered={false}
          className="shadow-md"
        >
          {utilityIndex.length > 0 ? (
            <LineChart
              series={series}
              categories={categories}
              height={300}
              type="line"
              zoomEnabled={false}
              dataLabelsEnabled={false}
              curve="smooth"
              title="Tiêu thụ điện & nước"
              titleAlign="left"
              gridRowColors={["#f3f3f3", "transparent"]}
              gridRowOpacity={0.5}
            />
          ) : (
            <Text type="secondary">Chưa có dữ liệu để hiển thị biểu đồ.</Text>
          )}
        </Card>

        <Card bordered={false} className="shadow-md">
          <div className="flex items-center justify-between mb-4">
            <Title level={4} className="m-0">
              Danh sách chỉ số điện & nước
            </Title>
            <Input.Search
              placeholder="Tìm kiếm theo tháng/năm hoặc chỉ số"
              onSearch={(value) => setSearchText(value)}
              allowClear
              className="max-w-xs w-full"
            />
          </div>
          <Table
            dataSource={filteredUtilityIndex}
            columns={columns}
            rowKey="id"
            bordered
            pagination={{
              pageSize: 10,
              showSizeChanger: true,
              pageSizeOptions: ["5", "10", "20"],
            }}
            scroll={{ x: "max-content" }}
            size="middle"
          />
        </Card>
      </Space>
    </div>
  );
};

export default UtilityIndexPage;
