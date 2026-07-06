import { ChartSerie } from "@/types/ApexChartType";
import { ApexOptions } from "apexcharts";
import React, { useMemo } from "react";
import ReactApexChart from "react-apexcharts";

export interface ColumnChartProps {
  dataSeries: any[];
  categories: string[];
  nameSeries: string;
  height?: number;
  title?: string;
}

const ColumnChart: React.FC<ColumnChartProps> = ({
  dataSeries,
  categories,
  height,
  nameSeries,
  title,
}) => {
  const dataChart: any = {
    series: [
      {
        name: nameSeries,
        data: dataSeries,
      },
    ],
    options: {
      chart: {
        height: 350,
        type: "bar",
      },
      plotOptions: {
        bar: {
          borderRadius: 10,
          dataLabels: {
            position: "top", // top, center, bottom
          },
        },
      },
      dataLabels: {
        enabled: true,
      },
      xaxis: {
        categories: categories,
        axisBorder: {
          show: false,
        },
        axisTicks: {
          show: false,
        },

        tooltip: {
          enabled: true,
        },
      },
      yaxis: {
        axisBorder: {
          show: false,
        },
        axisTicks: {
          show: false,
        },
        labels: {
          show: false,
        },
      },
      title: {
        text: title ?? "Thống kê",
        position: "top",
        align: "center",
        style: {
          color: "#444",
        },
      },
    },
  };

  return (
    <div className="mt-3">
      <div id="chart ">
        <ReactApexChart
          options={dataChart.options}
          series={dataChart.series}
          type="bar"
          height={height ?? 350}
        />
      </div>
    </div>
  );
};

export default ColumnChart;
