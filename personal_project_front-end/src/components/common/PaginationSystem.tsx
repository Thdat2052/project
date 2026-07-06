import type { PaginationProps } from "antd";
import { Pagination } from "antd";
import React from "react";

interface CustomPaginationProps {
  defaultPageSize?: number;
  totalItems: number;
  currentPage: number;
  onChangePage: (page: number, pageSize: number) => void;
}

const showTotal: PaginationProps["showTotal"] = (total) =>
  `Tổng ${total} sản phẩm`;

const PaginationSystem: React.FC<CustomPaginationProps> = ({
  totalItems,
  onChangePage,
  defaultPageSize,
  currentPage,
}) => (
  <>
    <Pagination
      size="small"
      total={totalItems}
      current={currentPage}
      showTotal={showTotal}
      showSizeChanger={false}
      defaultPageSize={defaultPageSize ? defaultPageSize : 5}
      onChange={onChangePage}
    />
  </>
);

export default PaginationSystem;
