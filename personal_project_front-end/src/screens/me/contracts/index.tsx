import React, { useEffect, useState, useMemo } from "react";
import { Input, Table, Typography } from "antd";
import { BiBoltCircle, BiCommentDetail, BiLogOutCircle } from "react-icons/bi";
import { useNavigate } from "react-router-dom";
import { ContractStatusEnum } from "@/enums/ContractStatusEnum";
import CheckoutRequestModal from "./CheckoutRequestModal";
import { ContractApi } from "@/apis/apps/ContractApi";
import { ContractResponse } from "@/models/response/ContractResponse";

const { Title } = Typography;

const ContractPage: React.FC = () => {
  const [contracts, setContracts] = useState<ContractResponse[]>([]);
  const navigate = useNavigate();
  const [checkoutModalVisible, setCheckoutModalVisible] = useState(false);
  const [selectedRoomId, setSelectedRoomId] = useState<number | null>(null);
  const [searchText, setSearchText] = useState<string>("");

  const openCheckoutModal = React.useCallback((roomId: number) => {
    setSelectedRoomId(roomId);
    setCheckoutModalVisible(true);
  }, []);

  const fetchContractData = () => {
    ContractApi.getMyContracts().then((contractFetch) => {
      setContracts(contractFetch);
    });
  };

  useEffect(() => {
    fetchContractData();
  }, []);

  const filteredContracts = useMemo(() => {
    const lowerCaseSearchText = searchText.toLowerCase();
    return contracts.filter((contract) => {
      // Adjust fields to search based on your data structure
      return (
        (contract.id?.toString() ?? "")
          .toLowerCase()
          .includes(lowerCaseSearchText) ||
        (contract.roomNumber?.toString() ?? "")
          .toLowerCase()
          .includes(lowerCaseSearchText) ||
        (contract.status ?? "").toLowerCase().includes(lowerCaseSearchText) ||
        (contract.startDate ?? "")
          .toLowerCase()
          .includes(lowerCaseSearchText) ||
        (contract.endDate ?? "").toLowerCase().includes(lowerCaseSearchText)
      );
    });
  }, [searchText, contracts]);

  const columns = useMemo(
    () => [
      { title: "Mã hợp đồng", dataIndex: "id", key: "id" },
      { title: "Người thuê", dataIndex: "fullName", key: "fullName" },
      { title: "Số phòng", dataIndex: "roomNumber", key: "roomNumber" },
      { title: "Trạng thái", dataIndex: "status", key: "status" },
      { title: "Ngày bắt đầu", dataIndex: "startDate", key: "startDate" },
      { title: "Ngày kết thúc", dataIndex: "endDate", key: "endDate" },
      {
        title: "Hành động",
        key: "actions",
        render: (_: any, record: ContractResponse) => (
          <div className="flex gap-3">
            <BiCommentDetail
              className="text-blue-500 hover:text-blue-700 cursor-pointer text-xl"
              title="Chi tiết hợp đồng"
              onClick={() => navigate(`/contract/${record.id}`)}
            />

            <BiBoltCircle
              className="text-green-500 hover:text-green-700 cursor-pointer text-xl"
              title="Thông số điện nước"
              onClick={() => navigate(`/utility-index/${record.id}`)}
            />
            {record.status === ContractStatusEnum.ACTIVE && (
              <BiLogOutCircle
                className="text-red-500 hover:text-red-700 cursor-pointer text-xl"
                title="Trả phòng"
                onClick={() => openCheckoutModal(record.roomId)}
              />
            )}
          </div>
        ),
      },
    ],
    [navigate, openCheckoutModal]
  );

  return (
    <div className="bg-white shadow-lg rounded-lg p-6">
      <div className="flex justify-between items-center mb-4">
        <Title level={3}>Danh sách hợp đồng</Title>
        <Input.Search
          placeholder="Tìm kiếm hợp đồng..."
          onSearch={(value) => setSearchText(value)}
          onChange={(e) => setSearchText(e.target.value)}
          className="max-w-xs w-full"
        />
      </div>
      <Table
        dataSource={filteredContracts}
        columns={columns}
        rowKey="id"
        pagination={{ pageSize: 10 }}
        bordered
        loading={contracts.length === 0}
        rowClassName="hover:bg-gray-50 cursor-pointer"
        className="rounded-lg"
      />
      <CheckoutRequestModal
        visible={checkoutModalVisible}
        onClose={() => setCheckoutModalVisible(false)}
        roomId={selectedRoomId}
      />
    </div>
  );
};

export default ContractPage;
