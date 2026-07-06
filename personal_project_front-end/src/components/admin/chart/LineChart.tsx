import { ApexOptions } from "apexcharts";
import React from "react";
import ReactApexChart from "react-apexcharts";

export interface LineChartProps {
  series?: {
    name: string;
    data: any[];
  }[];
  height?: number;
  type?: string;
  zoomEnabled?: boolean;
  dataLabelsEnabled?: boolean;
  curve?: string;
  title?: string;
  titleAlign?: string;
  gridRowColors?: string[];
  gridRowOpacity?: number;
  categories?: string[];
}

const LineChart: React.FC<LineChartProps> = ({
  series,
  height = 350,
  type = "line",
  zoomEnabled = false,
  dataLabelsEnabled = false,
  curve = "straight",
  title,
  titleAlign = "left",
  gridRowColors = ["#f3f3f3", "transparent"],
  gridRowOpacity = 0.5,
  categories,
}) => {
  const options: any = {
    chart: {
      height: height,
      type: type,
      zoom: {
        enabled: zoomEnabled,
      },
    },
    dataLabels: {
      enabled: dataLabelsEnabled,
    },
    stroke: {
      curve: curve,
    },
    title: {
      text: title,
      align: titleAlign,
    },
    grid: {
      row: {
        colors: gridRowColors,
        opacity: gridRowOpacity,
      },
    },
    xaxis: {
      categories: categories,
    },
  };

  return (
    <div className="mt-3">
      <div id="chart">
        <ReactApexChart
          options={options}
          series={series}
          type="line"
          height={height}
        />
      </div>
    </div>
  );
};

export default LineChart;
