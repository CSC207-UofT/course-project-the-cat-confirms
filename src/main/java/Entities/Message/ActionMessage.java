package Entities.Message;

import Entities.User;
//
//import java.util.ArrayList;

public class ActionMessage extends Message<String> {
    public final TextMessage original;
//    private final String action;

<<<<<<<<< Temporary merge branch 1


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
=========
//
//
//
    public ActionMessage(User sender, TextMessage original) {
        this.original = original;
        this.sender = sender;
//        listOfActions.add("Like");
//        listOfActions.add("Dislike");
//        listOfActions.add(":)");
//        if (listOfActions.contains(act)) {
//            this.action = act;
//            this.sender = sender;
//            this.original = original;
//        } else {
//            throw new IllegalArgumentException("action can only be one of {Like, Dislike, :)}");
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "ActionMessage{" +
//                "sender='" + this.sender.getNickname() + '\'' +
//                "action='" + this.action + '\'' +
//                "the original message='" + this.original + '\'' +
//                '}';
//
>>>>>>>>> Temporary merge branch 2
    }
}


