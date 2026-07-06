import { UserModel } from "@/models/UserModel";
import { Button, Form, Modal, Popconfirm, Table } from "antd";
import type { ColumnsType } from "antd/es/table";
import React, { forwardRef, useImperativeHandle, useState } from "react";
import UserForm from "./UserForm";

export interface MemberListFormRef {
  getMembers: () => UserModel[];
}
interface MemberListFormProps {
  initialMembers?: UserModel[];
  isViewContract: boolean;
}

const MemberListForm = forwardRef<MemberListFormRef, MemberListFormProps>(
  ({ initialMembers = [], isViewContract }, ref) => {
    const [members, setMembers] = useState<UserModel[]>(initialMembers);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [form] = Form.useForm();

    useImperativeHandle(ref, () => ({
      getMembers: () => members,
    }));

    const handleAdd = () => {
      form.validateFields().then((values) => {
        setMembers((prev) => [...prev, values]);
        form.resetFields();
        setIsModalOpen(false);
      });
    };

    const handleDelete = (index: number) => {
      setMembers((prev) => prev.filter((_, i) => i !== index));
    };

    const columns: ColumnsType<UserModel> = [
      { title: "Họ và tên", dataIndex: "username" },
      { title: "Email", dataIndex: "email" },
      { title: "CCCD", dataIndex: "cccd" },
      { title: "SĐT", dataIndex: "phoneNumber" },
      { title: "Địa chỉ", dataIndex: "permanentAddress" },
      { title: "Biển số xe", dataIndex: "licensePlateNumber" },
      {
        title: "Hành động",
        render: (_, __, index) => (
          <Popconfirm
            title="Xóa thành viên?"
            onConfirm={() => handleDelete(index)}
          >
            <Button danger size="small" disabled={isViewContract}>
              Xóa
            </Button>
          </Popconfirm>
        ),
      },
    ];

    return (
      <>
        <Table
          dataSource={members}
          columns={columns}
          rowKey={(record, index) => `${record.cccd}-${index}`}
          pagination={false}
        />

        {!isViewContract && (
          <Button
            type="primary"
            onClick={() => setIsModalOpen(true)}
            style={{ marginTop: 16 }}
          >
            Thêm thành viên
          </Button>
        )}

        <Modal
          title="Thêm thành viên"
          open={isModalOpen}
          onCancel={() => {
            setIsModalOpen(false);
            form.resetFields();
          }}
          onOk={handleAdd}
          okText="Lưu"
          cancelText="Hủy"
        >
          <UserForm form={form} isViewContract={false} />
        </Modal>
      </>
    );
  }
);

export default MemberListForm;
