package Entities.Message;

import Utils.JSONable;

import java.util.Date;

public abstract class Message implements IMessage {
    protected String sender;
    protected String msgId;
    protected Date timestamp;

    @Override
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @Override
    public Date getTimestamp() {
        return timestamp;
    }
}
