import React from 'react';
import {MainPage} from './MainPage/MainPage';
import {ChatroomDialog} from './Dialogs/ChatroomDialog';

export class App extends React.Component {
    constructor() {
        super();
        this.state = {
            chatRooms: {
                1638247291513: {
                    roomName: 'cat-bites-butts',
                    messages: {
                        1638247291514: {
                            sender: 'Peter',
                            type: 'txt',
                            msg: 'hello world'
                        },
                    }
                }
            },
            activeChatroomId: null
        };
    }

    render() {
        return (<div>
                <MainPage app={this}/>
                <ChatroomDialog app={this}/>
            </div>
        );
    }
}