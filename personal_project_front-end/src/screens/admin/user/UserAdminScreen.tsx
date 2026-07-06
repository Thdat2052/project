import React, { useState, useEffect, useCallback, useMemo } from "react";
import { UserApi } from "@/apis/apps/UserApi";
import { AuthApi } from "@/apis/apps/AuthApi";
import { UserResponse } from "@/models/response/UserResponse";
import { PageResponse } from "@/models/response/PageResponse";
import ManagementScreen from "@/components/admin/common/ManagementScreen";
import { Button, Modal, Form, Input, TableColumnsType, Select } from "antd";
import { SearchProps } from "antd/es/input/Search";
import { Link } from "react-router-dom";
import { notify } from "@/utils/notification";
import { ButtonConfig } from "@/configs/buttonConfig";
import { RoleEnum } from "@/enums/RoleEnum";
import { AiOutlineDelete } from "react-icons/ai";
import { MdEdit } from "react-icons/md";

const listLinkInBreadcrumb = [
  {
    title: <Link to="/">Trang chủ</Link>,
  },
  {
    title: "Người dùng",
  },
];

type UserAdminScreenProps = Record<string, never>;

const UserAdminScreen: React.FC<UserAdminScreenProps> = () => {
  const [users, setUsers] = useState<UserResponse[]>([]);
  const [totalItems, setTotalItems] = useState<number>(0);
  const [isShowModal, setIsShowModal] = useState<boolean>(false);
  const [isAddItem, setIsAddItem] = useState<boolean>(false);
  const [selectedUser, setSelectedUser] = useState<UserResponse | null>(null);
  const [form] = Form.useForm();
  const [paging, setPaging] = useState({
    pageCurrent: 1,
    pageSize: 10,
  });
  const [keyword, setKeyword] = useState("");

  const fetchData = useCallback(
    (params: any) => {
      UserApi.getAll(params).then((response: PageResponse<UserResponse>) => {
        setUsers(response.content);
        setTotalItems(response.totalElements);
      });
    },
    [setUsers, setTotalItems]
  );

  useEffect(() => {
    fetchData({
      pageCurrent: paging.pageCurrent - 1,
      pageSize: paging.pageSize,
      keyword: keyword,
    });
  }, [paging, keyword, fetchData]);

  const onCreateItem = useCallback(() => {
    setIsAddItem(true);
    setSelectedUser(null);
    form.resetFields();
    setIsShowModal(true);
  }, [form, setIsAddItem, setSelectedUser, setIsShowModal]);

  const onEditItem = useCallback(
    (record: UserResponse) => {
      setIsAddItem(false);
      setSelectedUser(record);
      form.setFieldsValue(record);
      setIsShowModal(true);
    },
    [form, setIsAddItem, setSelectedUser, setIsShowModal]
  );

  const onDeleteItem = useCallback(
    async (id: number) => {
      Modal.confirm({
        title: "Bạn có chắc chắn muốn xóa người dùng này?",
        okText: "Có",
        cancelText: "Hủy",
        okType: "danger",
        onOk: async () => {
          UserApi.delete(id.toString()).then(() => {
            notify.success("Xóa người dùng thành công");
            fetchData({
              pageCurrent: paging.pageCurrent -1,
              pageSize: paging.pageSize,
              keyword: keyword,
            });
          });
        },
      });
    },
    [fetchData, paging, keyword]
  );

  const onSearch: SearchProps["onSearch"] = (value, _e) => {
    setKeyword(value);
    setPaging({ ...paging, pageCurrent: 1 });
  };

  const handleSave = async () => {
    form.validateFields().then((values) => {
      UserApi.save({ ...selectedUser, ...values }).then(() => {
        if (isAddItem) {
          notify.success("Tạo người dùng thành công");
        } else {
          notify.success("Cập nhật người dùng thành công");
        }
        setIsShowModal(false);
        fetchData({
          pageCurrent: paging.pageCurrent-1,
          pageSize: paging.pageSize,
          keyword: keyword,
        });
      });
    });
  };

  const handleResetPassword = useCallback(async (username: string) => {
    Modal.confirm({
      title: "Bạn có chắc chắn muốn đặt lại mật khẩu cho người dùng này?",
      okText: "Có",
      cancelText: "Hủy",
      okType: "danger",
      onOk: () => {
        AuthApi.resetPassword(username).then(() => {
          notify.success("Đã gửi mật khẩu mới đến người dùng");
        });
      },
    });
  }, []);

  const buttonConfigs: ButtonConfig[] = useMemo(
    () => [
      {
        type: "primary",
        label: "Thêm mới",
        onClick: () => onCreateItem(),
      },
    ],
    [onCreateItem]
  );

  const handleResetPasswordClick = useCallback(
    (username: string) => {
      handleResetPassword(username);
    },
    [handleResetPassword]
  );

  const columns: TableColumnsType<UserResponse> = useMemo(
    () => [
      {
        title: "ID",
        dataIndex: "id",
        key: "id",
      },
      {
        title: "Họ và tên",
        dataIndex: "fullName",
        key: "fullName",
      },
      {
        title: "Username",
        dataIndex: "username",
        key: "username",
      },
      {
        title: "Số điện thoại",
        dataIndex: "phoneNumber",
        key: "phoneNumber",
      },
      {
        title: "CCCD",
        dataIndex: "cccd",
        key: "cccd",
      },
      {
        title: "Địa chỉ thường trú",
        dataIndex: "permanentAddress",
        key: "permanentAddress",
      },
      {
        title: "Ngày tạo",
        dataIndex: "createdAt",
        key: "createdAt",
        render: (text: string) => {
          return new Date(text).toLocaleDateString("vi-VN");
        },
      },
      {
        title: "Biển số xe",
        dataIndex: "licensePlateNumber",
        key: "licensePlateNumber",
      },
      {
        title: "Email",
        dataIndex: "email",
        key: "email",
      },
      {
        title: "Hành động",
        key: "actions",
        render: (text: string, record: UserResponse) => (
          <div className="flex space-x-2">
            <Button
              type="primary"
              icon={<MdEdit />}
              className="flex items-center"
              onClick={() => onEditItem(record)}
            >
              Sửa
            </Button>
            <Button
              type="default"
              icon={<AiOutlineDelete />}
              className="flex items-center"
              danger
              onClick={() => record.id && onDeleteItem(record.id)}
            >
              Xóa
            </Button>
            <Button
              type="dashed"
              className="flex items-center"
              onClick={() => handleResetPasswordClick(record.username)}
            >
              Đặt lại mật khẩu
            </Button>
          </div>
        ),
      },
    ],
    [onEditItem, handleResetPasswordClick, onDeleteItem]
  );

  return (
    <main className="w-full">
      <ManagementScreen
        itemLinks={listLinkInBreadcrumb}
        tableProps={{
          rowKey: "id",
          columns: columns,
          dataSource: users,
          pagination: {
            current: paging.pageCurrent,
            pageSize: paging.pageSize,
            total: totalItems,
            onChange: (page: number, pageSize: number) => {
              setPaging({ pageCurrent: page, pageSize });
            },
          },
        }}
        buttonConfigs={buttonConfigs}
        onSearchItem={onSearch}
      />
      <Modal
        title={isAddItem ? "Thêm người dùng" : "Sửa người dùng"}
        visible={isShowModal}
        onOk={handleSave}
        onCancel={() => setIsShowModal(false)}
      >
        <Form form={form} layout="vertical">
          <Form.Item
            name="fullName"
            label="Họ và tên"
            rules={[{ required: true, message: "Vui lòng nhập họ và tên!" }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="email"
            label="Email"
            rules={[{ required: true, message: "Vui lòng nhập email!" }]}
          >
            <Input type="email" />
          </Form.Item>
          <Form.Item
            name="phoneNumber"
            label="Số điện thoại"
            rules={[
              { required: true, message: "Vui lòng nhập số điện thoại!" },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="cccd"
            label="CCCD"
            rules={[{ required: true, message: "Vui lòng nhập CCCD!" }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="permanentAddress"
            label="Địa chỉ thường trú"
            rules={[
              { required: true, message: "Vui lòng nhập địa chỉ thường trú!" },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="licensePlateNumber"
            label="Biển số xe"
            rules={[
              { required: true, message: "Vui lòng nhập biển số xe!" },
            ]}
          >
            <Input />
          </Form.Item>
          {isAddItem && (
            <>
              <Form.Item
                name="username"
                label="Tên tài khoản đăng nhập"
                rules={[
                  {
                    required: true,
                    message: "Vui lòng nhập tài khoản đăng nhập!",
                  },
                ]}
              >
                <Input />
              </Form.Item>
              <Form.Item
                name="newPassword"
                label="Mật khẩu"
                rules={[
                  {
                    required: true,
                    message: "Vui lòng nhập mật khẩu  đăng nhập!",
                  },
                ]}
              >
                <Input.Password />
              </Form.Item>
            </>
          )}
          <Form.Item
            name="roles"
            label="Vai trò"
            rules={[{ required: true, message: "Vui lòng chọn vai trò!" }]}
          >
            <Select mode="multiple">
              {Object.values(RoleEnum).map((role) => (
                <Select.Option key={role} value={role}>
                  {role}
                </Select.Option>
              ))}
            </Select>
          </Form.Item>
        </Form>
      </Modal>
    </main>
  );
};

export default UserAdminScreen;
