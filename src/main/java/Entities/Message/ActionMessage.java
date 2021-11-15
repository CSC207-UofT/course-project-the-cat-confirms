package Entities.Message;

import Entities.User;

public class ActionMessage extends Message<String> {
    private final String action;
    public final TextMessage original;


    public ActionMessage(String act, User sender, TextMessage original) {
        this.action = act;
        this.sender = sender;
        this.original = original;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "sender='" + this.sender + '\'' +
                "action='" + this.action + '\'' +
                "to original message='" + this.original + '\'' +
                '}';
    }
}
