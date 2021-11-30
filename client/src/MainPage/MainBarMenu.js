import React from 'react';
import {IconButton, Menu, MenuItem} from '@mui/material';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import {NewRoomDialog} from '../Dialogs/NewRoomDialog';

export class MainBarMenu extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            anchorEl: null,
            newRoomDialogOpen: false
        };
    }

    handleMenu = (ev) => {
        this.setState({
            anchorEl: ev.target
        });
    };

    handleClose = (ev) => {
        if (ev.target.id === 'new-room') {
            this.setState({
                newRoomDialogOpen: true
            });
        } else if (ev.target.id === 'join-room') {

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
                    aria-label="account of current user"
                    aria-controls="menu-appbar"
                    aria-haspopup="true"
                    onClick={this.handleMenu}
                    color="inherit"
                >
                    <AddCircleIcon/>
                </IconButton>
                <Menu
                    id="menu-appbar"
                    anchorEl={anchorEl}
                    open={Boolean(anchorEl)}
                    onClose={this.handleClose}
                >
                    <MenuItem id={'new-room'} onClick={this.handleClose}>New Room</MenuItem>
                    <MenuItem id={'join-room'} onClick={this.handleClose}>Join Room</MenuItem>
                </Menu>
                <NewRoomDialog app={this.props.app} menu={this}/>
            </div>
        );

    }


}