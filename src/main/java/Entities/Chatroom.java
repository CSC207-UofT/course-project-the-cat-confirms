package Entities;

import Entities.Message.Message;
import Utils.JSONable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Chatroom implements JSONable {
    private final String roomId;
    private final ArrayList<Message> messages;
    private String roomName;
    private final User admin;

    public Chatroom(String roomName, User admin) {
        this.roomId = UUID.randomUUID().toString();
        this.roomName = roomName;
        this.admin = admin;
        this.messages = new ArrayList<>();
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

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> dict = new HashMap<>();
        dict.put("roomId", this.roomId);
        dict.put("roomName", this.roomName);
        dict.put("messages", this.messages);
        return dict;
    }
}