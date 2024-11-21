const initialState = {
  id: null,
  username: "",
  courseId: null,
  roles: [],
};

const userReducer = (state = initialState, action) => {
  switch (action.type) {
    case "LOGIN_SUCCESS":
      return {
        ...state,
        id: action.payload.id,
        username: action.payload.username,
        courseId: action.payload.courseId,
        roles: action.payload.roles,
      };
    case "LOGOUT":
      return initialState;
    default:
      return state;
  }
};

export default userReducer;
