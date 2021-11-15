package Entities.Message;

import Entities.User;

public class TextMessage extends Message<String> {
    private final String msg;

    public TextMessage(String str, User sender) {
        this.msg = str;
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "sender='" + this.sender.getNickname() + '\'' +
                "msg='" + this.msg + '\'' +
                '}';
    }
}
