import React from 'react';
import AppBar from '@mui/material/AppBar';
import {Toolbar, Typography} from '@mui/material';
import {MainBarMenu} from './MainBarMenu';

export class MainBar extends React.Component {
    render() {
        return (<AppBar color={'primary'} position="static">
            <Toolbar>
                <Typography variant={'h5'} sx={{flexGrow: 1}}>
                    ChatHub
                </Typography>
                <MainBarMenu app={this.props.app}/>

            </Toolbar>

        </AppBar>);
    }
}