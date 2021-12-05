import React from 'react';
import {Button, IconButton, TextField} from '@mui/material';
import {sendTextMsg} from '../actions/room';
import {AddCircle} from '@mui/icons-material';

export class MessageInput extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            textInput: ""
        }
    }

    handleTextInputChange = (ev) => {
        this.setState({
            textInput: ev.target.value
        })
    }

    handleKeyPress = (ev) => {
        if (ev.key === 'Enter'){
            const send_text_button = document.getElementById('send-text-button');
            send_text_button.click();
        }
    }

    handleSend = () => {
        const {app} = this.props;
        const {textInput} = this.state;

        sendTextMsg(app, textInput);
        this.setState({
            textInput: ""
        })

        const textInputElem = document.getElementById('text-input');
        textInputElem.focus();
    }

    render() {
        const {textInput} = this.state;
        return (
            <div style={{width: '100vw', display: 'flex'}}>
                <TextField
                    id={'text-input'}
                    onChange={this.handleTextInputChange}
                    onKeyPress={this.handleKeyPress}
                    value={textInput}
                    hiddenLabel
                    fullWidth
                />
                {(textInput === '') ?
                    <IconButton><AddCircle fontSize={"large"}/></IconButton>
                    : <Button id={'send-text-button'} onClick={this.handleSend} variant={'contained'} color={'info'}>
                        Send
                    </Button>}

            </div>
        )
    }

}