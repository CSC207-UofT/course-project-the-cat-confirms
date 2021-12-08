package UseCases;

import Entities.Message.Message;
import Entities.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Chatroom implements IChatroom {
    private final ConcurrentLinkedQueue<Message> messages;
    private final User owner;
    private String roomId;
    private String roomName;

    public Chatroom(String roomName, User owner) {
        if (roomName.equals("new")) {
            throw new IllegalArgumentException("Cannot name a room as new");
        }

        this.roomId = UUID.randomUUID().toString();
        this.roomName = roomName;
        this.owner = owner;
        this.messages = new ConcurrentLinkedQueue<>();

        System.out.println("Created chatroom " + roomName + " with id=" + this.roomId);
    }

    @Override
    public String getRoomId() {
        return roomId;
    }

    @Override
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public String getRoomName() {
        return roomName;
    }

    @Override
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public void addMessage(String msgType, String msgData, String senderId) {
        Message msg = Message.messageMaker(msgType, msgData, senderId);
        this.messages.add(msg);
        if (this.messages.size() >= 50) {
            this.messages.poll();
        }
    }

    @Override
    public ArrayList<HashMap<String, Object>> getMessagesSince(Date timestamp) {
        // FIXME: support other types of messages
        ArrayList<HashMap<String, Object>> ret = new ArrayList<>();
        for (Message msg :
                this.messages) {
            if (msg.getTimestamp().after(timestamp)) {
                ret.add(msg.toDict());
            }
        }
        return ret;
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> dict = new HashMap<>();
        dict.put("roomId", this.roomId);
        dict.put("roomName", this.roomName);
        dict.put("messages", this.getMessagesSince(new Date(0)));
        dict.put("owner", this.owner.toDict());

        return dict;
    }
}