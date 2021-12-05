package Entities.Message;

import Entities.User;
import Utils.JSONable;

import java.util.Date;

public abstract class Message implements JSONable {
    protected String sender;
    protected String msgId;
    protected Date timestamp;

    public static Message messageMaker(Object data, String sender){
        Message msg = null;
        if (data instanceof String){
            msg = new TextMessage((String) data, sender);
        }

        return msg;
    }

    public void setMsgId(String msgId){
        this.msgId = msgId;
    }

    public Date getTimestamp(){
        return timestamp;
    }
}
