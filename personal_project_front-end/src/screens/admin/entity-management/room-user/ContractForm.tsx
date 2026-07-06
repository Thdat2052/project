import { ContractModel } from "@/models/ContractModel";
import { DatePicker, Form, InputNumber } from "antd";
import dayjs from "dayjs";
import React, { forwardRef, useEffect, useImperativeHandle } from "react";

export interface ContractFormRef {
  getContractData: () => Promise<ContractModel>;
}
interface ContractFormProps {
  initialValues?: Partial<ContractModel>;
  isViewContract: boolean;
}
const ContractForm = forwardRef<ContractFormRef, ContractFormProps>(
  ({ initialValues, isViewContract }, ref) => {
    const [form] = Form.useForm<any>();

    useEffect(() => {
      if (initialValues) {
        const transformedValues = {
          ...initialValues,
          startDate: initialValues.startDate
            ? dayjs(initialValues.startDate)
            : undefined,
          endDate: initialValues.endDate
            ? dayjs(initialValues.endDate)
            : undefined,
        };
        form.setFieldsValue(transformedValues);
      }
    }, [initialValues, form]);

    useImperativeHandle(ref, () => ({
      getContractData: async () => {
        const values = await form.validateFields();
        return {
          ...values,
          startDate: dayjs(values.startDate).toISOString(),
          endDate: values.endDate
            ? dayjs(values.endDate).toISOString()
            : undefined,
        };
      },
    }));

    return (
      <Form form={form} layout="vertical">
        <Form.Item
          label="Ngày ký hợp đồng"
          name="startDate"
          rules={[{ required: true, message: "Start date is required" }]}
        >
          <DatePicker
            style={{ width: "100%" }}
            showTime
            disabled={isViewContract}
          />
        </Form.Item>

        <Form.Item label="Ngày kết thúc hợp đồng" name="endDate">
          <DatePicker
            style={{ width: "100%" }}
            showTime
            disabled={isViewContract}
          />
        </Form.Item>

        <Form.Item
          label="Tiền cọc"
          name="deposit"
          rules={[
            { required: true, message: "Deposit amount is required" },
            {
              type: "number",
              min: 0,
              message: "Deposit must be greater than zero",
            },
          ]}
        >
          <InputNumber
            disabled={isViewContract}
            min={0}
            style={{ width: "100%" }}
          />
        </Form.Item>

        <Form.Item
          label="Tiền phòng hàng tháng"
          name="monthlyRent"
          rules={[
            { required: true, message: "Monthly rent is required" },
            {
              type: "number",
              min: 0,
              message: "Monthly rent must be greater than zero",
            },
          ]}
        >
          <InputNumber
            disabled={isViewContract}
            min={0}
            style={{ width: "100%" }}
          />
        </Form.Item>
      </Form>
    );
  }
);

export default ContractForm;
