import React, { useCallback, useEffect, useState, useMemo } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { ContractResponse } from "@/models/response/ContractResponse";
import {
  Table,
  Descriptions,
  Button,
  Typography,
  Form,
  Input,
  Select,
  Modal,
  message,
  Card,
  Space,
  Tag,
} from "antd";
import type { TableColumnsType } from "antd";
import AddMemberModal from "./AddMemberModal";
import { ServiceRoomStatusEnum } from "@/enums/ServiceRoomStatusEnum";
import { ArrowLeftOutlined } from "@ant-design/icons";
import { ContractStatusEnum } from "@/enums/ContractStatusEnum";
import { MaintenanceRequestApi } from "@/apis/apps/MaintenanceRequestApi ";
import { ContractApi } from "@/apis/apps/ContractApi";
import { notify } from "@/utils/notification";
import { formatDateToVietnamese } from "@/utils/dateUtils";
import { formatCurrency } from "@/utils/currencyUtils";

const ServiceStatusTag: React.FC<{ status?: ServiceRoomStatusEnum }> = ({
  status,
}) => {
  let color = "default";
  let text = "Unknown";
  switch (status) {
    case ServiceRoomStatusEnum.ACTIVE:
      color = "success";
      text = "Đang hoạt động";
      break;
    case ServiceRoomStatusEnum.INACTIVE:
      color = "error";
      text = "Không hoạt động";
      break;
    case ServiceRoomStatusEnum.ERROR:
      color = "warning";
      text = "Lỗi";
      break;
    case ServiceRoomStatusEnum.UNDER_MAINTENANCE:
      color = "processing";
      text = "Đang bảo trì";
      break;
    case ServiceRoomStatusEnum.SUSPENDED:
      color = "default";
      text = "Tạm ngưng";
      break;
  }
  return <Tag color={color}>{text}</Tag>;
};
const { Title, Text } = Typography;
const { Option } = Select;

const ContractDetailPage: React.FC = () => {
  const navigate = useNavigate();
  const { id: contractIdParam } = useParams<{ id: string }>();
  const [selectedContract, setSelectedContract] =
    useState<ContractResponse | null>(null);
  const [showAddMemberModal, setShowAddMemberModal] = useState(false);
  const [showMaintenanceModal, setShowMaintenanceModal] = useState(false);
  const [maintenanceForm] = Form.useForm();

  const fetchContractDetail = useCallback(
    (id: number) => {
      ContractApi.getById(id)
        .then((contract) => setSelectedContract(contract))
        .catch((error: any) => {
          notify.error(error?.message || "Failed to load contract details.");
          navigate(-1);
        });
    },
    [navigate]
  );

  useEffect(() => {
    if (contractIdParam) {
      const parsedId = Number(contractIdParam);
      if (!isNaN(parsedId) && parsedId > 0) {
        fetchContractDetail(parsedId);
      } else {
        notify.error("Invalid Contract ID.");
        navigate(-1);
      }
    } else {
      notify.error("Contract ID not found.");
      navigate(-1);
    }
  }, [contractIdParam, fetchContractDetail, navigate]);

  const memberColumns: TableColumnsType<any> = useMemo(
    () => [
      { title: "Họ và tên", dataIndex: "fullName", key: "fullName" },
      { title: "Số điện thoại", dataIndex: "phoneNumber", key: "phoneNumber" },
      { title: "Email", dataIndex: "email", key: "email" },
    ],
    []
  );

  const serviceColumns: TableColumnsType<any> = useMemo(
    () => [
      { title: "Tên dịch vụ", dataIndex: "name", key: "name" },
      {
        title: "Trạng thái",
        dataIndex: "status",
        key: "status",
        render: (status?: ServiceRoomStatusEnum) => (
          <ServiceStatusTag status={status} />
        ),
      },
      {
        title: "Giá",
        dataIndex: "price",
        key: "price",
        align: "right",
        render: (price: number) => formatCurrency(price),
      },
    ],
    []
  );

  const onFinishMaintenance = (values: any) => {
    if (!selectedContract?.id) return;
    const payload = {
      contractId: selectedContract.id,
      serviceRoomId: maintenanceForm.getFieldValue("serviceRoomId"),
      decision: values.decision || "",
    };
    MaintenanceRequestApi.save(payload).then(() => {
      fetchContractDetail(selectedContract?.id || 0);
      notify.success("Yêu cầu bảo trì đã được gửi thành công!");
      maintenanceForm.resetFields();
      setShowMaintenanceModal(false);
    });
  };

  const handleMaintenanceModalCancel = () => {
    maintenanceForm.resetFields();
    setShowMaintenanceModal(false);
  };

  const isContractActive =
    selectedContract?.status === ContractStatusEnum.ACTIVE;

  return (
    <div className="p-4">
      <div className="flex justify-between items-center mb-4">
        <Button icon={<ArrowLeftOutlined />} onClick={() => navigate(-1)}>
          Quay lại
        </Button>
        <Title level={3} style={{ margin: 0 }}>
          Chi tiết hợp đồng - Phòng: {selectedContract?.roomNumber || "..."}
        </Title>
      </div>

      {selectedContract ? (
        <Space direction="vertical" size="large" className="w-full">
          <Card title="Thông tin cơ bản" bordered={false} className="shadow-md">
            <Descriptions
              layout="vertical"
              bordered
              column={{ xxl: 3, xl: 2, lg: 2, md: 2, sm: 1, xs: 1 }}
            >
              <Descriptions.Item label="Mã hợp đồng">
                {selectedContract.id}
              </Descriptions.Item>
              <Descriptions.Item label="Người thuê">
                {selectedContract.userName}
              </Descriptions.Item>
              <Descriptions.Item label="Số phòng">
                {selectedContract.roomNumber}
              </Descriptions.Item>
              <Descriptions.Item label="Trạng thái HĐ">
                <Tag
                  color={
                    ContractStatusEnum.ACTIVE === selectedContract.status
                      ? "success"
                      : "error"
                  }
                >
                  {selectedContract.status}
                </Tag>
              </Descriptions.Item>
              <Descriptions.Item label="Ngày bắt đầu">
                {formatDateToVietnamese(selectedContract.startDate)}
              </Descriptions.Item>
              <Descriptions.Item label="Ngày kết thúc">
                {formatDateToVietnamese(selectedContract.endDate)}
              </Descriptions.Item>
            </Descriptions>
          </Card>

          <Card
            title="Thành viên"
            bordered={false}
            className="shadow-md"
            extra={
              <Button
                type="primary"
                disabled={!isContractActive}
                onClick={() => setShowAddMemberModal(true)}
              >
                Thêm thành viên
              </Button>
            }
          >
            <Table
              dataSource={selectedContract.members}
              columns={memberColumns}
              rowKey="id"
              pagination={false}
              bordered
              scroll={{ x: "max-content" }}
              size="small"
            />
          </Card>

          <Card
            title="Dịch vụ đã đăng ký"
            bordered={false}
            className="shadow-md"
            extra={
              <Button
                type="primary"
                disabled={!isContractActive}
                onClick={() => setShowMaintenanceModal(true)}
              >
                Yêu cầu bảo trì
              </Button>
            }
          >
            <Table
              dataSource={selectedContract.services}
              columns={serviceColumns}
              rowKey="id"
              pagination={false}
              bordered
              scroll={{ x: "max-content" }}
              size="small"
            />
          </Card>
        </Space>
      ) : (
        <div className="flex justify-center items-center h-full">
          <Text type="secondary">Không tìm thấy thông tin hợp đồng.</Text>
        </div>
      )}

      {selectedContract && (
        <AddMemberModal
          open={showAddMemberModal}
          onClose={() => {
            setShowAddMemberModal(false);
            fetchContractDetail(selectedContract.id || 0);
          }}
          currentUsers={selectedContract.members}
          contractId={selectedContract.id}
        />
      )}

      <Modal
        title="Yêu cầu bảo trì dịch vụ"
        open={showMaintenanceModal}
        onCancel={handleMaintenanceModalCancel}
        footer={null}
        destroyOnClose
      >
        <Form
          form={maintenanceForm}
          onFinish={onFinishMaintenance}
          layout="vertical"
        >
          <Form.Item
            name="serviceRoomId"
            label="Chọn dịch vụ cần bảo trì"
            rules={[{ required: true, message: "Vui lòng chọn một dịch vụ" }]}
          >
            <Select placeholder="Chọn dịch vụ">
              {selectedContract?.services?.map((service) => (
                <Option key={service.id} value={service.id}>
                  {service.name}
                </Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item
            name="decision"
            label="Mô tả yêu cầu / vấn đề"
            rules={[{ required: true, message: "Vui lòng nhập mô tả" }]}
          >
            <Input.TextArea
              placeholder="Nhập mô tả yêu cầu bảo trì hoặc vấn đề gặp phải"
              rows={4}
            />
          </Form.Item>
          <Form.Item style={{ marginBottom: 0 }}>
            <Button type="primary" htmlType="submit" block loading={false}>
              Gửi yêu cầu
            </Button>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default ContractDetailPage;
