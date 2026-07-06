// CheckoutRequestModal.tsx (Improved UI)

import React, { useState, useEffect } from "react";
import { Modal, Button, message, Typography, Input, Form, Space } from "antd";
import { useNotifi } from "@/contexts/NotificationContext";
import { LogoutOutlined, InfoCircleOutlined } from "@ant-design/icons";
import { CheckoutRequestRequest } from "@/models/request/CheckoutRequestRequest";
import { CheckoutRequestApi } from "@/apis/apps/CheckoutRequestApi";
import { notify } from "@/utils/notification";

const { TextArea } = Input;
const { Text, Paragraph, Title } = Typography;

interface CheckoutRequestModalProps {
  visible: boolean;
  onClose: () => void;
  roomId: number | null;
}

const CheckoutRequestModal: React.FC<CheckoutRequestModalProps> = ({
  visible,
  onClose,
  roomId,
}) => {
  const [form] = Form.useForm();

  useEffect(() => {
    if (!visible) {
      form.resetFields();
    }
  }, [visible, form]);

  const handleCheckout = (values: { reason: string }) => {
    if (!roomId) {
      notify.error("Mã phòng không hợp lệ.");
      return;
    }

    const payload: CheckoutRequestRequest = {
      roomId,
      reason: values.reason.trim(),
    };
    CheckoutRequestApi.create(payload).then(() => {
      notify.success("Yêu cầu trả phòng đã được gửi thành công.");
      onClose();
    });
  };

  return (
    <Modal
      open={visible}
      onCancel={onClose}
      title={
        <Space align="center">
          <LogoutOutlined />
          <Text strong>Yêu cầu trả phòng</Text>
        </Space>
      }
      footer={[
        <Button key="back" onClick={onClose}>
          Hủy
        </Button>,
        <Button key="submit" type="primary" onClick={() => form.submit()}>
          Gửi yêu cầu
        </Button>,
      ]}
      width={1000}
      destroyOnClose
    >
      <Space direction="vertical" size="middle" className="w-full">
        <Paragraph type="secondary">
          <InfoCircleOutlined className="mr-2" />
          Vui lòng nêu rõ lý do bạn muốn trả phòng. Yêu cầu của bạn sẽ được gửi
          đến quản lý để xem xét.
        </Paragraph>
        <Form
          form={form}
          layout="vertical"
          onFinish={handleCheckout}
          requiredMark="optional"
        >
          <Form.Item
            name="reason"
            label={<Text strong>Lý do trả phòng</Text>}
            rules={[
              { required: true, message: "Vui lòng nhập lý do trả phòng." },
              { min: 10, message: "Lý do cần ít nhất 10 ký tự." },
            ]}
          >
            <TextArea
              rows={4}
              placeholder="Nhập lý do chi tiết..."
              showCount
              maxLength={500}
            />
          </Form.Item>
        </Form>
      </Space>
    </Modal>
  );
};

export default CheckoutRequestModal;
