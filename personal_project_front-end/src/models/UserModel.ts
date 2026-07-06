import { BaseModel } from "./Basemodel";

export type UserModel = BaseModel & {
  fullName: string;
  email: string;
  cccd: string;
  phoneNumber: string;
  permanentAddress?: string;
  licensePlateNumber?: string;
  note?: string;
  username: string;
  password?: string;
  isRoomRepresentative?: boolean;
};

export const defaultUser: UserModel = {
  fullName: "",
  email: "",
  cccd: "",
  phoneNumber: "",
  username: "",
  permanentAddress: "",
  licensePlateNumber: "",
  note: "",
  password: "",
  isRoomRepresentative: false,
};
