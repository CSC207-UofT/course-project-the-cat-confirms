package Entities.Message;

import Utils.JSONable;

import java.util.Date;

public interface IMessage extends JSONable {
    /**
     * Factory method that makes Messages according to "type"
     *
     * @param type   of the message
     * @param data   in the message
     * @param sender of the message
     * @return a Message entity
     */
    static Message messageMaker(String type, String data, String sender) {
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

    void setMsgId(String msgId);

    Date getTimestamp();
}
