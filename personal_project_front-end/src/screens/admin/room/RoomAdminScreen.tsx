import React, { useState, useEffect, useCallback, useMemo } from "react";
import { RoomApi } from "@/apis/apps/RoomApi";
import { RoomModel } from "@/models/RoomModel";
import { PageResponse } from "@/models/response/PageResponse";
import ManagementScreen from "@/components/admin/common/ManagementScreen";
import { Button, Modal, Form, Input, TableColumnsType, Select } from "antd";
import { SearchProps } from "antd/es/input/Search";
import { Link } from "react-router-dom";
import { notify } from "@/utils/notification";
import { ButtonConfig } from "@/configs/buttonConfig";
import { AiOutlineDelete } from "react-icons/ai";
import { MdEdit } from "react-icons/md";

const listLinkInBreadcrumb = [
  {
    title: <Link to="/">Trang chủ</Link>,
  },
  {
    title: "Quản lý phòng trọ",
  },
];

type RoomAdminScreenProps = Record<string, never>;

const RoomAdminScreen: React.FC<RoomAdminScreenProps> = () => {
  const [rooms, setRooms] = useState<RoomModel[]>([]);
  const [totalItems, setTotalItems] = useState<number>(0);
  const [isShowModal, setIsShowModal] = useState<boolean>(false);
  const [isAddItem, setIsAddItem] = useState<boolean>(false);
  const [selectedRoom, setSelectedRoom] = useState<RoomModel | null>(null);
  const [form] = Form.useForm();
  const [paging, setPaging] = useState({
    page: 1,
    pageSize: 10,
  });
  const [keyword, setKeyword] = useState("");

  const fetchData = useCallback(
    async (params: any) => {
      RoomApi.findAllWithPaging(params).then((response: PageResponse<RoomModel>) => {
        setRooms(response.content);
        setTotalItems(response.totalElements);
      });
    },
    [setRooms, setTotalItems]
  );

  useEffect(() => {
    fetchData({
      page: paging.page,
      pageSize: paging.pageSize,
      keyword: keyword,
    });
  }, [paging, keyword, fetchData]);

  const onCreateItem = useCallback(() => {
    setIsAddItem(true);
    setSelectedRoom(null);
    form.resetFields();
    setIsShowModal(true);
  }, [form, setIsAddItem, setSelectedRoom, setIsShowModal]);

  const onEditItem = useCallback(
    (record: RoomModel) => {
      setIsAddItem(false);
      setSelectedRoom(record);
      form.setFieldsValue(record);
      setIsShowModal(true);
    },
    [form, setIsAddItem, setSelectedRoom, setIsShowModal]
  );

  const onDeleteItem = useCallback(
    async (id: number) => {
      Modal.confirm({
        title: "Bạn có chắc chắn muốn xóa phòng trọ này?",
        okText: "Có",
        cancelText: "Hủy",
        okType: "danger",
        onOk: async () => {
          RoomApi.deleteByIdIn([id]).then(() => {
            notify.success("Xóa phòng trọ thành công");
            fetchData({
              page: paging.page,
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
    setPaging({ ...paging, page: 1 });
  };

  const handleSave = async () => {
    form.validateFields().then((values) => {
      if (isAddItem) {
        RoomApi.createOrUpdate(values).then(() => {
          notify.success("Tạo phòng trọ thành công");
          setIsShowModal(false);
          fetchData({
            page: paging.page,
            pageSize: paging.pageSize,
            keyword: keyword,
          });
        });
      } else {
        if (selectedRoom) {
          RoomApi.createOrUpdate({ ...selectedRoom, ...values }).then(() => {
            notify.success("Cập nhật phòng trọ thành công");
            setIsShowModal(false);
            fetchData({
              page: paging.page,
              pageSize: paging.pageSize,
              keyword: keyword,
            });
          });
        }
      }
    });
  };

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

  const columns: TableColumnsType<RoomModel> = useMemo(
    () => [
      {
        title: "ID",
        dataIndex: "id",
        key: "id",
      },
      {
        title: "Tên phòng",
        dataIndex: "roomName",
        key: "roomName",
      },
      {
        title: "Mã phòng",
        dataIndex: "roomNumber",
        key: "roomNumber",
      },
      {
        title: "Giá thuê",
        dataIndex: "rentalPrice",
        key: "rentalPrice",
        render: (text: number) => text?.toLocaleString("vi-VN"),
      },
      {
        title: "Mô tả",
        dataIndex: "description",
        key: "description",
      },
      {
        title: "Hành động",
        key: "actions",
        render: (text: string, record: RoomModel) => (
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
          </div>
        ),
      },
    ],
    [onEditItem, onDeleteItem]
  );

  return (
    <main className="w-full">
      <ManagementScreen
        itemLinks={listLinkInBreadcrumb}
        tableProps={{
          rowKey: "id",
          columns: columns,
          dataSource: rooms,
          pagination: {
            current: paging.page,
            pageSize: paging.pageSize,
            total: totalItems,
            onChange: (page: number, pageSize: number) => {
              setPaging({ page, pageSize });
            },
          },
        }}
        buttonConfigs={buttonConfigs}
        onSearchItem={onSearch}
      />
      <Modal
        title={isAddItem ? "Thêm phòng trọ" : "Sửa phòng trọ"}
        visible={isShowModal}
        onOk={handleSave}
        onCancel={() => setIsShowModal(false)}
        footer={[
          <Button key="cancel" onClick={() => setIsShowModal(false)}>
            Hủy
          </Button>,
          <Button key="save" type="primary" onClick={handleSave}>
            Lưu
          </Button>,
        ]}
      >
        <Form form={form} layout="vertical">
          <Form.Item
            name="roomName"
            label="Tên phòng"
            rules={[{ required: true, message: "Vui lòng nhập tên phòng!" }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="roomNumber"
            label="Mã phòng"
            rules={[{ required: true, message: "Vui lòng nhập mã phòng!" }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="rentalPrice"
            label="Giá thuê"
            rules={[{ required: true, message: "Vui lòng nhập giá thuê!" }]}
          >
            <Input type="number" />
          </Form.Item>
          <Form.Item
            name="description"
            label="Mô tả"
            rules={[{ required: true, message: "Vui lòng nhập mô tả!" }]}
          >
            <Input />
          </Form.Item>
        </Form>
      </Modal>
    </main>
  );
};

export default RoomAdminScreen;