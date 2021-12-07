import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import {joinRoom} from '../actions/room';
import IconButton from '@mui/material/IconButton';
import {Camera} from '@mui/icons-material';
import QrCode from 'qrcode-reader';
import Webcam from 'react-webcam';

export class JoinRoomDialog extends React.Component {
    constructor(props) {
        super(props);

        this.cameraRef = React.createRef();
        this.cameraInterval = null;

        this.qrCodeReader = new QrCode();
        const {app, menu} = this.props;
        this.qrCodeReader.callback = (error, value) => {
            if(error) {
                console.log(error)
                return;
            }
            menu.setState({
                joinRoomDialogOpen: false
            });
            const joinRoomToken = value.result;
            joinRoom(app, joinRoomToken);
        }
    }
    handleClose = (ev) => {
        const {app, menu} = this.props;
        if (ev.target.id === 'join-room-confirm') {
            const joinRoomToken = document.getElementById('join-room-token').value;
            joinRoom(app, joinRoomToken);
        }
        menu.setState({
            joinRoomDialogOpen: false
        });
    };

    handleJoinRoomKeyPress = (ev) => {
        if (ev.key === "Enter"){
            const create_button = document.getElementById('join-room-confirm');
            create_button.click();
        }
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        const {joinRoomDialogOpen} = this.props.menu.state;

        if(joinRoomDialogOpen){
            clearInterval(this.cameraInterval);
            this.cameraInterval = setInterval(()=>{
                if(this.cameraRef.current === null){
                    clearInterval(this.cameraInterval);
                } else {
                    const screenshot = this.cameraRef.current.getScreenshot();
                    this.qrCodeReader.decode(screenshot);
                }

            }, 1000)
        }
    }

    render() {
        const {joinRoomDialogOpen} = this.props.menu.state;

        return (
            <Dialog open={joinRoomDialogOpen} onClose={this.handleClose}>
                <DialogTitle>Join Room</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        <Webcam style={{width: "50vw"}} ref={this.cameraRef} videoConstraints={{facingMode: { exact: "environment" }}}/>
                    </DialogContentText>
                    <DialogContentText>
                        <TextField
                            autoFocus
                            margin="dense"
                            id="join-room-token"
                            label="Join Room Token"
                            type="text"
                            fullWidth
                            variant="standard"
                            onKeyPress={this.handleJoinRoomKeyPress}
                        />
                    </DialogContentText>

                </DialogContent>
                <DialogActions>
                    <Button id={'join-room-cancel'} onClick={this.handleClose}>Cancel</Button>
                    <Button id={'join-room-confirm'} variant={'contained'} onClick={this.handleClose}>Join</Button>
                </DialogActions>
            </Dialog>
        );

    }

}