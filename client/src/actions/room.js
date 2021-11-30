export const newRoom = (app, roomName) => {
    const {chatRooms} = app.state;

    chatRooms[Date.now()] = {
        roomName: roomName,
        messages: {}
    };

    app.setState({
        chatRooms: chatRooms
    });
};

export const sendTextMsg = (app, msg) => {
    const {chatRooms, activeChatroomId} = app.state;
    chatRooms[activeChatroomId].messages[Date.now()] = {
            sender: 'Junhao',
            type: 'txt',
            msg: msg
    }
    app.setState({
        chatRooms: chatRooms
    });
}