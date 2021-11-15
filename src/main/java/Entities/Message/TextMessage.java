package Entities.Message;

import Entities.User;

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
}
