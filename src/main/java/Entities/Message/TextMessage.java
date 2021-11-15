package Entities.Message;

import Entities.User;

import java.util.HashMap;
import java.util.UUID;

public class TextMessage extends Message<String> {
    private final String msg;

    protected TextMessage(String str, User sender) {
        this.msg = str;

        this.msgId = UUID.randomUUID().toString();
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "sender='" + this.sender + '\'' +
                "msg='" + this.msg + '\'' +
                '}';
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> dict = new HashMap<>();

        dict.put("msgId", msgId);
        dict.put("msgString", "txt="+msg);
        dict.put("sender", sender.toDict());

        return dict;
    }
}
