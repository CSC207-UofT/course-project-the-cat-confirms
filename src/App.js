import React from 'react';
import {MainPage} from './MainPage/MainPage';
import {ChatroomDialog} from './Dialogs/ChatroomDialog';
import {getOwnerProfile} from './actions/owner';


export class App extends React.Component {
    constructor(props) {
        super(props);
        const {
            match: {params},
        } = props;

        this.port = parseInt(params.port);

        this.userId = "";
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