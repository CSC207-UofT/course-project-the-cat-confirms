package Entities.Message;

import java.util.HashMap;

public class ActionMessage extends Message {
    private final String data;
    private final String sender;

    protected ActionMessage(String data, String sender) {
        this.data = data;
        this.sender = sender;
    }

    @Override
    public HashMap<String, Object> toDict() {
        return null;
    }
    // TODO: implement ME
}
