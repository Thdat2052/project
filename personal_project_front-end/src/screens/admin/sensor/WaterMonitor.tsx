import LineChart from "@/components/admin/chart/LineChart";
import { connectWebSocket, disconnectWebSocket } from "@/configs/websocket";
import React, { useEffect, useState } from "react";

const TURBIDITY_THRESHOLD = 5.0;

const WaterMonitor: React.FC = () => {
  const [turbidity, setTurbidity] = useState<number | null>(null);
  const [timestamp, setTimestamp] = useState<string>("N/A");
  const [status, setStatus] = useState<string>("Đang kiểm tra...");
  const [wsStatus, setWsStatus] = useState<string>("Mất kết nối");
  const [statusClass, setStatusClass] = useState<string>("");
  const [wsClass, setWsClass] = useState<string>("disconnected");

  const [chartData, setChartData] = useState<number[]>([]);
  const [chartLabels, setChartLabels] = useState<string[]>([]);

  const MAX_POINTS = 20;

  useEffect(() => {
    connectWebSocket(
      (data) => {
        const now = new Date();
        const formattedTime = now
          .toISOString()
          .replace("T", " ")
          .substring(0, 19);

        setTurbidity(data.turbidity);
        setTimestamp(formattedTime);

        if (data.turbidity > TURBIDITY_THRESHOLD) {
          setStatus("Không thể sử dụng");
          setStatusClass("unsafe");
        } else {
          setStatus("Có thể sử dụng");
          setStatusClass("safe");
        }

        setChartData((prev) => {
          const updated = [...prev, data.turbidity];
          return updated.length > MAX_POINTS
            ? updated.slice(-MAX_POINTS)
            : updated;
        });

        setChartLabels((prev) => {
          const updated = [...prev, formattedTime];
          return updated.length > MAX_POINTS
            ? updated.slice(-MAX_POINTS)
            : updated;
        });
      },
      () => {
        setWsStatus("Đã kết nối");
        setWsClass("connected");
      },
      (error) => {
        console.error("WebSocket error:", error);
        setWsStatus("Mất kết nối");
        setWsClass("disconnected");
      }
    );

    return () => {
      disconnectWebSocket();
    };
  }, []);

  return (
    <div style={{ padding: 20 }}>
      <h2>
        🌊 Độ đục nước:{" "}
        <span>{turbidity !== null ? `${turbidity} NTU` : "N/A"}</span>
      </h2>
      <h3>
        🕒 Thời gian đo: <span>{timestamp}</span>
      </h3>
      <h3>
        🔍 Trạng thái nước: <span className={statusClass}>{status}</span>
      </h3>
      <h3>
        🔌 Trạng thái WebSocket: <span className={wsClass}>{wsStatus}</span>
      </h3>

      <div style={{ width: 600 }}>
        <LineChart
          title="Biểu đồ độ đục nước"
          titleAlign="center"
          type="line"
          height={300}
          curve="smooth"
          dataLabelsEnabled={false}
          categories={chartLabels}
          gridRowColors={["#e0f7fa", "transparent"]}
          gridRowOpacity={0.3}
          series={[
            {
              name: "Độ đục nước (NTU)",
              data: chartData,
            },
          ]}
        />
      </div>

      <style>{`
        .safe { color: green; }
        .unsafe { color: red; }
        .connected { color: blue; }
        .disconnected { color: gray; }
      `}</style>
    </div>
  );
};

export default WaterMonitor;
