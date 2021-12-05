import axios from 'axios';

export const newRoom = (app, roomName) => {
    const {chatRooms} = app.state;

    axios.get(`http://localhost:${app.port}/chatroom_create`, {
        params: {
            roomName: roomName
        }
    }).then((response) => {
        console.log(response.data);
        const {messages, owner, roomId, roomName} = response.data;
        chatRooms[roomId] = {
            roomName:roomName,
            messages: messages,
            owner: owner
        }
        app.setState({
            chatRooms: chatRooms
        });
    });
};

export const sendTextMsg = (app, msg) => {
    const {chatRooms, activeChatroomId} = app.state;

    // axios.get(`http://${chatRooms[activeChatroomId].owner.ipAddress}/chatroom_send`, {
    //     params: {
    //         roomId: activeChatroomId,
    //         msgStr
    //     }
    // }).then((response) => {
    //     console.log(response)
    // });


    // chatRooms[activeChatroomId].messages[Date.now()] = {
    //     sender: 'Junhao',
    //     type: 'txt',
    //     msg: msg
    // };
    // app.setState({
    //     chatRooms: chatRooms
    // });
};