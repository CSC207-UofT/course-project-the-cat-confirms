package UseCases;

import Entities.Message.Message;
import Entities.User;
import Utils.JSONable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Chatroom implements JSONable {
    private final String roomId;
    private final ArrayList<Message> messages;
    private final User admin;
    private final ArrayList<User> listeners;
    private String roomName;

    public Chatroom(String roomName, User admin) {
        if (roomName.equals("new")){
            throw new IllegalArgumentException("Cannot name a room as new");
        }

        this.roomId = UUID.randomUUID().toString();
        this.roomName = roomName;
        this.admin = admin;
        this.messages = new ArrayList<>();
        this.listeners = new ArrayList<>();

        System.out.println("Created chatroom " + roomName + " with id=" + this.roomId);
    }

    public String getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void addMessage(Message msg) {
        this.messages.add(msg);
    }

    public ArrayList<String> getMessages() {
        // FIXME: support other types of messages
        ArrayList<String> ret = new ArrayList<>();
        for (Message msg :
                this.messages) {
            ret.add(msg.toString());
        }
        return ret;
    }

    public User getAdmin() {
        return admin;
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
        dict.put("messages", this.messages);
        return dict;
    }


}