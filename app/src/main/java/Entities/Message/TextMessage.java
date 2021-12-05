package Entities.Message;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class TextMessage extends Message {
    private final String msg;

    protected TextMessage(String str, String sender) {
        this.msg = str;

        this.msgId = UUID.randomUUID().toString();
        this.sender = sender;
        this.timestamp = new Date();
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "sender='" + this.sender + '\'' +
                "msg='" + this.msg + '\'' +
                "timestamp='" + this.timestamp + '\'' +
                '}';
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> dict = new HashMap<>();

        dict.put("msgId", msgId);
        dict.put("msgString", "txt="+msg);
        dict.put("sender", sender);
        dict.put("timestamp", timestamp.getTime());

        return dict;
    }
}
