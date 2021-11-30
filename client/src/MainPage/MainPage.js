import React from 'react';
import {MainBar} from './MainBar';
import List from '@mui/material/List';
import {Divider, ListItem, ListItemButton} from '@mui/material';

export class MainPage extends React.Component {

    openChatRoom = (ev) => {
        const {app} = this.props;
        app.setState({
            activeChatroomId: ev.target.id
        });
    };

    render() {
        const {chatRooms} = this.props.app.state;

        const roomList = [];
        for (const [roomId, room] of Object.entries(chatRooms)) {
            roomList.push(<ListItem key={roomId}>
                <ListItemButton id={roomId} onClick={this.openChatRoom}>
                    {room.roomName}
                </ListItemButton>
            </ListItem>);
            roomList.push(<Divider key={roomId+'_div'}/>);
        }

        return (<div>
            <MainBar app={this.props.app}/>
            <List>
                {roomList}
            </List>
        </div>);
    }
}