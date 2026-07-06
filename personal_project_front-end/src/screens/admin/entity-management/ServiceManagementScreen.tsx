import { ServiceApi } from "@/apis/apps/ServiceApi";
import ManagementScreen from "@/components/admin/common/ManagementScreen";
import { ButtonConfig } from "@/configs/buttonConfig";
import { renderServiceType, ServiceTypeEnum } from "@/enums/ServiceTypeEnum";
import {
  defaultPagingQueryConditionRequest,
  PagingQueryConditionRequest,
} from "@/models/request/PagingQueryConditionRequest";
import { defaultServiceModel, ServiceModel } from "@/models/ServiceModel";
import { formatCurrency } from "@/utils/currencyUtils";
import { notify } from "@/utils/notification";
import {
  Button,
  Form,
  Input,
  InputNumber,
  Modal,
  Select,
  Switch,
  TableColumnsType,
} from "antd";
import { SearchProps } from "antd/es/input/Search";
import React, { useCallback, useEffect, useMemo, useState } from "react";
import { AiOutlineDelete } from "react-icons/ai";
import { MdEdit } from "react-icons/md";
import { Link } from "react-router-dom";

const listLinkInBreadcrumb = [
  {
    title: <Link to="/">Dashboard</Link>,
  },
  {
    title: "Quản lý dịch vụ",
  },
];

const ServiceManagementScreen: React.FC = () => {
  const [isShowModal, setIsShowModal] = useState<boolean>(false);
  const [isAddItem, setIsAddItem] = useState<boolean>(false);
  const [service, setService] = useState<ServiceModel>(defaultServiceModel);
  const [form] = Form.useForm();

  const [dataSource, setDataSource] = useState<ServiceModel[]>([]);

  const fetchData = useCallback(
    async (body: PagingQueryConditionRequest) => {
      try {
        const response = await ServiceApi.findAllWithPaging(body);
        setDataSource(response.content);
      } catch (error: any) {
        notify.error(error.data.message);
      }
    },
    [setDataSource]
  );

  const resetItem = useCallback(() => {
    setService(defaultServiceModel);
    form.resetFields();
    form.setFieldsValue(defaultServiceModel); // Set default values in form
  }, [form]);

  const callApiAddOrUpdate = useCallback(
    async (valuesForm: ServiceModel) => {
      const entityToSave = { ...service, ...valuesForm };
      try {
        const response = await ServiceApi.createOrUpdate(entityToSave);
        notify.success("Save success!");
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
    },
    [service, setDataSource, resetItem, setIsShowModal]
  );

  const onEditItem = useCallback(
    (record: ServiceModel) => {
      setService({ ...record });
      form.setFieldsValue({ ...record });
      setIsShowModal(true);
      setIsAddItem(false);
    },
    [form, setService]
  );

  const onCreateItem = useCallback(() => {
    setIsAddItem(true);
    resetItem();
    setIsShowModal(true);
  }, [resetItem]);

  const saveOrUpdate = () => {
    form.validateFields().then((values) => {
      callApiAddOrUpdate(values);
    });
  };

  const callApiDelete = useCallback(
    async (id: number, callback?: () => void) => {
      Modal.confirm({
        title: "Are you sure, you want to delete this record?",
        okText: "Yes",
        onOk: async () => {
          try {
            await ServiceApi.deleteById(id);
            notify.success("Deleted success");
            setDataSource((prev) => prev.filter((item) => item.id !== id));
            if (callback) callback();
          } catch (error: any) {
            notify.error(error.message);
          }
        },
      });
    },
    []
  );

  const onSearch: SearchProps["onSearch"] = (value, _e) => {
    const params = { ...defaultPagingQueryConditionRequest, keyword: value };
    fetchData(params);
  };

  useEffect(() => {
    fetchData(defaultPagingQueryConditionRequest);
  }, [fetchData]);

  const buttonConfigs: ButtonConfig[] = useMemo(
    () => [
      {
        type: "primary",
        label: "Add New",
        onClick: () => onCreateItem(),
      },
    ],
    [onCreateItem]
  );

  const columns: TableColumnsType<ServiceModel> = useMemo(
    () => [
      {
        key: "1",
        title: "Mã dịch vụ",
        dataIndex: "id",
      },
      {
        key: "2",
        title: "Tên dịch vụ",
        dataIndex: "name",
      },
      {
        key: "3",
        title: "Giá dịch vụ",
        dataIndex: "price",
        render: (price: number) => <>{formatCurrency(price)}</>,
      },
      {
        key: "5",
        title: "Loại dịch vụ",
        dataIndex: "serviceType",
        render: (status: ServiceTypeEnum) => <>{renderServiceType(status)}</>,
      },
      {
        key: "6",
        title: "Hành động",
        render: (text: string, record: ServiceModel) => (
          <div className="flex space-x-2">
            <Button
              className="mr-3 flex items-center"
              icon={<MdEdit />}
              onClick={() => {
                onEditItem(record);
              }}
            ></Button>
            <Button
              danger
              icon={<AiOutlineDelete />}
              onClick={() => {
                callApiDelete(record.id ?? 0);
              }}
              className="flex items-center"
            ></Button>
          </div>
        ),
      },
    ],
    [callApiDelete, onEditItem]
  );

  return (
    <main className="w-full">
      <ManagementScreen
        itemLinks={listLinkInBreadcrumb}
        tableProps={{
          rowKey: "id",
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
        <Form
          layout="vertical"
          form={form}
          name="form_in_modal"
          initialValues={service}
        >
          <Form.Item
            name="name"
            label="Tên dịch vụ"
            rules={[{ required: true, message: "Vui lòng nhập tên dịch vụ!" }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="price"
            label="Giá dịch vụ"
            rules={[{ required: true, message: "Vui lòng nhập giá dịch vụ!" }]}
          >
            <InputNumber min={0} style={{ width: "100%" }} />
          </Form.Item>
          <Form.Item name="isActive" label="Hoạt động" valuePropName="checked">
            <Switch />
          </Form.Item>
          <Form.Item
            name="serviceType"
            label="Loại dịch vụ"
            rules={[{ required: true, message: "Vui lòng chọn loại dịch vụ!" }]}
          >
            <Select>
              {Object.values(ServiceTypeEnum).map((type) => (
                <Select.Option key={type} value={type}>
                  {renderServiceType(type)}
                </Select.Option>
              ))}
            </Select>
          </Form.Item>
        </Form>
      </Modal>
    </main>
  );
};

export default ServiceManagementScreen;
