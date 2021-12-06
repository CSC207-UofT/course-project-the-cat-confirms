package Entities.Message;

import Entities.User;
import Utils.JSONable;

public abstract class Message<T> implements JSONable {
    protected User sender;
    protected String msgId;

    public static Message messageMaker(Object data, User sender){
        Message msg = null;
        if (data instanceof String){
            msg = new TextMessage((String) data, sender);
        }

        return msg;
    }

    public void setMsgId(String msgId){
        this.msgId = msgId;
    }
}
