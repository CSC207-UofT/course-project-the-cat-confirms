package Entities.Message;

import Entities.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ActionMessage extends Message<String> {
    private final String action;
    public final TextMessage original;



    public ActionMessage(String act, User sender, TextMessage original) {
        ArrayList<String> listOfActions = new ArrayList<>();
        listOfActions.add("Like");
        listOfActions.add("Dislike");
        listOfActions.add(":)");
        if (listOfActions.contains(act)) {
            this.action = act;
            this.sender = sender;
            this.original = original;
        } else {
            throw new IllegalArgumentException("action can only be one of {Like, Dislike, :)}");
        }
    }

    @Override
    public String toString() {
        return "ActionMessage{" +
                "sender='" + this.sender.getNickname() + '\'' +
                "action='" + this.action + '\'' +
                "the original message='" + this.original + '\'' +
                '}';
    }

    @Override
    public HashMap<String, Object> toDict() {
        return null;
    }
}
