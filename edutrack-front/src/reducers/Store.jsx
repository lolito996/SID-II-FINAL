import { configureStore } from "@reduxjs/toolkit";
import appReducer from "./AppReducer";

export const store = configureStore({
  reducer: appReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false,
    }),
});
