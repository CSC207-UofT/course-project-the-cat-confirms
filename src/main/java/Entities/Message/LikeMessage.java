package Entities.Message;

import Entities.User;


public class LikeMessage extends ActionMessage{
    public final TextMessage original;

    public LikeMessage(TextMessage original, User sender){
        super(sender, original);
        this.original = original;
        this.sender = sender;
        int currLikeCount = original.getLikeCount();
        original.setLikeCount(currLikeCount + 1);
    }

    @Override
    public String toString() {
        return "LikeMessage{" +
                "sender='" + this.sender.getNickname() + '\'' +
                "Number of Likes'" + this.original.getLikeCount() + '\'' +
                "the original message='" + this.original + '\'' +
                '}';
    }
}


