import { ContractApi } from "@/apis/apps/ContractApi";
import { RoomApi } from "@/apis/apps/RoomApi";
import { ServiceApi } from "@/apis/apps/ServiceApi";
import { RoomManageAction } from "@/enums/RoomManageAction";
import { ContractModel, initialContract } from "@/models/ContractModel";
import { ContractInformationRequest } from "@/models/request/ContractInformationRequest";
import {
  defaultPagingQueryConditionRequest,
  PagingQueryConditionRequest,
} from "@/models/request/PagingQueryConditionRequest";
import { ServiceModel } from "@/models/ServiceModel";
import { defaultUser, UserModel } from "@/models/UserModel";
import { notify } from "@/utils/notification";
import { RouteUrls } from "@/utils/urlUtils";
import {
  Breadcrumb,
  Button,
  Divider,
  Flex,
  Form,
  InputNumber,
  Table,
  TableColumnsType,
  Tabs,
  TabsProps,
} from "antd";
import { pick } from "lodash";
import React, {
  useCallback,
  useEffect,
  useMemo,
  useRef,
  useState,
} from "react";
import {
  Link,
  useNavigate,
  useParams,
  useSearchParams,
} from "react-router-dom";
import ContractForm, { ContractFormRef } from "./ContractForm";
import MemberListForm, { MemberListFormRef } from "./MemberListForm";
import UserForm from "./UserForm";

export const RoomUserManagementScreen: React.FC = () => {
  const navigate = useNavigate();
  const [formUser] = Form.useForm<UserModel>();
  const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([]);
  const memberFormRef = useRef<MemberListFormRef>(null);
  const contractFormRef = useRef<ContractFormRef>(null);
  const { id } = useParams<{ id: string }>();
  const roomId = Number(id);
  const [searchParams] = useSearchParams();
  const action = searchParams.get("action")
    ? searchParams.get("action")
    : RoomManageAction.VIEW;
  const isCreate = RoomManageAction.CREATE === action;
  const isView = RoomManageAction.VIEW === action;
  const [members, setMembers] = useState<UserModel[]>([]);
  const [keyMember, setKeyMember] = useState<UserModel>(defaultUser);
  const listLinkInBreadcrumb = useMemo(() => {
    return [
      {
        title: <Link to="/">Dashboard</Link>,
      },
      {
        title: isView ? "Chi tiết hợp đồng" : "Thêm khách hàng thuê phòng",
      },
    ];
  }, [isView]);
  const [contractValue, setContractValue] = useState<ContractModel>({
    ...initialContract,
    roomId: roomId,
  });
  const [serviceOfRooms, setServiceOfRooms] = useState<ServiceModel[]>([]);
  const getRoomById = useCallback(async (roomId: number) => {
    try {
      const response = await RoomApi.getById(roomId);
      setContractValue((prev) => ({
        ...prev,
        monthlyRent: response.price,
      }));
    } catch (error: any) {
      // handle error if needed
    }
  }, []);

  useEffect(() => {
    if (roomId > 0) getRoomById(roomId);
  }, [getRoomById, roomId]);

  const fetchContractInfo = useCallback(async () => {
    try {
      const response = await ContractApi.getContractInfo(roomId);
      const members = response.members;
      const services = response.services;
      setContractValue((prev) => ({
        ...prev,
        ...pick(response, ["startDate", "endDate", "deposit", "monthlyRent"]),
      }));
      const keyMember = members.find((member) => member.id === response.userId);
      setMembers(members);
      const selectedServiceIds = services.map((s: any) => s.id) || [];
      setSelectedRowKeys(selectedServiceIds);

      const existingServicesMap = new Map(
        services.map((item) => [item.id, item])
      );

      // Chỉ giữ lại các item trong response có id tồn tại trong services
      const filteredContent = services.filter((item: any) =>
        existingServicesMap.has(item.id)
      );

      const updatedContent = filteredContent.map((item: any) => {
        const existing = existingServicesMap.get(item.id);
        return {
          ...item,
          quantity: existing?.quantity ?? item.quantity,
          price: existing?.price ?? item.price,
        };
      });
      setServiceOfRooms(updatedContent);
      setKeyMember(keyMember ?? defaultUser);
    } catch (error) {
      /* empty */
    }
  }, [roomId]);
  useEffect(() => {
    if (!isCreate) fetchContractInfo();
  }, [fetchContractInfo, isCreate]);
  const onSelectChange = (newSelectedRowKeys: React.Key[]) => {
    setSelectedRowKeys(newSelectedRowKeys);
  };

  const fetchServiceData = useCallback(
    async (body: PagingQueryConditionRequest) => {
      try {
        const response = await ServiceApi.findAllWithPaging(body);
        setServiceOfRooms(response.content);
      } catch (error: any) {
        // handle error if needed
      }
    },
    []
  );

  const rowSelection = {
    selectedRowKeys,
    onChange: onSelectChange,
  };
  const onSave = useCallback(async () => {
    try {
      const userRoom = await formUser.validateFields();
      userRoom.isRoomRepresentative = true;
      const members = memberFormRef.current?.getMembers() || [];
      const contract = await contractFormRef.current?.getContractData();
      const allUser = [...members, userRoom].map((member) => ({
        ...member,
        fullName: member.username,
      }));
      const selectedServices = serviceOfRooms.filter((service) =>
        selectedRowKeys.includes(service.id ?? 0)
      );
      const contractInfor: ContractInformationRequest = {
        memberOfRooms: allUser,
        roomId: roomId,
        serviceOfRooms: selectedServices,
        contract: contract!,
      };
      ContractApi.createContract(contractInfor);
      notify.success("Tạo hợp đồng thành công");
      window.location.href = RouteUrls.manageRooms;
    } catch (error) {
      notify.error("Vui lòng nhập đầy đủ thông tin");
    }
  }, [formUser, roomId, selectedRowKeys, serviceOfRooms]);

  const handleInputChange = useCallback(
    (value: number | null, recordId: number, field: "price" | "quantity") => {
      const newData = serviceOfRooms.map((item) => {
        if (item.id === recordId) {
          return {
            ...item,
            [field]: value ?? 0,
          };
        }
        return item;
      });
      setServiceOfRooms(newData);
    },
    [serviceOfRooms]
  );

  const columns: TableColumnsType<ServiceModel> = useMemo(
    () => [
      {
        key: "1",
        title: "Tên dịch vụ",
        dataIndex: "name",
      },
      {
        key: "2",
        title: "Giá dịch vụ",
        dataIndex: "price",
        render: (value, record: ServiceModel) => {
          return (
            <InputNumber
              disabled={isView}
              min={0}
              value={record.price}
              onChange={(value) =>
                handleInputChange(value, record.id ?? 0, "price")
              }
              formatter={(val) =>
                `${val}`.replace(/\B(?=(\d{3})+(?!\d))/g, ",")
              }
              parser={(val) => parseInt(val?.replace(/,/g, "") || "0")}
            />
          );
        },
      },
      {
        key: "3",
        title: "Số lượng",
        dataIndex: "quantity",
        render: (value, record: ServiceModel) => (
          <InputNumber
            disabled={isView}
            min={0}
            value={record?.quantity ?? 1}
            onChange={(value) =>
              handleInputChange(value, record.id ?? 0, "quantity")
            }
          />
        ),
      },
    ],
    [handleInputChange, isView]
  );

  const items: TabsProps["items"] = [
    {
      key: "1",
      label: "Thông tin khách thuê",
      children: (
        <div className="w-100">
          <UserForm
            form={formUser}
            isViewContract={isView}
            initialValue={keyMember}
          ></UserForm>
        </div>
      ),
    },
    {
      key: "2",
      label: "Dịch vụ",
      children: (
        <Table
          rowKey="id"
          rowSelection={isView ? undefined : rowSelection}
          columns={columns}
          dataSource={serviceOfRooms}
          pagination={{
            pageSize: 10,
          }}
        ></Table>
      ),
    },
    {
      key: "3",
      label: "Thành viên",
      children: (
        <>
          {" "}
          <MemberListForm
            ref={memberFormRef}
            initialMembers={members}
            isViewContract={isView}
          />
        </>
      ),
    },
    {
      key: "4",
      label: "Hợp đồng",
      children: (
        <div className="w-100">
          <ContractForm
            ref={contractFormRef}
            initialValues={contractValue}
            isViewContract={isView}
          ></ContractForm>
        </div>
      ),
    },
  ];

  useEffect(() => {
    fetchServiceData(defaultPagingQueryConditionRequest);
  }, [fetchServiceData]);

  return (
    <main className="w-full">
      <div className="max-w-screen-xl">
        <Breadcrumb items={listLinkInBreadcrumb} />
      </div>
      <div className="main">
        <div className="flex justify-end">
          <Flex gap="small">
            {!isView && (
              <Button type="primary" onClick={onSave}>
                Lưu
              </Button>
            )}
            <Button
              onClick={() => {
                navigate(RouteUrls.manageRooms);
              }}
            >
              Quay lại
            </Button>
          </Flex>
        </div>

        <Divider />
        <div>
          <Tabs defaultActiveKey="1" items={items} />
        </div>
      </div>
    </main>
  );
};

export default RoomUserManagementScreen;
