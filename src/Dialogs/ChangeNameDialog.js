import * as React from 'react';
import Dialog from '@mui/material/Dialog';
import Typography from '@mui/material/Typography';
import {Grid, TextField} from '@mui/material';
import Button from '@mui/material/Button';
import {changeOwnerName} from '../actions/owner';


export class ChangeNameDialog extends React.Component {
    constructor() {
        super();

    }

    handleSubmit = (ev) => {
        const {app} = this.props;

        const newOwnerName = document.getElementById('new-owner-name').value
        if (newOwnerName !== ""){
            changeOwnerName(app, newOwnerName);
        }
    };


    render() {
        const {nickname} = this.props.app.state;
        return (
            <Dialog
                fullScreen
                open={nickname === ""}
            >
                <Grid
                    container
                    spacing={0}
                    direction="column"
                    alignItems="center"
                    justifyContent="center"
                    style={{width: '100vw', height: '100vh', backgroundColor: 'black'}}
                >
                    <Typography color={'white'} variant={'h3'}>Welcome to</Typography>
                    <img src="./logo.png" style={{width: '50vw'}}/>
                    <br/>
                    <Typography color={'white'} variant={'h6'}>Please enter your nickname: </Typography>

                    <TextField
                        id={'new-owner-name'}
                        style={{backgroundColor: 'white'}}
                        variant={'outlined'}
                        size={'small'}
                        margin={'dense'}
                    />
                    <br/>

                    <Button variant={'contained'} onClick={this.handleSubmit}>
                        Submit
                    </Button>
                </Grid>
            </Dialog>
        );
    }

}
