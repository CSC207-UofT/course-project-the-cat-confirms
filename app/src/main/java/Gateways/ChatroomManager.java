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

    public ArrayList<Chatroom> getChatRooms() {
        return (ArrayList<Chatroom>) chatRooms.values();
    }

//    private void sendMessageHelper(Chatroom chatroom, Message msg, User listener) {
//        String urlString = "http://"+ listener.getIpAddress() + "/msg_in";
//
//        HashMap<String, Object> data = new HashMap<>();
//        data.put("chatroomId", chatroom.getRoomId());
//        data.put("msg", msg.toDict());
//
////        MyHttpClient.post(urlString, data);
//    }

//    public void sendMessage(Chatroom chatroom, Message msg) {
//        // assume the parameters are all correct when calling this
//        User chatRoomOwner = chatroom.getOwner();
//        sendMessageHelper(chatroom, msg, chatRoomOwner);
//    }
//
//    public void broadcastMessage(Chatroom chatroom, Message msg) {
//        for (User listener:
//             chatroom.getListeners()) {
//            sendMessageHelper(chatroom, msg, listener);
//        }
//    }

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

//    public ArrayList<String> getMessages(String chatroomId) {
//        return chatRooms.get(chatroomId).getMessages();
//    }


    public String enrollUser(String chatroomId, String userId, String nickname, String ipAddress) {
        if (!this.chatRooms.containsKey(chatroomId)){
            return "[FAIL] chatroomId " + chatroomId + " not found";
        }

        User listener = new User(userId, nickname, ipAddress);
        Chatroom chatroom = chatRooms.get(chatroomId);
        chatroom.addListener(listener);

        return JSONValue.toJSONString(chatroom.toDict());
    }
}
