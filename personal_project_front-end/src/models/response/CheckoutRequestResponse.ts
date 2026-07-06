import { CheckoutRequestStatusEnum } from "@/enums/CheckoutRequestEnum";

export type CheckoutRequestResponse = {
  id?: number;
  roomId: number;
  roomName: string;
  userId: number;
  userName: string;
  requestDate: string;
  status: CheckoutRequestStatusEnum;
  reason: string;
};
