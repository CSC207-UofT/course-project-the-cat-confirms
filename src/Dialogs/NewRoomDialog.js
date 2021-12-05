import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import {newRoom} from '../actions/room';


export class NewRoomDialog extends React.Component {
    handleClose = (ev) => {
        const {app, menu} = this.props;
        if (ev.target.id === 'new-room-create') {
            const newRoomName = document.getElementById('new-room-name').value;
            newRoom(app, newRoomName);
        }
        menu.setState({
            newRoomDialogOpen: false
        });
    };

    handleRoomNameKeyPress = (ev) => {
        if (ev.key === "Enter"){
            const create_button = document.getElementById("new-room-create");
            create_button.click();
        }
    }
    render() {
        const {newRoomDialogOpen} = this.props.menu.state;

        return (
            <Dialog open={newRoomDialogOpen} onClose={this.handleClose}>
                <DialogTitle>New Room</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Please enter the new room's name:
                    </DialogContentText>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="new-room-name"
                        label="New Room Name"
                        type="text"
                        fullWidth
                        variant="standard"
                        onKeyPress={this.handleRoomNameKeyPress}
                    />
                </DialogContent>
                <DialogActions>
                    <Button id={'new-room-cancel'} onClick={this.handleClose}>Cancel</Button>
                    <Button id={'new-room-create'} variant={'contained'} onClick={this.handleClose}>Create</Button>
                </DialogActions>
            </Dialog>
        );

    }

}