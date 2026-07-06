import { RootState } from "@/apps/store";
import { RoleEnum } from "@/enums/RoleEnum";
import { UserResponse } from "@/models/response/UserResponse";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface UserState {
  currentUser: UserResponse | null;
}

const initialState: UserState = {
  currentUser: null,
};

export const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    setUser: (state, action: PayloadAction<UserResponse>) => {
      state.currentUser = action.payload;
    },
    clearUser: (state) => {
      state.currentUser = null;
    },
  },
});

export const { setUser, clearUser } = userSlice.actions;

export const selectCurrentUser = (state: RootState) => state.user.currentUser;

export const selectHasRole = (role: RoleEnum) => (state: RootState) =>
  state.user.currentUser?.roles?.includes(role) ?? false;

export const selectHasAnyRole = (roles: RoleEnum[]) => (state: RootState) =>
  state.user.currentUser?.roles?.some((r) => roles.includes(r)) ?? false;

export default userSlice.reducer;
