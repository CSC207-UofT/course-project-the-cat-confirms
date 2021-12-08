import axios from 'axios';

export const newRoom = (app, roomName) => {
    const {chatRooms} = app.state;

    axios.get(`http://localhost:${app.port}/chatroom_create`, {
        params: {
            roomName: roomName
        }
    }).then((response) => {
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

export const joinRoom = (app, joinRoomToken) => {
    const {chatRooms} = app.state;

    axios.post(joinRoomToken, {
        userId: app.ownerId,
        nickname: app.state.nickname,
        ipAddress: app.ipAddress
    },{
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
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

    axios.post(`http://${chatRooms[activeChatroomId].owner.ipAddress}/chatroom_send`, 'txt-' + msg, {
        params: {
            roomId: activeChatroomId,
            senderId: app.ownerId
        },
    }).then((response) => {
        console.log(response)
    });
};

export const sendImgMsg = (app, msg) => {
    const {chatRooms, activeChatroomId} = app.state;

    axios.post(`http://${chatRooms[activeChatroomId].owner.ipAddress}/chatroom_send`, 'img-' + msg, {
        params: {
            roomId: activeChatroomId,
            senderId: app.ownerId
        },
    }).then((response) => {
        console.log(response)
    });
};

export const pollMsg = (app) => {
    const {chatRooms, activeChatroomId} = app.state;

    const chatroom = chatRooms[activeChatroomId];
    const chatroom_messages = chatroom.messages;
    let timestamp = 0;
    if (chatroom_messages.length > 0){
        timestamp = chatroom_messages[chatroom_messages.length - 1].timestamp;
    }

    app.cancelTokenSource.cancel("new poll started");
    app.cancelTokenSource = axios.CancelToken.source();
    axios.get(`http://${chatroom.owner.ipAddress}/chatroom_view`, {
        params: {
            roomId: activeChatroomId,
            timestamp: timestamp
        },
        cancelToken: app.cancelTokenSource.token
    }).then((response) => {
        chatroom.messages = chatroom_messages.concat(response.data);
        app.setState({
            chatRooms: chatRooms
        });
    }).catch(reason => {
        console.log(reason);
    });
}