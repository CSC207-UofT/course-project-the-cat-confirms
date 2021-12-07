import axios from 'axios';


export const getOwnerProfile = (app) => {
    axios.get(`http://localhost:${app.port}/owner`)
        .then((response) => {
            const {nickname, ipAddress, userId} = response.data;

            app.ownerId = userId;
            app.ipAddress = ipAddress;
            app.setState({
                nickname: nickname
            });
        });
};

export const changeOwnerName = (app, newName) => {
    axios.get(`http://localhost:${app.port}/changeOwnerName`, {
        params: {
            newName: newName
        }
    })
        .then((response) => {
            const {nickname} = response.data;

            app.setState({
                nickname: nickname
            });
        });
};