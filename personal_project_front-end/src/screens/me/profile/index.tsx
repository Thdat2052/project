import React, { useEffect, useState } from "react";
import {
  Card,
  Avatar,
  Descriptions,
  Row,
  Col,
  Button,
  Form,
  Input,
  Space,
} from "antd";
import { UserResponse } from "@/models/response/UserResponse";
import { UserOutlined } from "@ant-design/icons";
import { UserApi } from "@/apis/apps/UserApi";

const EditProfile: React.FC<{
  user: UserResponse;
  onSave: () => void;
  onCancel: () => void;
}> = ({ user, onSave, onCancel }) => {
  const [form] = Form.useForm();
  const [isSaving, setIsSaving] = useState(false);

  const handleSubmit = (values: UserResponse) => {
    setIsSaving(true);
    UserApi.updateMe({ ...user, ...values })
      .then(() => {
        form.resetFields();
        onSave();
      })
      .finally(() => {
        setIsSaving(false);
      });
  };

  return (
    <Card title="Chỉnh sửa hồ sơ">
      <Form
        form={form}
        layout="vertical"
        initialValues={user}
        onFinish={handleSubmit}
        className="p-6"
      >
        <Row gutter={[16, 16]}>
          <Col xs={24} md={12}>
            <Form.Item
              name="fullName"
              label="Họ và Tên"
              rules={[
                { required: true, message: "Vui lòng nhập họ và tên đầy đủ" },
              ]}
            >
              <Input size="large" />
            </Form.Item>
          </Col>
          <Col xs={24} md={12}>
            <Form.Item
              name="email"
              label="Email"
              rules={[
                {
                  required: true,
                  type: "email",
                  message: "Vui lòng nhập địa chỉ email hợp lệ",
                },
              ]}
            >
              <Input size="large" />
            </Form.Item>
          </Col>
          <Col xs={24} md={12}>
            <Form.Item name="phoneNumber" label="Số điện thoại">
              <Input size="large" />
            </Form.Item>
          </Col>
          <Col xs={24} md={12}>
            <Form.Item name="cccd" label="Chứng minh nhân dân">
              <Input size="large" />
            </Form.Item>
          </Col>

          <Col xs={24} md={12}>
            <Form.Item name="permanentAddress" label="Địa chỉ thường trú">
              <Input size="large" />
            </Form.Item>
          </Col>
          <Col xs={24} md={12}>
            <Form.Item name="licensePlateNumber" label="Biển số xe">
              <Input size="large" />
            </Form.Item>
          </Col>
        </Row>

        <Form.Item className="mt-8 text-right">
          <Space size="middle">
            <Button
              type="primary"
              htmlType="submit"
              loading={isSaving}
              size="large"
            >
              Lưu
            </Button>
            <Button onClick={onCancel} size="large">
              Hủy
            </Button>
          </Space>
        </Form.Item>
      </Form>
    </Card>
  );
};

const ProfilePage: React.FC = () => {
  const [user, setUser] = useState<UserResponse>();
  const [isEditing, setIsEditing] = useState(false);

  const fetchUserData = () => {
    UserApi.getMe()
      .then((userFetch) => {
        setUser(userFetch);
      });
  };

  useEffect(() => {
    fetchUserData();
  }, []);

  const handleSave = () => {
    setIsEditing(false);
    fetchUserData();
  };

  const handleCancel = () => {
    setIsEditing(false);
  };

  if (!user) {
    return (
      <div className="text-center text-xl text-red-600 py-10">
        Không thể tải dữ liệu người dùng.
      </div>
    );
  }

  return (
    <div className="p-6 bg-gray-100 min-h-screen">
      {isEditing ? (
        <EditProfile user={user} onSave={handleSave} onCancel={handleCancel} />
      ) : (
        <Card title="Thông tin người dùng" className="shadow-lg rounded-lg">
          <Row gutter={[24, 24]} justify="center">
            <Col xs={24} md={8} className="flex justify-center items-start">
              <div className="flex flex-col items-center">
                <Avatar
                  icon={<UserOutlined />}
                  size={160}
                  className="border-4 border-blue-500 hover:scale-105 transition-transform duration-300 ease-in-out shadow-md"
                />
              </div>
            </Col>
            <Col xs={24} md={16}>
              <div className="flex justify-end mb-6">
                <Button
                  type="primary"
                  onClick={() => setIsEditing(true)}
                  size="large"
                >
                  Chỉnh sửa hồ sơ
                </Button>
              </div>
              <Descriptions
                title="Chi tiết thông tin cá nhân"
                layout="vertical"
                column={{ xxl: 3, xl: 3, lg: 2, md: 2, sm: 1, xs: 1 }}
                bordered
                className="text-gray-700"
              >
                <Descriptions.Item label="Họ và Tên">
                  {user.fullName}
                </Descriptions.Item>
                <Descriptions.Item label="Email">
                  {user.email}
                </Descriptions.Item>
                <Descriptions.Item label="Tên đăng nhập">
                  {user.username}
                </Descriptions.Item>
                <Descriptions.Item label="CCCD">{user.cccd}</Descriptions.Item>
                <Descriptions.Item label="Số điện thoại">
                  {user.phoneNumber}
                </Descriptions.Item>
                <Descriptions.Item label="Địa chỉ thường trú">
                  {user.permanentAddress}
                </Descriptions.Item>
                <Descriptions.Item label="Biển số xe">
                  {user.licensePlateNumber}
                </Descriptions.Item>
                <Descriptions.Item label="Ngày tạo tài khoản">
                  {user.createdAt}
                </Descriptions.Item>
              </Descriptions>
            </Col>
          </Row>
        </Card>
      )}
    </div>
  );
};

export default ProfilePage;
