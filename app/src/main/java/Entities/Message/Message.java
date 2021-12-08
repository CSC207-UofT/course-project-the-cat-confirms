package Entities.Message;

import Utils.JSONable;

import java.util.Date;

public abstract class Message implements JSONable {
    protected String sender;
    protected String msgId;
    protected Date timestamp;

    public static Message messageMaker(String type, String data, String sender) {
        Message msg = null;
        switch (type) {
            case "txt":
                msg = new TextMessage(data, sender);
                break;
            case "img":
                msg = new ImageMessage(data, sender);
                break;
            case "act":
                msg = new ActionMessage(data, sender);
                break;
            default:
                throw new TypeNotPresentException(type, new RuntimeException("not supported"));
        }

        return msg;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
