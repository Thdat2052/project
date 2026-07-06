import { RootState } from "@/apps/store";
import { createSlice } from "@reduxjs/toolkit";

interface LoadingState {
  count: number;
}

const initialState: LoadingState = {
  count: 0,
};

export const loadingSlice = createSlice({
  name: "loading",
  initialState,
  reducers: {
    increment: (state) => {
      state.count += 1;
    },
    decrement: (state) => {
      state.count = Math.max(0, state.count - 1);
    },
  },
});
export const selectLoadingCount = (state: RootState) => state.loading.count;
export const { increment, decrement } = loadingSlice.actions;
export default loadingSlice.reducer;
