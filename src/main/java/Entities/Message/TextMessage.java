package Entities.Message;

import Entities.User;

public class TextMessage extends Message<String> {
    private final String msg;
    private int likeCount;

    public TextMessage(String str, User sender) {
        this.msg = str;
        this.sender = sender;
        this.likeCount = 0;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "sender='" + this.sender.getNickname() + '\'' +
                "msg='" + this.msg + '\'' +
                '}';
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
