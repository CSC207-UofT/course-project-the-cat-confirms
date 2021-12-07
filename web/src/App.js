import React from 'react';
import {MainPage} from './MainPage/MainPage';
import {ChatroomDialog} from './Dialogs/ChatroomDialog';
import {getOwnerProfile} from './actions/owner';
import axios from 'axios';
import {ChangeNameDialog} from './Dialogs/ChangeNameDialog';

export class App extends React.Component {
    constructor(props) {
        super(props);

        this.port = 0;
        this.cancelTokenSource = axios.CancelToken.source();

        this.ownerId = "";
        this.ipAddress = "";

        this.state = {
            nickname: "",
            chatRooms: {},
            activeChatroomId: null,
        };
    }

    componentDidMount() {
        // update backend listening port
        const urlParams = new URLSearchParams(window.location.search);
        this.port = parseInt(urlParams.get("port"));

        getOwnerProfile(this)
    }

    render() {
        return (<div>
                <MainPage app={this}/>
                <ChatroomDialog app={this}/>
                <ChangeNameDialog app={this}/>
            </div>
        );
    }
}