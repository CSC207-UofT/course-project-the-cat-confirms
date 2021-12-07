import React from 'react';
import {IconButton, Menu, MenuItem} from '@mui/material';
import {MoreVert} from '@mui/icons-material';
import {ShareRoomDialog} from './ShareRoomDialog';

export class ChatroomMoreMenu extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            anchorEl: null,
            shareRoomDialogOpen: false
        };
    }

    handleMenu = (ev) => {
        this.setState({
            anchorEl: ev.target
        });
    };

    handleClose = (ev) => {
        if (ev.target.id === 'share-room') {
            this.setState({
                shareRoomDialogOpen: true
            });
        } else if (ev.target.id === 'drop-room') {

        }

        this.setState({
            anchorEl: null
        });
    };

    render() {
        const {anchorEl} = this.state;

        return (
            <div>
                <IconButton
                    size="large"
                    aria-haspopup="true"
                    onClick={this.handleMenu}
                    color="inherit"
                >
                    <MoreVert/>
                </IconButton>
                <Menu
                    id="menu-appbar"
                    anchorEl={anchorEl}
                    open={Boolean(anchorEl)}
                    onClose={this.handleClose}
                >
                    <MenuItem id={'share-room'} onClick={this.handleClose}>Share Room</MenuItem>
                    <MenuItem id={'drop-room'} onClick={this.handleClose}>Drop Room</MenuItem>
                </Menu>
                <ShareRoomDialog app={this.props.app} menu={this}/>
            </div>
        );

    }


}