import { CheckoutRequestApi } from "@/apis/apps/CheckoutRequestApi";
import ManagementScreen from "@/components/admin/common/ManagementScreen";
import {
  defaultPagingQueryConditionRequest,
  PagingQueryConditionRequest,
} from "@/models/request/PagingQueryConditionRequest";
import { CheckoutRequestResponse } from "@/models/response/CheckoutRequestResponse";
import { notify } from "@/utils/notification";
import { Button, Modal, TableColumnsType } from "antd";
import { SearchProps } from "antd/es/input/Search";
import React, { useCallback, useEffect, useMemo, useState } from "react";
import { Link } from "react-router-dom";
import { CheckoutRequestStatusEnum } from "@/enums/CheckoutRequestEnum";

const listLinkInBreadcrumb = [
  {
    title: <Link to="/admin">Dashboard</Link>,
  },
  {
    title: "Quản lý yêu cầu trả phòng",
  },
];

const CheckoutRequestManagementScreen: React.FC = () => {
  const [dataSource, setDataSource] = useState<CheckoutRequestResponse[]>([]);

  const fetchData = useCallback(
    (body: PagingQueryConditionRequest) => {
      return CheckoutRequestApi.findAll(body).then((response) => {
        setDataSource(response.content);
      });
    },
    [setDataSource]
  );

  const handleApprove = useCallback(
    (id: number) => {
      Modal.confirm({
        title: "Are you sure you want to approve this checkout request?",
        okText: "Yes",
        onOk: () => {
          return CheckoutRequestApi.approve(
            id,
            CheckoutRequestStatusEnum.COMPLETED
          ).then(() => {
            notify.success("Checkout request approved successfully!");
            fetchData(defaultPagingQueryConditionRequest);
          });
        },
      });
    },
    [fetchData]
  );

  const handleReject = useCallback(
    (id: number) => {
      Modal.confirm({
        title: "Are you sure you want to reject this checkout request?",
        okText: "Yes",
        okType: "danger",
        onOk: () => {
          return CheckoutRequestApi.approve(
            id,
            CheckoutRequestStatusEnum.CANCELLED
          ).then(() => {
            notify.success("Checkout request rejected successfully!");
            fetchData(defaultPagingQueryConditionRequest);
          });
        },
      });
    },
    [fetchData]
  );

  const onSearch: SearchProps["onSearch"] = (value, _e) => {
    const params = { ...defaultPagingQueryConditionRequest, keyword: value };
    fetchData(params);
  };

  useEffect(() => {
    fetchData(defaultPagingQueryConditionRequest);
  }, [fetchData]);

  const columns: TableColumnsType<CheckoutRequestResponse> = useMemo(
    () => [
      {
        key: "1",
        title: "ID",
        dataIndex: "id",
      },
      {
        key: "2",
        title: "Phòng",
        dataIndex: "roomName",
      },
      {
        key: "2.1",
        title: "Người thuê",
        dataIndex: "fullName",
      },
      {
        key: "3",
        title: "Ngày yêu cầu",
        dataIndex: "requestDate",
        render: (date: string) => new Date(date).toLocaleDateString(),
      },
      {
        key: "4",
        title: "Trạng thái",
        dataIndex: "status",
      },
      {
        key: "5",
        title: "Actions",
        render: (text: string, record: CheckoutRequestResponse) => (
          <div className="flex space-x-2">
            {record.status === CheckoutRequestStatusEnum.IN_PROGRESS && (
              <>
                <Button
                  type="primary"
                  onClick={() => record.id && handleApprove(record.id)}
                >
                  Approve
                </Button>
                <Button
                  danger
                  onClick={() => record.id && handleReject(record.id)}
                >
                  Reject
                </Button>
              </>
            )}
          </div>
        ),
      },
    ],
    [handleApprove, handleReject]
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
        onSearchItem={onSearch}
        buttonConfigs={[]}
      ></ManagementScreen>
    </main>
  );
};

export default CheckoutRequestManagementScreen;
