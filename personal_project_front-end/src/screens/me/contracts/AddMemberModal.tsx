import React, { useCallback, useEffect, useState } from "react";
import { Modal, Button, Form, Space, Typography } from "antd";
import { UserModel } from "@/models/UserModel";
import { UserApi } from "@/apis/apps/UserApi";
import { ContractApi } from "@/apis/apps/ContractApi";
import { UserResponse } from "@/models/response/UserResponse";
import { notify } from "@/utils/notification";
import {
  SelectLoadMoreParams,
  SelectLoadMoreResponse,
} from "@/components/base/SelectLoadMore";
import SelectLoadMore from "@/components/base/SelectLoadMore"; // Ensure import

interface AddMemberModalProps {
  open: boolean;
  onClose: () => void;
  contractId?: number;
  currentUsers: UserModel[];
}

const { Text, Paragraph } = Typography;

const AddMemberModal: React.FC<AddMemberModalProps> = ({
  open,
  onClose,
  contractId,
  currentUsers,
}) => {
  const [selectedUsers, setSelectedUsers] = useState<string[]>([]);

  const fetchUserOptions = useCallback(
    async (
      params: SelectLoadMoreParams
    ): Promise<SelectLoadMoreResponse<UserResponse>> => {
      const apiParams = {
        pageCurrent: params.pageCurrent,
        pageSize: params.pageSize,
        keyword: params.keyword,
      };
      const response = await UserApi.getAll(apiParams);
      return {
        data: response.content,
        total: response.totalElements || 0,
      };
    },
    []
  );

  const renderUserOption = useCallback(
    (user: UserResponse | UserModel) => ({
      label: `${user.fullName} (${user.cccd})`,
      value: (user.id ?? "").toString(),
    }),
    []
  );

  useEffect(() => {
    if (open) {
      const ids = currentUsers.map((u) => (u.id ?? "").toString());
      setSelectedUsers(ids);
    } else {
      setSelectedUsers([]);
    }
  }, [open, currentUsers]);

  const handleAdd = async () => {
    if (!selectedUsers.length || !contractId) {
      notify.error("Vui lòng chọn người dùng và đảm bảo có hợp đồng hợp lệ.");
      return;
    }

    try {
      await ContractApi.addUserToContract(
        contractId,
        selectedUsers.map(Number)
      );
      notify.success("Thêm thành viên thành công!");
      onClose();
    } catch (error) {
      notify.error("Có lỗi xảy ra khi thêm thành viên.");
    }
  };

  return (
    <Modal
      title={
        <Space align="center">
          <Text strong>Thêm thành viên vào hợp đồng</Text>
        </Space>
      }
      open={open}
      onCancel={onClose}
      footer={null}
      width={1000}
    >
      <Paragraph type="secondary">
        Tìm kiếm và chọn người dùng bạn muốn thêm vào hợp đồng này. Những người
        dùng hiện tại đã được chọn sẵn.
      </Paragraph>
      <Form layout="vertical">
        <Form.Item label="Chọn người dùng" required>
          <SelectLoadMore
            fetchOptions={fetchUserOptions}
            renderOption={renderUserOption}
            value={selectedUsers}
            onChange={(value: string | string[]) =>
              setSelectedUsers(Array.isArray(value) ? value : [value])
            }
            placeholder="Tìm kiếm theo tên, email, hoặc CCCD..."
            mode="multiple"
            initialData={currentUsers as UserResponse[]}
            selectProps={{
              size: "large",
              allowClear: true,
              className: "w-full",
              maxTagCount: "responsive",
            }}
          />
        </Form.Item>
        <Form.Item>
          <div className="flex justify-end gap-2">
            <Button onClick={onClose}>Hủy</Button>
            <Button
              type="primary"
              onClick={handleAdd}
              disabled={selectedUsers.length === 0}
            >
              Thêm thành viên
            </Button>
          </div>
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default AddMemberModal;
