import { UserModel } from "@/models/UserModel";
import { Form, Input } from "antd";
import { FormInstance } from "antd/lib/form";
import React, { useEffect } from "react";

interface UserFormProps {
  form: FormInstance;
  isViewContract: boolean;
  initialValue?: Partial<UserModel>;
}

const UserForm: React.FC<UserFormProps> = ({
  form,
  isViewContract,
  initialValue,
}) => {
  useEffect(() => {
    if (initialValue) {
      form.setFieldsValue(initialValue);
    }
  }, [initialValue, form]);
  return (
    <Form
      layout="vertical"
      form={form}
      name="user_form"
      initialValues={initialValue}
    >
      <Form.Item
        name="username"
        label="Họ và tên"
        rules={[{ required: true, message: "Vui lòng nhập họ và tên" }]}
      >
        <Input disabled={isViewContract} />
      </Form.Item>

      <Form.Item
        name="email"
        label="Email"
        rules={[
          { required: true, message: "Vui lòng nhập email" },
          { type: "email", message: "Email không hợp lệ" },
        ]}
      >
        <Input disabled={isViewContract} />
      </Form.Item>

      <Form.Item
        name="cccd"
        label="CCCD"
        rules={[{ required: true, message: "Vui lòng nhập CCCD" }]}
      >
        <Input disabled={isViewContract} />
      </Form.Item>

      <Form.Item
        name="phoneNumber"
        label="Số điện thoại"
        rules={[{ required: true, message: "Vui lòng nhập số điện thoại" }]}
      >
        <Input disabled={isViewContract} />
      </Form.Item>

      <Form.Item name="permanentAddress" label="Địa chỉ">
        <Input disabled={isViewContract} />
      </Form.Item>

      <Form.Item name="licensePlateNumber" label="Biển số xe">
        <Input disabled={isViewContract} />
      </Form.Item>
    </Form>
  );
};

export default UserForm;
