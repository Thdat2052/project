export interface JwtResponse {
  token: string;
  type: string;
  id: number;
  name: string;
  email: string;
  phone: string;
  address: string;
  gender: boolean;
  status: boolean;
  image: string;
  registerDate: Date;
}
