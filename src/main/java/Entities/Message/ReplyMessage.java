package Entities.Message;

import Entities.User;


public class ReplyMessage extends ActionMessage{
    public final TextMessage original;
    public final String reply;

    public ReplyMessage(TextMessage original, String reply, User sender){
        super(sender, original);
        this.original = original;
        this.reply = reply;
        this.sender = sender;
    }

    @Override
    public String toString() {
        return "ReplyMessage{" +
                "sender='" + this.sender.getNickname() + '\'' +
                "has replied with'" + this.reply + '\'' +
                "to the original message='" + this.original + '\'' +
                '}';
    }
}
