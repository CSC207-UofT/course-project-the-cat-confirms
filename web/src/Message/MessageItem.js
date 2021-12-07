import React from 'react';

import {Avatar, ListItem, ListItemAvatar, ListItemText} from '@mui/material';
import {People} from '@mui/icons-material';

export class MessageItem extends React.Component {
    render() {
        const {sender, msgString} = this.props;

        const type = msgString.substr(0,3);
        const msg = msgString.substr(4);

        return (<ListItem>
            <ListItemAvatar>
                <Avatar>
                    <People/>
                </Avatar>
            </ListItemAvatar>
            <ListItemText primary={sender} secondary={type==='txt'?msg:"Message type not supported"}/>
        </ListItem>)
    }
}