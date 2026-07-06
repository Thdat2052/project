import { notification } from "antd";
import type { NotificationPlacement } from "antd/es/notification/interface";

type NotificationType = "success" | "info" | "warning" | "error";

export const notify = {
  open: (
    type: NotificationType,
    message: string,
    description?: string,
    placement: NotificationPlacement = "bottomRight"
  ) => {
    notification[type]({
      message,
      description,
      placement,
      duration: 3, // giây
    });
  },

  success: (
    message: string,
    description?: string,
    placement?: NotificationPlacement
  ) => notify.open("success", message, description, placement),

  error: (
    message: string,
    description?: string,
    placement?: NotificationPlacement
  ) => notify.open("error", message, description, placement),

  warning: (
    message: string,
    description?: string,
    placement?: NotificationPlacement
  ) => notify.open("warning", message, description, placement),

  info: (
    message: string,
    description?: string,
    placement?: NotificationPlacement
  ) => notify.open("info", message, description, placement),
};
