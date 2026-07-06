import { RoomApi } from "@/apis/apps/RoomApi";
import ManagementScreen from "@/components/admin/common/ManagementScreen";
import { ButtonConfig } from "@/configs/buttonConfig";
import { RoomManageAction } from "@/enums/RoomManageAction";
import { RoomStatusEnum } from "@/enums/RoomStatusEnum";
import {
  defaultRoomConditionRequest,
  RoomConditionRequest,
} from "@/models/request/RoomConditionRequest";
import { defaultRoomModel, RoomModel } from "@/models/RoomModel";
import { formatCurrency } from "@/utils/currencyUtils";
import { notify } from "@/utils/notification";
import { replaceRoomIdInUrl, RouteUrls } from "@/utils/urlUtils";
import {
  Button,
  Form,
  Input,
  InputNumber,
  Modal,
  Select,
  TableColumnsType,
} from "antd";
import { SearchProps } from "antd/es/input/Search";
import TextArea from "antd/es/input/TextArea";
import React, { useCallback, useEffect, useMemo, useState } from "react";
import {
  AiOutlineDelete,
  AiOutlineEye,
  AiOutlineUserAdd,
} from "react-icons/ai";
import { MdEdit } from "react-icons/md";
import { Link, useNavigate } from "react-router-dom";

const listLinkInBreadcrumb = [
  {
    title: <Link to="/">Dashboard</Link>,
  },
  {
    title: "Quản lý phòng trọ",
  },
];

const RoomManagementScreen: React.FC = () => {
  const [isShowModal, setIsShowModal] = useState<boolean>(false);
  const [isAddItem, setIsAddItem] = useState<boolean>(false);
  const [room, setRoom] = useState<RoomModel>(defaultRoomModel);
  const [form] = Form.useForm();
  const navigate = useNavigate();
  const [dataSource, setDataSource] = useState<RoomModel[]>([]);
  const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([]);
  const onSelectChange = (newSelectedRowKeys: React.Key[]) => {
    setSelectedRowKeys(newSelectedRowKeys);
  };

  const rowSelection = {
    selectedRowKeys,
    onChange: onSelectChange,
  };
  const hasSelected = selectedRowKeys.length > 0;

  const executeLogicWhenCallServerSuccess = (successMessage: string) => {
    notify.success(successMessage);
  };

  const fetchData = async (body: RoomConditionRequest) => {
    try {
      const response = await RoomApi.findAllWithPaging(body);
      setDataSource(response.content);
    } catch (error: any) {
      /* empty */
    }
  };

  const callApiDelete = useCallback(
    async (ids: number[], callback?: () => void) => {
      Modal.confirm({
        title: "Are you sure, you want to delete this record?",
        okText: "Yes",
        okType: "danger",
        onOk: async () => {
          try {
            await RoomApi.deleteByIdIn(ids);
            executeLogicWhenCallServerSuccess("Deleted success");
            setDataSource((prev) =>
              prev.filter((item) => !ids.includes(item.id ?? 0))
            );
            if (callback) callback();
          } catch (error: any) {
            /* empty */
          }
        },
      });
    },
    [setDataSource]
  );

  const callApiAddOrUpdate = async (valuesForm: any) => {
    const entityEdit = { ...room };
    entityEdit.length = valuesForm.length;
    entityEdit.price = valuesForm.price;
    entityEdit.width = valuesForm.width;
    entityEdit.status = valuesForm.status;
    entityEdit.note = valuesForm.note;
    entityEdit.number = valuesForm.number;
    entityEdit.id = isAddItem ? undefined : room.id;
    try {
      const response = await RoomApi.createOrUpdate(entityEdit);
      executeLogicWhenCallServerSuccess("Save success!");
      setDataSource((prev) => {
        const index = prev.findIndex((item) => item.id === response.id);
        if (index !== -1) {
          const newData = [...prev];
          newData[index] = response;
          return newData;
        }
        // Add
        return [...prev, response];
      });
      resetItem();
      setIsShowModal(false);
    } catch (error: any) {
      notify.error(error.data.message);
    }
  };

  const onEditItem = useCallback(
    (record: RoomModel) => {
      setRoom({ ...record });
      form.setFieldsValue({ ...record });
      setIsShowModal(true);
      setIsAddItem(false);
    },
    [form]
  );

  const resetItem = useCallback(() => {
    setRoom(defaultRoomModel);
    form.resetFields();
    form.setFieldValue("status", RoomStatusEnum.AVAILABLE);
  }, [form]);

  const onCreateItem = useCallback(() => {
    setIsAddItem(true);
    resetItem();
    setIsShowModal(true);
  }, [resetItem]);

  const saveOrUpdate = async () => {
    try {
      const values = await form.validateFields();
      callApiAddOrUpdate(values);
    } catch (error) {
      /* empty */
    }
  };

  const executeLogicDeletedIdIn = useCallback(() => {
    const numberIds: number[] = selectedRowKeys.map((key) => Number(key));
    callApiDelete(numberIds, () => {
      setSelectedRowKeys([]);
    });
  }, [callApiDelete, selectedRowKeys]);

  const onSearch: SearchProps["onSearch"] = (value, _e) => {
    const params = { ...defaultRoomConditionRequest, keyword: value };
    fetchData(params);
  };

  useEffect(() => {
    fetchData(defaultRoomConditionRequest);
  }, []);

  const buttonConfigs: ButtonConfig[] = useMemo(
    () => [
      {
        type: "primary",
        label: "Add New",
        onClick: () => onCreateItem(),
      },
      {
        type: "primary",
        label: "Deleted Rows",
        onClick: () => executeLogicDeletedIdIn(),
        danger: true,
        disabled: !hasSelected,
      },
    ],
    [executeLogicDeletedIdIn, hasSelected, onCreateItem]
  );

  const columns: TableColumnsType<RoomModel> = useMemo(
    () => [
      {
        key: "1",
        title: "Mã phòng",
        dataIndex: "id",
      },
      {
        key: "2",
        title: "Tên phòng",
        dataIndex: "number",
      },
      {
        key: "2",
        title: "Chiều dài",
        dataIndex: "length",
      },
      {
        key: "3",
        title: "Chiều rộng",
        dataIndex: "width",
      },
      {
        key: "4",
        title: "Trạng thái phòng",
        dataIndex: "status",
        render: (status: RoomStatusEnum) => (
          <>{RoomStatusEnum.RENTED === status ? "Đã cho thuê" : "Trống"}</>
        ),
      },
      {
        key: "5",
        title: "Giá phòng",
        dataIndex: "price",
        render: (price: number) => <>{formatCurrency(price)}</>,
      },
      {
        key: "5",
        title: "Ghi chú",
        dataIndex: "note",
      },
      {
        key: "6",
        title: "Hành động",
        render: (text: string, record: RoomModel) => (
          <div className="flex space-x-2">
            <Button
              className="flex items-center"
              icon={<MdEdit />}
              onClick={() => {
                onEditItem(record);
              }}
            />
            {RoomStatusEnum.AVAILABLE === record.status && (
              <Button
                danger
                className="flex items-center"
                icon={<AiOutlineDelete />}
                onClick={() => {
                  callApiDelete([record.id ?? 0], () => {
                    setSelectedRowKeys((prev) =>
                      prev.filter((id) => record.id !== id)
                    );
                  });
                }}
              />
            )}
            {RoomStatusEnum.AVAILABLE === record.status && (
              <Button
                className="flex items-center"
                icon={<AiOutlineUserAdd />}
                onClick={() => {
                  navigate(
                    replaceRoomIdInUrl(
                      RouteUrls.addUserToRooms,
                      record.id ?? 0,
                      RoomManageAction.CREATE
                    )
                  );
                }}
              ></Button>
            )}
            {RoomStatusEnum.RENTED === record.status && (
              <Button
                className="flex items-center"
                icon={<AiOutlineEye />}
                onClick={() => {
                  navigate(
                    replaceRoomIdInUrl(
                      RouteUrls.addUserToRooms,
                      record.id ?? 0,
                      RoomManageAction.VIEW
                    )
                  );
                }}
              ></Button>
            )}
          </div>
        ),
      },
    ],
    [callApiDelete, navigate, onEditItem]
  );

  const handleChange = (value: string | string[]) => {
    setRoom((prev) => ({
      ...prev,
      status: value as RoomStatusEnum,
    }));
  };

  return (
    <main className="w-full">
      <ManagementScreen
        itemLinks={listLinkInBreadcrumb}
        tableProps={{
          rowKey: "id", // for select multi
          rowSelection: rowSelection,
          columns: columns,
          dataSource: dataSource,
          pagination: {
            pageSize: 10,
          },
        }}
        buttonConfigs={buttonConfigs}
        onSearchItem={onSearch}
      ></ManagementScreen>

      <Modal
        title={isAddItem ? "Add Item" : "Edit Item"}
        visible={isShowModal}
        okText="Save"
        onCancel={() => {
          resetItem();
          setIsShowModal(false);
        }}
        onOk={saveOrUpdate}
      >
        <Form layout="vertical" form={form} name="form_in_modal">
          <Form.Item name="number" label="Tên phòng">
            <Input placeholder="Nhập tên phòng" className="w-full" />
          </Form.Item>

          <Form.Item name="length" label="Chiều dài">
            <InputNumber min={1} max={999} style={{ width: "100%" }} />
          </Form.Item>

          <Form.Item name="width" label="Chiều rộng">
            <InputNumber min={1} max={999} style={{ width: "100%" }} />
          </Form.Item>

          <Form.Item name="price" label="Giá phòng">
            <InputNumber min={0} style={{ width: "100%" }} />
          </Form.Item>

          <Form.Item name="status" label="Trạng thái phòng">
            <Select
              defaultValue={room.status}
              onChange={handleChange}
              options={[
                { value: RoomStatusEnum.RENTED, label: "Đã cho thuê" },
                { value: RoomStatusEnum.AVAILABLE, label: "Còn trống" },
              ]}
              className="w-full"
            />
          </Form.Item>

          <Form.Item name="note" label="Ghi chú">
            <TextArea rows={4} className="w-full" />
          </Form.Item>
        </Form>
      </Modal>
    </main>
  );
};

export default RoomManagementScreen;
