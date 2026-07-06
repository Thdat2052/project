import { InvoiceApi } from "@/apis/apps/InvoiceApi";
import { RoomApi } from "@/apis/apps/RoomApi";
import RevenueStatsFilter from "@/components/admin/revenue/RevenueStatsFilter";
import {
  InvoiceStatusEnum,
  renderInvoiceStatus,
} from "@/enums/InvoiceStatusEnum";
import { RoomStatusEnum } from "@/enums/RoomStatusEnum";
import {
  defaultInvoiceServiceInfoDetailModel,
  InvoiceServiceInfoDetailModel,
} from "@/models/InvoiceServiceInfoDetailModel";
import { InformationStats } from "@/models/request/InformationStats";
import { RoomModel } from "@/models/RoomModel";
import { formatCurrency } from "@/utils/currencyUtils";
import { getCurrentMonth, getCurrentYear } from "@/utils/dateUtils";
import { notify } from "@/utils/notification";
import {
  Breadcrumb,
  Button,
  Flex,
  Modal,
  Select,
  Table,
  TableColumnsType,
} from "antd";
import html2canvas from "html2canvas";
import jsPDF from "jspdf";
import React, {
  useCallback,
  useEffect,
  useMemo,
  useRef,
  useState,
} from "react";
import { AiOutlineDelete, AiOutlineEye } from "react-icons/ai";
import { Link } from "react-router-dom";
import { InvoiceDetail } from "./InvoiceDetail";

const listLinkInBreadcrumb = [
  {
    title: <Link to="/">Dashboard</Link>,
  },
  {
    title: "Tính tiền",
  },
];

const conditionDefault: InformationStats = {
  month: getCurrentMonth(),
  year: getCurrentYear(),
};

const InvoiceManagementScreen: React.FC = () => {
  const [isShowModal, setIsShowModal] = useState<boolean>(false);
  const [isViewModal, setIsViewModal] = useState<boolean>(false);
  const [invoice, setInvoice] = useState<InvoiceServiceInfoDetailModel>(
    defaultInvoiceServiceInfoDetailModel
  );

  const [rooms, setRooms] = useState<RoomModel[]>([]);
  const [roomId, setRoomId] = useState<number>(0);

  const invoiceRef = useRef<HTMLDivElement>(null);

  const handleExportImage = async () => {
    if (invoiceRef.current) {
      const canvas = await html2canvas(invoiceRef.current);
      const image = canvas.toDataURL("image/png");

      // Tải ảnh xuống
      const link = document.createElement("a");
      link.href = image;
      link.download = "invoice.png";
      link.click();
    }
  };
  const handleExportPDF = async () => {
    if (!invoiceRef.current) return;

    const canvas = await html2canvas(invoiceRef.current, { scale: 2 });
    const imgData = canvas.toDataURL("image/png");

    const imgWidthPx = canvas.width;
    const imgHeightPx = canvas.height;
    const pxToMm = (px: number) => px * 0.264583;

    const imgWidthMm = pxToMm(imgWidthPx);
    const imgHeightMm = pxToMm(imgHeightPx);

    // Giới hạn khổ PDF theo A4
    const pageWidth = 210; // A4 width in mm
    const pageHeight = 297; // A4 height in mm

    // Tính tỷ lệ để fit vào A4
    const ratio = Math.min(pageWidth / imgWidthMm, pageHeight / imgHeightMm);
    const pdfWidth = imgWidthMm * ratio;
    const pdfHeight = imgHeightMm * ratio;

    const pdf = new jsPDF({
      orientation: "portrait",
      unit: "mm",
      format: "a4",
    });

    // Căn giữa nếu nhỏ hơn trang
    const offsetX = (pageWidth - pdfWidth) / 2;
    const offsetY = (pageHeight - pdfHeight) / 2;

    pdf.addImage(imgData, "PNG", offsetX, offsetY, pdfWidth, pdfHeight);
    pdf.save("invoice.pdf");
  };
  const [dataSource, setDataSource] = useState<InvoiceServiceInfoDetailModel[]>(
    []
  );

  const [condition, setCondition] =
    useState<InformationStats>(conditionDefault);
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

  const fetchRoomData = useCallback(async () => {
    try {
      const response = await RoomApi.findAllByStatus(RoomStatusEnum.RENTED);
      setRooms(response);
    } catch (error: any) {
      notify.error(error.data.message);
    }
  }, []);

  const fetchData = useCallback(async () => {
    try {
      const response = await InvoiceApi.getInvoiceInfoByTime(
        condition.month,
        condition.year
      );
      setDataSource(response);
    } catch (error: any) {
      notify.error(error.data.message);
    }
  }, [condition]);

  const handleCalculate = useCallback(async () => {
    try {
      if (!roomId) {
        notify.error("Vui lòng chọn phòng");
        return;
      }
      await InvoiceApi.createOrUpdate(roomId, condition.month, condition.year);
      notify.success("Save success!");
      fetchData();
      setIsShowModal(false);
    } catch (error: any) {
      notify.error(error.data.message);
    }
  }, [condition.month, condition.year, fetchData, roomId]);

  const callApiDelete = useCallback(
    async (id: number, callback?: () => void) => {
      Modal.confirm({
        title: "Are you sure, you want to delete this record?",
        okText: "Yes",
        okType: "danger",
        onOk: async () => {
          try {
            await InvoiceApi.deleteById(id);
            notify.success("Deleted success");
            setDataSource((prev) =>
              prev.filter((item) => item.invoiceId !== id)
            );
            if (callback) callback();
          } catch (error: any) {
            notify.error(error.message);
          }
        },
      });
    },
    []
  );

  const handleConfirmPayment = useCallback(
    (id: number) => {
      Modal.confirm({
        title: "Xác nhận thanh toán?",
        content:
          "Bạn có chắc chắn muốn xác nhận hóa đơn này đã được thanh toán?",
        okText: "Xác nhận",
        okType: "danger",
        cancelText: "Hủy",
        onOk: async () => {
          await InvoiceApi.confirmPayment(id);
          notify.success("Xác nhận thanh toán thành công!");
          fetchData();
        },
      });
    },
    [fetchData]
  );

  const roomOptions = useMemo(() => {
    return rooms.map((room) => ({
      value: room.id,
      label: `Phòng ${room.id}`,
    }));
  }, [rooms]);

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  useEffect(() => {
    fetchRoomData();
  }, [fetchRoomData]);

  const columns: TableColumnsType<InvoiceServiceInfoDetailModel> = useMemo(
    () => [
      {
        key: "1",
        title: "Mã phòng",
        dataIndex: "roomId",
      },
      {
        key: "2",
        title: "Tên khách",
        dataIndex: "fullName",
      },
      {
        key: "3",
        title: "Tổng tiền",
        dataIndex: "totalInvoice",
        render: (price: number) => <>{formatCurrency(price)}</>,
      },
      {
        key: "4",
        title: "Trạng thái",
        dataIndex: "status",
        render: (status: InvoiceStatusEnum) => (
          <>{renderInvoiceStatus(status)}</>
        ),
      },
      {
        key: "5",
        title: "Hành động",
        render: (text: string, record: InvoiceServiceInfoDetailModel) => (
          <div className="flex space-x-2">
            <Button
              icon={<AiOutlineEye />}
              onClick={() => {
                setInvoice({ ...record });
                setIsViewModal(true);
              }}
            ></Button>
            {record.status !== InvoiceStatusEnum.PAID && (
              <Button
                type="primary"
                onClick={() => {
                  handleConfirmPayment(record.invoiceId ?? 0);
                }}
                className="flex items-center"
              >
                Thanh toán
              </Button>
            )}
            <Button
              danger
              icon={<AiOutlineDelete />}
              onClick={() => {
                callApiDelete(record.invoiceId ?? 0);
              }}
              className="flex items-center"
            ></Button>
          </div>
        ),
      },
    ],
    [callApiDelete, handleConfirmPayment]
  );

  const handleRoomChange = useCallback((value: number) => {
    setRoomId(value);
  }, []);

  return (
    <main className="w-full">
      <div className="max-w-screen-xl mb-10">
        <Breadcrumb items={listLinkInBreadcrumb} />
      </div>
      <div className="mb-3">
        <Flex gap="middle">
          <div>
            <RevenueStatsFilter
              updateMonthValue={updateMonthValue}
              updateYearValue={updateYearValue}
            />
          </div>
          <Select
            style={{ width: 120 }}
            onChange={handleRoomChange}
            options={roomOptions}
          />
          <Button type="primary" onClick={handleCalculate}>
            Tính tiền
          </Button>
        </Flex>
      </div>
      <div className="mt-2">
        <Table
          dataSource={dataSource}
          columns={columns}
          rowKey="invoiceId"
          pagination={false}
        />
      </div>
      <Modal
        title={"Tính tiền"}
        visible={isShowModal}
        okText="Calcaulate"
        onCancel={() => {
          setIsShowModal(false);
        }}
        onOk={() => {
          alert("Tính tiền");
        }}
      ></Modal>

      <Modal
        open={isViewModal}
        onCancel={() => {
          setIsViewModal(false);
        }}
        footer={[
          <Button key="export" type="primary" onClick={handleExportImage}>
            Xuất ảnh hóa đơn
          </Button>,
          <Button key="pdf" onClick={handleExportPDF} type="dashed">
            Xuất PDF hóa đơn
          </Button>,
          <Button key="close" onClick={() => setIsViewModal(false)}>
            Đóng
          </Button>,
        ]}
      >
        <div ref={invoiceRef}>
          <InvoiceDetail invoice={invoice} />
        </div>
      </Modal>
    </main>
  );
};

export default InvoiceManagementScreen;
