import React from 'react';
import {MainPage} from './MainPage/MainPage';
import {ChatroomDialog} from './Dialogs/ChatroomDialog';
import {getOwnerProfile} from './actions/owner';
import axios from 'axios';
import {pollMsg} from './actions/room';


export class App extends React.Component {
    constructor(props) {
        super(props);
        const {
            match: {params},
        } = props;

        this.port = parseInt(params.port);
        this.cancelTokenSource = axios.CancelToken.source();

        this.ownerId = "";
        this.ipAddress = "";

        this.state = {
            nickname: "",
            chatRooms: {},
            activeChatroomId: null
        };
    }

    componentDidMount() {
        getOwnerProfile(this)
    }

    render() {
        return (<div>
                <MainPage app={this}/>
                <ChatroomDialog app={this}/>
            </div>
        );
    }
}