import { useAppSelector } from "@/apps/hooks";
import { selectLoadingCount } from "@/features/loading/loadingSlice";
import { LoadingOutlined } from "@ant-design/icons";
import { Spin } from "antd";
import React from "react";

export default function GlobalLoading() {
  const loadingCount = useAppSelector(selectLoadingCount);

  if (loadingCount === 0) return null;

  return (
    <div className="fixed top-0 left-0 w-full h-full flex justify-center items-center bg-black bg-opacity-50 zIndexMax">
      <Spin indicator={<LoadingOutlined className="text-5xl" spin />} />
    </div>
  );
}
