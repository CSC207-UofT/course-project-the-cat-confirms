import React from 'react';

import {Avatar, ListItem, ListItemAvatar, ListItemText} from '@mui/material';
import {People} from '@mui/icons-material';

export class MessageItem extends React.Component {
    render() {
        const {sender, type, msg} = this.props;
        return (<ListItem>
            <ListItemAvatar>
                <Avatar>
                    <People/>
                </Avatar>
            </ListItemAvatar>
            <ListItemText primary={sender} secondary={msg}/>
        </ListItem>)
    }
}