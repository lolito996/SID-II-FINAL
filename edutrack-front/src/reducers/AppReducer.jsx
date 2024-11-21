import userReducer from "./UserReducer";
import { combineReducers } from "redux";

const appReducer = combineReducers({
  user: userReducer,
});

export default appReducer;
