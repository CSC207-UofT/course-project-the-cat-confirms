import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import QRCode from 'react-qr-code';



export class ShareRoomDialog extends React.Component {

    handleClose = (ev) => {
        const {menu} = this.props;

        menu.setState({
            shareRoomDialogOpen: false
        });
    };

    handleRoomNameKeyPress = (ev) => {
        if (ev.key === "Enter"){
            const create_button = document.getElementById("new-room-create");
            create_button.click();
        }
    }

    render() {
        const {shareRoomDialogOpen} = this.props.menu.state;

        const {activeChatroomId, chatRooms} = this.props.app.state;
        if (activeChatroomId === null) {
            return (<div/>);
        }

        const room = chatRooms[activeChatroomId];
        const roomJoinLink = `http://${room.owner.ipAddress}/enroll?roomId=${activeChatroomId}`

        return (
            <Dialog open={shareRoomDialogOpen} onClose={this.handleClose}>
                <DialogTitle>New Room</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        Scan this to join:
                    </DialogContentText>

                    <QRCode value={roomJoinLink}/>


                    <DialogContentText>
                        Alternatively, you can use this token:
                    </DialogContentText>
                    <TextField fullWidth value={roomJoinLink}/>
                </DialogContent>
                <DialogActions>
                    <Button onClick={this.handleClose}>Close</Button>
                </DialogActions>
            </Dialog>
        );

    }

}