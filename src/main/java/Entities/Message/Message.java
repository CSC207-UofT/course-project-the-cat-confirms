package Entities.Message;

import Entities.User;

public class Message<T> {
    protected User sender;
    protected String msgId;

    public static Message messageMaker(Object data, User sender){
        Message msg = null;
        if (data instanceof String){
            msg = new TextMessage((String) data, sender);
        }

        return msg;
    }
}
