import { ButtonConfig } from "@/configs/buttonConfig";
import { Breadcrumb, Button, Table, TableProps } from "antd";
import Search from "antd/es/input/Search";
import React, { useMemo } from "react";

export interface GenericEntityManagementProps {
  itemLinks: any[];
  tableProps: TableProps;
  buttonConfigs: ButtonConfig[];
  // onCreateItem: () => void;
  // onDeleteItems: () => void;
  onSearchItem?: (value: string) => void;
}

const ManagementScreen: React.FC<GenericEntityManagementProps> = ({
  tableProps,
  // onCreateItem,
  buttonConfigs,
  onSearchItem,
  itemLinks,
}) => {
  const hasSelected = useMemo(() => {
    if (tableProps.rowSelection) {
      const result = tableProps.rowSelection.selectedRowKeys
        ? tableProps.rowSelection.selectedRowKeys.length > 0
        : false;
      return result;
    }
  }, [tableProps]);

  const selectedLength = useMemo(() => {
    if (tableProps.rowSelection) {
      const result = tableProps.rowSelection.selectedRowKeys
        ? tableProps.rowSelection.selectedRowKeys.length
        : 0;
      return result;
    }
  }, [tableProps]);

  return (
    <div className="w-full">
      <div className="max-w-screen-xl">
        <Breadcrumb items={itemLinks} />
      </div>
      <div>
        <div
          className={
            onSearchItem ? "mt-8 mb-4 flex justify-between" : "mt-8 mb-4"
          }
        >
          <div>
            {/* <Button className="mr-3" type="primary" onClick={onCreateItem}>
              Add New
            </Button> */}
            {buttonConfigs.map((config, index) => (
              <Button
                key={index}
                type={config.type}
                onClick={config.onClick}
                disabled={config.disabled}
                danger={config.danger}
                className="mr-3"
              >
                {config.label}
              </Button>
            ))}
            {/* <Button
              type="primary"
              onClick={onDeleteItems}
              disabled={!hasSelected}
              danger
            >
              Delete Rows
            </Button> */}
            <span style={{ marginLeft: 8 }}>
              {hasSelected ? `Selected ${selectedLength} items` : ""}
            </span>
          </div>
          {onSearchItem && (
            <div>
              <Search
                placeholder="key search"
                allowClear
                onSearch={onSearchItem}
                style={{ width: 200 }}
              />
            </div>
          )}
        </div>
        <Table {...tableProps} />
      </div>
    </div>
  );
};

export default ManagementScreen;
