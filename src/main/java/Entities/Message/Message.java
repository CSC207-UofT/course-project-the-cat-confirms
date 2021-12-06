package Entities.Message;

import Entities.User;
import Utils.JSONable;

import java.io.File;

public abstract class Message<T> implements JSONable {
    protected User sender;
    protected String msgId;

    public static Message messageMaker(Object data, User sender, TextMessage txt){
        Message msg = null;
        if (txt == null){
            if (data instanceof String){
                msg = new TextMessage((String) data, sender);
            }
            if (data instanceof File){
                msg = new ImageMessage((File) data, sender);
            }
        }
        else{
            if (data instanceof String){
            msg = new ActionMessage((String) data ,sender, txt);
            }
        }

        return msg;
    }

    public void setMsgId(String msgId){
        this.msgId = msgId;
    }
}
