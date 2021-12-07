package Entities.Message;

import Entities.User;

import java.util.HashMap;

public class ImageMessage extends Message {
    private String data;
    private String sender;

    protected ImageMessage(String data, String sender){
        this.data = data;
        this.sender = sender;
    }

    @Override
    public HashMap<String, Object> toDict() {
        return null;
    }
    // TODO: implement ME
}
