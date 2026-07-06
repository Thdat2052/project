import { MaintenanceRequestRequestStatusEnum } from "@/enums/MaintenanceRequestRequestStatusEnum";
import { MaintenanceRequestRequest } from "@/models/request/MaintenanceRequestRequest";
import { MaintenanceRequestResponse } from "@/models/response/MaintenanceRequestResponse";
import { MaintenanceRequestApi } from "@/apis/apps/MaintenanceRequestApi ";
import { notify } from "@/utils/notification";
import { Modal, Form, InputNumber, Select, Input } from "antd";
import React, { useEffect } from "react";

interface MaintenanceRequestEditModalProps {
  visible: boolean;
  onCancel: () => void;
  onSaveSuccess: () => void;
  initialData: MaintenanceRequestResponse | null;
}

const MaintenanceRequestEditModal: React.FC<
  MaintenanceRequestEditModalProps
> = ({ visible, onCancel, onSaveSuccess, initialData }) => {
  const [form] = Form.useForm();

  useEffect(() => {
    if (initialData) {
      form.setFieldsValue({
        ...initialData,
        totalFee: (initialData as any).totalFee || 0,
        requestDoneDate: (initialData as any).requestDoneDate,
      });
    } else {
      form.resetFields();
    }
  }, [initialData, form]);

  const handleSave = () => {
    form.validateFields().then((values) => {
      if (!initialData) return;
      const updatedRequest: MaintenanceRequestRequest = {
        id: initialData.id,
        price: values.price,
        status: values.status,
      };
      MaintenanceRequestApi.update(updatedRequest).then(() => {
        notify.success("Yêu cầu bảo trì đã được cập nhật.");
        onSaveSuccess();
        onCancel();
      });
    });
  };

  return (
    <Modal
      title="Chỉnh sửa yêu cầu bảo trì"
      visible={visible}
      onOk={handleSave}
      onCancel={onCancel}
      okText="Lưu"
      cancelText="Hủy"
    >
      <Form form={form} layout="vertical">
        <Form.Item name="id" label="ID" hidden>
          <InputNumber />
        </Form.Item>
        <Form.Item name="roomName" label="Tên phòng">
          <Input disabled />
        </Form.Item>
        <Form.Item name="decision" label="Mô tả">
          <Input.TextArea rows={4} disabled />
        </Form.Item>
        <Form.Item
          name="price"
          label="Tổng phí"
          rules={[{ required: true, message: "Vui lòng nhập tổng phí!" }]}
        >
          <InputNumber
            min={0}
            style={{ width: "100%" }}
            placeholder="Nhập tổng phí"
          />
        </Form.Item>
        <Form.Item
          name="status"
          label="Trạng thái"
          rules={[{ required: true, message: "Vui lòng chọn trạng thái!" }]}
        >
          <Select>
            {Object.values(MaintenanceRequestRequestStatusEnum).map(
              (status) => (
                <Select.Option key={status} value={status}>
                  {status === MaintenanceRequestRequestStatusEnum.PENDING &&
                    "Đang chờ"}
                  {status === MaintenanceRequestRequestStatusEnum.IN_PROGRESS &&
                    "Đang xử lý"}
                  {status === MaintenanceRequestRequestStatusEnum.COMPLETED &&
                    "Hoàn thành"}
                  {status === MaintenanceRequestRequestStatusEnum.CANCELLED &&
                    "Đã hủy"}
                </Select.Option>
              )
            )}
          </Select>
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default MaintenanceRequestEditModal;
