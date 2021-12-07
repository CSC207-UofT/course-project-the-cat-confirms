package Gateways;

import Entities.Message.Message;
import Entities.User;
import UseCases.Chatroom;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static Entities.Message.Message.messageMaker;


public class ChatroomManager {
    private final HashMap<String, Chatroom> chatRooms;
    private final User owner;

    public ChatroomManager(User owner) {
        this.chatRooms = new HashMap<>();
        this.owner = owner;
    }

    public Chatroom createChatRoom(String roomName) {
        Chatroom chatroom = new Chatroom(roomName, owner);

        this.chatRooms.put(chatroom.getRoomId(), chatroom);

        return chatroom;
    }

    public HashMap<String, Object> getChatRoomDict(String roomId) {
        return this.chatRooms.get(roomId).toDict();
    }

    public String storeMessage(String chatroomId, String msgString, String sender) {
        Chatroom chatroom = chatRooms.get(chatroomId);

        if (chatroom == null){
            return "[FAIL]" + "chatroomId" + chatroomId + "not found";
        }

        // decouple the type from the data
        String msgType = msgString.substring(0, 3);
        String msgData = msgString.substring(4);

        // pass the arguments to the use case
        chatroom.addMessage(msgType, msgData, sender);

        return "[SUCCESS]";
    }

    public String getMessageSince(String chatroomId, Date timestamp) {
        Chatroom chatroom = chatRooms.get(chatroomId);

        if (chatroom == null){
            return "[FAIL]" + "chatroomId" + chatroomId + "not found";
        }

        return JSONValue.toJSONString(chatroom.getMessagesSince(timestamp));
    }

    public User getOwner(){
        return owner;
    }

//    public String enrollUser(String chatroomId, String userId, String nickname, String ipAddress) {
//        if (!this.chatRooms.containsKey(chatroomId)){
//            return "[FAIL] chatroomId " + chatroomId + " not found";
//        }
//
//        User listener = new User(userId, nickname, ipAddress);
//        Chatroom chatroom = chatRooms.get(chatroomId);
//        chatroom.addListener(listener);
//
//        return JSONValue.toJSONString(chatroom.toDict());
//    }
}
