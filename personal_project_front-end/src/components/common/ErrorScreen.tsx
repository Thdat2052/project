import { Button, Result, ResultProps } from "antd";
import React from "react";
import { Link } from "react-router-dom";

export type ErrorScreenProps = ResultProps & {
  redirectTo?: string;
};

const ErrorScreen: React.FC<ErrorScreenProps> = ({
  status,
  title,
  subTitle,
  redirectTo = "/",
}) => {
  return (
    <div
      style={{
        minHeight: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <Result
        status={status}
        title={title}
        subTitle={subTitle}
        extra={
          <Link to={redirectTo}>
            <Button type="primary">Quay lại trang chủ</Button>
          </Link>
        }
      />
    </div>
  );
};

export default ErrorScreen;
