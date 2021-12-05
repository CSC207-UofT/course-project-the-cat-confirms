import * as React from 'react';
import Dialog from '@mui/material/Dialog';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import CloseIcon from '@mui/icons-material/Close';
import {Slide} from '@mui/material';
import {MessageItem} from '../Message/MessageItem';
import {MessageInput} from '../Message/MessageInput';
import {MoreVert} from '@mui/icons-material';

const TransitionLeft = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="left" ref={ref} {...props}/>;
})

export class ChatroomDialog extends React.Component {

    handleClose = (ev) => {
        const {app} = this.props;
        app.setState({
            activeChatroomId: null
        });
    };

    render() {
        const {activeChatroomId, chatRooms} = this.props.app.state;
        if (activeChatroomId == null) {
            return (<div/>);
        }

        const room = chatRooms[activeChatroomId];

        const msgList = [];
        for (const [msgId, msg] of Object.entries(room.messages)) {
            msgList.push(<MessageItem key={msgId} {...msg}/>);
            msgList.push(<Divider key={msgId + '_div'}/>);
        }

        return (
            <Dialog
                fullScreen
                open={Boolean(activeChatroomId)}
                onClose={this.handleClose}
                TransitionComponent={TransitionLeft}
            >
                <AppBar sx={{position: 'sticky'}}>
                    <Toolbar>
                        <IconButton
                            edge="start"
                            color="inherit"
                            onClick={this.handleClose}
                            aria-label="close"
                        >
                            <CloseIcon/>
                        </IconButton>
                        <Typography sx={{ml: 2, flex: 1}} variant="h6" component="div">
                            {room.roomName}
                        </Typography>
                        <IconButton autoFocus color="inherit" onClick={this.handleClose}>
                            <MoreVert/>
                        </IconButton>
                    </Toolbar>
                </AppBar>
                <List style={{flex: 1}}>
                    {msgList}
                </List>
                <MessageInput app={this.props.app}/>
            </Dialog>
        );
    }

}
