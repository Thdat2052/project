import { RevenueApi, RevenueStatResponse } from "@/apis/apps/RevenueApi";
import {
  RoomApi,
  RoomContractResponse,
  UnpaidCustomerResponse,
} from "@/apis/apps/RoomApi";
import LineChart, { LineChartProps } from "@/components/admin/chart/LineChart";
import CardDataStats from "@/components/admin/revenue/CardDataStats";
import { InvoiceStatusEnum } from "@/enums/InvoiceStatusEnum";
import { formatCurrency } from "@/utils/currencyUtils";
import {
  getAllAliasMonthOfYear,
  getCurrentMonth,
  getCurrentYear,
} from "@/utils/dateUtils";
import React, { useEffect, useState } from "react";
import { FaProductHunt } from "react-icons/fa6";
import { MdOutlineAttachMoney, MdOutlineStarRate } from "react-icons/md";
const dataLineChartDefault: LineChartProps = {
  series: [
    {
      name: "Revenue System",
      data: [10, 41, 35, 51, 49, 62, 69, 91, 148, 69, 91, 148],
    },
  ],
  height: 350,
  type: "line",
  zoomEnabled: false,
  dataLabelsEnabled: false,
  curve: "straight",
  title: "Revenue System",
  titleAlign: "left",
  gridRowColors: ["#f3f3f3", "transparent"],
  gridRowOpacity: 0.5,
  categories: getAllAliasMonthOfYear(),
};

export const defaultRevenueStatResponse: RevenueStatResponse = {
  year: 0,
  month: 0,
  quarter: 0,
  totalRevenue: 0,
};

export const defaultRoomContractResponse: RoomContractResponse = {
  roomId: 0,
  roomName: "",
  roomNumber: "",
  userId: 0,
  username: "",
  endDate: new Date(),
};

export const defaultUnpaidCustomerResponse: UnpaidCustomerResponse = {
  invoiceId: 0,
  roomId: 0,
  roomName: "",
  roomNumber: "",
  userId: 0,
  username: "",
  status: "",
  totalPrice: 0,
  paymentDate: new Date(),
};

const getAmountsOfMonth = (revenueStats: RevenueStatResponse[]): number[] => {
  return revenueStats.map((stats) => stats.totalRevenue);
};

const Home: React.FC = () => {
  const [dataLineChart, setDataLineChart] =
    useState<LineChartProps>(dataLineChartDefault);

  // const [revenue, setRevenue] = useState<RevenueStatResponse[]>([]);

  const [revenueByMonth, setRevenueByMonth] = useState<RevenueStatResponse>(
    defaultRevenueStatResponse
  );

  const [revenueByYear, setRevenueByYear] = useState<RevenueStatResponse>(
    defaultRevenueStatResponse
  );

  const [unpaidCustomers, setUnpaidCustomers] = useState<
    UnpaidCustomerResponse[]
  >([]);

  const [contracts, setContracts] = useState<RoomContractResponse[]>([]);

  const fetchAllData = async () => {
    const year = getCurrentYear();
    const month = getCurrentMonth();
    try {
      const [
        unpaidCustomersRes,
        contractsRes,
        revenueByMonthRes,
        revenueByYearRes,
        revenueRes,
      ] = await Promise.all([
        RoomApi.getUnpaidCustomers(InvoiceStatusEnum.UNPAID),
        RoomApi.getActiveContractsInNextDays(),
        RevenueApi.getRevenueByMonth(month, year),
        RevenueApi.getRevenueByYear(year),
        RevenueApi.getRevenue(),
      ]);
      const newSeries = [
        {
          name: "Doanh thu hàng tháng",
          data: getAmountsOfMonth(revenueRes),
        },
      ];
      setDataLineChart((prevState) => ({
        ...prevState,
        series: newSeries,
      }));
      setUnpaidCustomers(unpaidCustomersRes);
      setContracts(contractsRes);
      //   setRevenue(revenueRes);
      setRevenueByMonth(revenueByMonthRes);
      setRevenueByYear(revenueByYearRes);
    } catch (error) {
      /* empty */
    }
  };

  useEffect(() => {
    fetchAllData();
  }, []);

  return (
    <>
      <div className="grid grid-cols-1 gap-4 md:grid-cols-2 md:gap-6 xl:grid-cols-4 2xl:gap-7.5">
        <CardDataStats
          title="Số lượng phòng còn nợ tiền"
          total={unpaidCustomers.length}
        >
          <MdOutlineStarRate />
        </CardDataStats>
        <CardDataStats
          title="Số lượng phòng sắp hết hạn"
          total={contracts.length}
        >
          <FaProductHunt />
        </CardDataStats>
        <CardDataStats
          title={
            "Tổng doanh thu tháng " +
            getCurrentMonth() +
            " năm " +
            getCurrentYear()
          }
          total={formatCurrency(revenueByMonth.totalRevenue)}
        >
          <MdOutlineAttachMoney />
        </CardDataStats>
        <CardDataStats
          title={"Tổng doanh thu năm " + getCurrentYear()}
          total={formatCurrency(revenueByYear.totalRevenue)}
        >
          <MdOutlineAttachMoney />
        </CardDataStats>
      </div>
      <div>
        <LineChart {...dataLineChart}></LineChart>
      </div>
    </>
  );
};

export default Home;
