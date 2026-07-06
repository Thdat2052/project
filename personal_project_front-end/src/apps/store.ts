import { Action, configureStore, ThunkAction } from '@reduxjs/toolkit';
import loadingReducer from '../features/loading/loadingSlice';
import userReducer from '../features/user/userSlice';

export const store = configureStore({
  reducer: {
    user: userReducer,
    loading: loadingReducer,
  },
});

export const directDispatch = store.dispatch;
export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof store.getState>;
export type AppThunk<ReturnType = void> = ThunkAction<
  ReturnType,
  RootState,
  unknown,
  Action<string>
>;
