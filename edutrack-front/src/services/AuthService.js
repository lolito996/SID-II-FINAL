import axiosInstance from "./axios";

const auth = (user, password) => {
        axiosInstance.post('/login',{
                username: user,
                password: password
        })
        .then((response)=>{
                console.log(response);
        })
        .catch( (error) =>{
                console.error('Error fetching users:', error);
        })
};

export default auth;

