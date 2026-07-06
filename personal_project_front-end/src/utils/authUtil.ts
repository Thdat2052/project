// authUtil.ts

import { useAppSelector } from "@/apps/hooks";
import { RoleEnum } from "@/enums/RoleEnum";
import { selectHasAnyRole, selectHasRole } from "@/features/user/userSlice";
import { jwtDecode } from "jwt-decode";

export function isAuthenticated(): boolean {
  const token = localStorage.getItem("token");
  return token !== null && token !== undefined && token !== "";
}

export function saveToken(token: string): void {
  localStorage.setItem("token", token);
}

export function getToken(): string | null {
  return localStorage.getItem("token");
}

export function logout(): void {
  localStorage.removeItem("token");
}

export function useHasRole(role: RoleEnum): boolean {
  return useAppSelector(selectHasRole(role));
}

export function useHasAnyRole(roles: RoleEnum[]): boolean {
  return useAppSelector(selectHasAnyRole(roles));
}

export function getRoleOfUser(roles: RoleEnum[]): RoleEnum {
  return roles.includes(RoleEnum.ADMIN) ? RoleEnum.ADMIN : RoleEnum.USER;
}

type JwtPayload = {
  exp: number; // expiration timestamp (in seconds)
  [key: string]: any;
};

export const isTokenExpired = (token: string): boolean => {
  try {
    const decoded = jwtDecode<JwtPayload>(token);
    const currentTime = Date.now() / 1000; // current time in seconds
    return decoded.exp < currentTime;
  } catch (e) {
    // Token malformed or can't be decoded
    return true;
  }
};
