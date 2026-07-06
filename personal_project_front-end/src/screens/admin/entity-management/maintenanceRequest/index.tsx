import { MaintenanceRequestApi } from "@/apis/apps/MaintenanceRequestApi ";
import { MaintenanceRequestResponse } from "@/models/response/MaintenanceRequestResponse";
import {
  Breadcrumb,
  Button,
  Input,
  Table,
  TableColumnsType,
  TablePaginationConfig,
  Typography,
} from "antd";
import React, { useEffect, useState } from "react";
import { notify } from "@/utils/notification";
import { Link } from "react-router-dom";
import { MaintenanceRequestRequestStatusEnum } from "@/enums/MaintenanceRequestRequestStatusEnum";
import { AiOutlineEdit } from "react-icons/ai";
import MaintenanceRequestEditModal from "./MaintenanceRequestEditModal";
import { SearchProps } from "antd/es/input/Search";

const listLinkInBreadcrumb = [
  {
    title: <Link to="/">Dashboard</Link>,
  },
  {
    title: "Quản lý yêu cầu bảo trì",
  },
];
const { Title } = Typography;

const MaintenanceRequestScreen: React.FC = () => {
  const [dataSource, setDataSource] = useState<MaintenanceRequestResponse[]>(
    []
  );
  const [pagination, setPagination] = useState({
    current: 1,
    pageSize: 10,
    total: 0,
  });
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [editingRequest, setEditingRequest] =
    useState<MaintenanceRequestResponse | null>(null);
  const [keyword, setKeyword] = useState<string>("");

  const fetchData = (
    page: number,
    pageSize: number,
    searchKeyword?: string
  ) => {
    const params = {
      page: page - 1,
      size: pageSize,
      keyword: searchKeyword || keyword,
    };

    MaintenanceRequestApi.findAll(params)
      .then((response) => {
        setDataSource(response.content);
        setPagination({
          current: page,
          pageSize: pageSize,
          total: response.totalElements,
        });
      })
      .catch(() => {
        notify.error("Không thể tải danh sách yêu cầu bảo trì.");
      });
  };

  useEffect(() => {
    fetchData(pagination.current, pagination.pageSize);
  }, [pagination.current, pagination.pageSize]);

  const handleTableChange = (pagination: TablePaginationConfig) => {
    fetchData(pagination.current || 1, pagination.pageSize || 10);
  };

  const onSearch: SearchProps["onSearch"] = (value) => {
    setKeyword(value);
    fetchData(1, pagination.pageSize, value);
  };

  const showEditModal = (request: MaintenanceRequestResponse) => {
    setEditingRequest(request);
    setIsModalVisible(true);
  };

  const handleModalCancel = () => {
    setIsModalVisible(false);
    setEditingRequest(null);
  };

  const handleModalSaveSuccess = () => {
    fetchData(pagination.current, pagination.pageSize);
  };

  const columns: TableColumnsType<MaintenanceRequestResponse> = [
    {
      key: "id",
      title: "ID",
      dataIndex: "id",
    },
    {
      key: "roomNumber",
      title: "Tên phòng",
      dataIndex: "roomNumber",
    },
    {
      key: "requestDate",
      title: "Ngày yêu cầu",
      dataIndex: "requestDate",
      render: (date: string) =>
        date ? new Date(date).toLocaleDateString() : "N/A",
    },
    {
      key: "status",
      title: "Trạng thái",
      dataIndex: "status",
      render: (status: string) => {
        switch (status) {
        case MaintenanceRequestRequestStatusEnum.PENDING:
          return <span style={{ color: "orange" }}>Đang chờ</span>;
        case MaintenanceRequestRequestStatusEnum.IN_PROGRESS:
          return <span style={{ color: "blue" }}>Đang xử lý</span>;
        case MaintenanceRequestRequestStatusEnum.COMPLETED:
          return <span style={{ color: "green" }}>Hoàn thành</span>;
        case MaintenanceRequestRequestStatusEnum.CANCELLED:
          return <span style={{ color: "red" }}>Đã hủy</span>;
        }
      },
    },
    {
      key: "decision",
      title: "Mô tả",
      dataIndex: "decision",
    },
    {
      key: "totalFee",
      title: "Tổng phí",
      dataIndex: "totalFee",
      render: (fee: number | undefined) =>
        fee?.toLocaleString("vi-VN") || "N/A",
    },
    {
      key: "requestDoneDate",
      title: "Ngày hoàn thành",
      dataIndex: "requestDoneDate",
      render: (date: string | undefined) =>
        date ? new Date(date).toLocaleDateString() : "N/A",
    },
    {
      key: "action",
      title: "Hành động",
      render: (_, record) => (
        <div className="flex justify-center">
          <Button
            disabled={record.status === "COMPLETED"}
            icon={<AiOutlineEdit />}
            className="text-blue-500 hover:text-blue-700"
            onClick={() => showEditModal(record)}
          >
            Cập nhật
          </Button>
        </div>
      ),
    },
  ];

  return (
    <>
      <div className="max-w-screen-xl mb-10">
        <Breadcrumb items={listLinkInBreadcrumb} />
      </div>
      <div className="flex justify-between items-center mt-2 mb-4">
        <Title level={3} className="text-2xl font-bold">
          Quản lý yêu cầu bảo trì
        </Title>
        <Input.Search
          placeholder="Tìm kiếm theo phòng, trạng thái, mô tả..."
          onSearch={onSearch}
          className="max-w-md"
          allowClear
        />
      </div>
      <div className="mt-2">
        <Table
          dataSource={dataSource}
          columns={columns}
          rowKey="id"
          pagination={pagination}
          onChange={handleTableChange}
        />
      </div>
      <MaintenanceRequestEditModal
        visible={isModalVisible}
        onCancel={handleModalCancel}
        onSaveSuccess={handleModalSaveSuccess}
        initialData={editingRequest}
      />
    </>
  );
};

export default MaintenanceRequestScreen;
