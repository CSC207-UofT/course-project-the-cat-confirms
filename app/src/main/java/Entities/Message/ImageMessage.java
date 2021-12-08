package Entities.Message;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class ImageMessage extends Message {
    private final String base64;

    protected ImageMessage(String str, String sender) {
        this.base64 = str;

        this.msgId = UUID.randomUUID().toString();
        this.sender = sender;
        this.timestamp = new Date();
    }

    @Override
    public String toString() {
        return "ImageMessage{" +
                "sender='" + this.sender + '\'' +
                "msg='" + this.base64 + '\'' +
                "timestamp='" + this.timestamp + '\'' +
                '}';
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> dict = new HashMap<>();

        dict.put("msgId", msgId);
        dict.put("msgString", "img=" + base64);
        dict.put("sender", sender);
        dict.put("timestamp", timestamp.getTime());

        return dict;
    }
}
