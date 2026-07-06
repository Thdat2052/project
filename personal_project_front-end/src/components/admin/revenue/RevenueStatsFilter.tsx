import {
  generateYearOptions,
  getCurrentMonth,
  getCurrentYear,
  months,
} from "@/utils/dateUtils";
import { Select, Space } from "antd";
import React, { useCallback } from "react";

interface RevenueStatsFilterProps {
  updateMonthValue: (month: number) => void;
  updateYearValue: (year: number) => void;
}

export const optionMonths = months.map((month) => ({
  value: month,
  label: `Tháng ${month}`,
}));

export const years = generateYearOptions(2010, 2100);

const RevenueStatsFilter: React.FC<RevenueStatsFilterProps> = ({
  updateMonthValue,
  updateYearValue,
}) => {
  const handleMonthChange = useCallback(
    (value: number) => {
      updateMonthValue(value);
    },
    [updateMonthValue]
  );

  const handleYearChange = useCallback(
    (value: number) => {
      updateYearValue(value);
    },
    [updateYearValue]
  );

  return (
    <Space wrap>
      <Select
        defaultValue={getCurrentMonth()}
        style={{ width: 120 }}
        onChange={handleMonthChange}
        options={optionMonths}
      />
      <Select
        defaultValue={getCurrentYear()}
        style={{ width: 120 }}
        onChange={handleYearChange}
        options={generateYearOptions(2010, 2100)}
      />
    </Space>
  );
};

export default RevenueStatsFilter;
