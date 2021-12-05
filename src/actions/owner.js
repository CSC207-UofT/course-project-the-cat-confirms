import axios from 'axios';


export const getOwnerProfile = (app) =>{
    axios.get(`http://localhost:${app.port}/owner`)
        .then((response)=>{
            const {nickname, ipAddress, userId} = response.data;

            app.userId = userId;
            app.ipAddress = ipAddress;
            app.setState({
                nickname:nickname
            })
        })
}
