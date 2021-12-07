package UseCases;

import Entities.Message.Message;
import Entities.User;
import Utils.JSONable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Chatroom implements JSONable {
    private String roomId;
    private final ConcurrentLinkedQueue<Message> messages;
    private final User owner;
    private final ArrayList<User> listeners;
    private String roomName;

    public Chatroom(String roomName, User owner) {
        if (roomName.equals("new")){
            throw new IllegalArgumentException("Cannot name a room as new");
        }

        this.roomId = UUID.randomUUID().toString();
        this.roomName = roomName;
        this.owner = owner;
        this.messages = new ConcurrentLinkedQueue<>();
        this.listeners = new ArrayList<>();

        System.out.println("Created chatroom " + roomName + " with id=" + this.roomId);
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId){
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void addMessage( String msgType, String msgData, String senderId) {
        Message msg = Message.messageMaker(msgType, msgData, senderId);
        this.messages.add(msg);
        if (this.messages.size() >= 50){
            this.messages.poll();
        }
    }

    public ArrayList<HashMap<String, Object>> getMessagesSince(Date timestamp) {
        // FIXME: support other types of messages
        ArrayList<HashMap<String, Object>> ret = new ArrayList<>();
        for (Message msg :
                this.messages) {
            if (msg.getTimestamp().after(timestamp)){
                ret.add(msg.toDict());
            }
        }
        return ret;
    }

    public User getOwner() {
        return owner;
    }

    public void addListener(User listener){
        this.listeners.add(listener);
    }

    public ArrayList<User> getListeners() {
        return this.listeners;
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> dict = new HashMap<>();
        dict.put("roomId", this.roomId);
        dict.put("roomName", this.roomName);
        dict.put("messages", this.getMessagesSince(new Date(0)));
        dict.put("owner", this.owner.toDict());
//        dict.put("listeners", this.listeners);

        return dict;
    }


}