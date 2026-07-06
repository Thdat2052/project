import { RoleEnum } from "@/enums/RoleEnum";

export type UserResponse = {
  fullName: string;
  id?: number;
  email: string;
  cccd: string;
  phoneNumber: string;
  newPassword?: string;
  permanentAddress?: string;
  createdAt?: string;
  recordedDate?: string;
  licensePlateNumber?: string;
  note?: string;
  username: string;
  transactionId?: string;
  isRoomRepresentative?: boolean;
  roles?: RoleEnum[];
};
