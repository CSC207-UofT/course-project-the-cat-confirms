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

    handleTextInputChange = (ev) =>{
        this.setState({
            textInput: ev.target.value
        })
    }

    handleSend = () =>{
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
        return(
            <div style={{width:'100vw', display:'flex'}}>
                <TextField
                    id={'text-input'}
                    onChange={this.handleTextInputChange}
                    value={textInput}
                    hiddenLabel
                    fullWidth
                />
                {(textInput === '') ?
                    <IconButton><AddCircle fontSize={"large"}/></IconButton>
                    :<Button onClick={this.handleSend} variant={'contained'} color={'info'}>
                    Send
                </Button>}

            </div>
            )

    }


}