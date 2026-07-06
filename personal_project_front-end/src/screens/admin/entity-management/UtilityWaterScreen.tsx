import { UtilityWaterApi } from "@/apis/apps/UtilityWaterApi";
import RevenueStatsFilter from "@/components/admin/revenue/RevenueStatsFilter";
import { InformationStats } from "@/models/request/InformationStats";
import { UtilityIndexModel } from "@/models/UtilityIndexModel";
import { getCurrentMonth, getCurrentYear } from "@/utils/dateUtils";
import { notify } from "@/utils/notification";
import { Breadcrumb, InputNumber, Table, TableColumnsType } from "antd";
import React, { useCallback, useEffect, useMemo, useState } from "react";
import { AiFillSave } from "react-icons/ai";
import { Link } from "react-router-dom";

const conditionDefault: InformationStats = {
  month: getCurrentMonth(),
  year: getCurrentYear(),
};

const listLinkInBreadcrumb = [
  {
    title: <Link to="/">Dashboard</Link>,
  },
  {
    title: "Quản lý điện nước",
  },
];

const UtilityWaterScreen: React.FC = () => {
  const [condition, setCondition] =
    useState<InformationStats>(conditionDefault);
  const [dataSource, setDataSource] = useState<UtilityIndexModel[]>([]);

  const updateMonthValue = (month: number) => {
    setCondition((prevState) => ({
      ...prevState,
      month: month,
    }));
  };

  const updateYearValue = (year: number) => {
    setCondition((prevState) => ({
      ...prevState,
      year: year,
    }));
  };

  const fetchData = async () => {
    try {
      const response = await UtilityWaterApi.findAllWithRoomIsRented();
      setDataSource(response);
    } catch (error: any) {
      /* empty */
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleValueChange = useCallback(
    (value: number | null, key: keyof UtilityIndexModel, index: number) => {
      const newData = [...dataSource];
      const row = { ...newData[index] };

      row[key] = value ?? 0;

      if (
        row.electricityNewIndex >= row.electricityOldIndex &&
        row.waterNewIndex >= row.waterOldIndex
      ) {
        row.electricUsage = row.electricityNewIndex - row.electricityOldIndex;
        row.waterUsage = row.waterNewIndex - row.waterOldIndex;
      }

      newData[index] = row;
      setDataSource(newData);
    },
    [dataSource]
  );
  // eslint-disable-next-line react-hooks/exhaustive-deps
  const handleSave = async (record: UtilityIndexModel) => {
    if (
      record.electricityNewIndex < record.electricityOldIndex ||
      record.waterNewIndex < record.waterOldIndex
    ) {
      notify.error("Chỉ số mới phải >= chỉ số cũ.");
      return;
    }

    const body = {
      ...record,
      monthMeasure: condition.month,
      yearMeasure: condition.year,
    };

    try {
      await UtilityWaterApi.createOrUpdate(body);
      notify.success("Đã lưu chỉ số");
    } catch (error) {
      /* empty */
    }
  };

  const columns: TableColumnsType<UtilityIndexModel> = useMemo(
    () => [
      {
        key: "roomId",
        title: "Mã phòng",
        dataIndex: "roomId",
      },
      {
        key: "electricityOldIndex",
        title: "CS điện cũ",
        render: (_, record, index) => (
          <InputNumber
            min={0}
            value={record.electricityOldIndex}
            onChange={(value) =>
              handleValueChange(value, "electricityOldIndex", index)
            }
          />
        ),
      },
      {
        key: "electricityNewIndex",
        title: "CS điện mới",
        render: (_, record, index) => (
          <InputNumber
            min={0}
            value={record.electricityNewIndex}
            onChange={(value) =>
              handleValueChange(value, "electricityNewIndex", index)
            }
          />
        ),
      },
      {
        key: "electricUsage",
        title: "Điện sử dụng",
        dataIndex: "electricUsage",
      },
      {
        key: "waterOldIndex",
        title: "CS nước cũ",
        render: (_, record, index) => (
          <InputNumber
            min={0}
            value={record.waterOldIndex}
            onChange={(value) =>
              handleValueChange(value, "waterOldIndex", index)
            }
          />
        ),
      },
      {
        key: "waterNewIndex",
        title: "CS nước mới",
        render: (_, record, index) => (
          <InputNumber
            min={0}
            value={record.waterNewIndex}
            onChange={(value) =>
              handleValueChange(value, "waterNewIndex", index)
            }
          />
        ),
      },
      {
        key: "waterUsage",
        title: "Nước sử dụng",
        dataIndex: "waterUsage",
      },
      {
        key: "action",
        title: "Hành động",
        render: (_, record) => (
          <AiFillSave
            className="cursor-pointer text-blue-500 text-xl"
            onClick={() => handleSave(record)}
          />
        ),
      },
    ],
    [handleSave, handleValueChange]
  );

  return (
    <>
      <div className="max-w-screen-xl mb-10">
        <Breadcrumb items={listLinkInBreadcrumb} />
      </div>
      <div className="mb-3">
        <RevenueStatsFilter
          updateMonthValue={updateMonthValue}
          updateYearValue={updateYearValue}
        />
      </div>
      <div className="mt-2">
        <Table
          dataSource={dataSource}
          columns={columns}
          rowKey="roomId"
          pagination={false}
        />
      </div>
    </>
  );
};

export default UtilityWaterScreen;
