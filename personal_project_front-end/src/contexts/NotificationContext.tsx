import { notification } from "antd";
import React, { createContext, useContext, ReactNode } from "react";
import type { NotificationArgsProps } from "antd";

export type NotificationPlacement = NotificationArgsProps["placement"];
export type NotificationType = "success" | "info" | "warning" | "error";

export interface InfoNotification {
  type: NotificationType;
  message: string;
  description?: string;
  duration?: number;
  placement?: NotificationPlacement;
}

interface NotificationProviderProps {
  children: ReactNode;
}

interface NotifiContextData {
  toast: (notify: InfoNotification) => void;
}

const NotifiContext = createContext<NotifiContextData | undefined>(undefined);

export const NotificationProvider: React.FC<NotificationProviderProps> = ({
  children,
}) => {
  const [api, contextHolder] = notification.useNotification();

  const toast = (notify: InfoNotification) => {
    api[notify.type]({
      message: notify.message,
      description: notify.description ?? "",
      duration: notify.duration ?? 1,
      placement: notify.placement ?? "bottomRight",
    });
  };

  return (
    <NotifiContext.Provider value={{ toast }}>
      {" "}
      {contextHolder} {children}
    </NotifiContext.Provider>
  );
};

export const useNotifi = (): NotifiContextData => {
  const context = useContext(NotifiContext);
  if (!context) {
    throw new Error("useNotifi must be used within a NotificationProvider");
  }
  return context;
};
