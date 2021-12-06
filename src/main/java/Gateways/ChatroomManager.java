package Gateways;

import Entities.Message.Message;
import Entities.User;
import UseCases.Chatroom;
import Utils.MyHttpClient;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static Entities.Message.Message.messageMaker;


public class ChatroomManager {
    private final HashMap<String, Chatroom> chatRooms;
    private final User owner;

    public ChatroomManager(User owner) {
        this.chatRooms = new HashMap<>();
        this.owner = owner;
    }

//    public void addChatRoom(String roomName, User admin, String roomId) {
//        Chatroom chatroom = new Chatroom(roomName, admin);
//        if (roomId != null){
//            chatroom.setRoomId(roomId);
//        }
//
//        this.chatRooms.put(chatroom.getRoomId(), chatroom);
//    }
    public void addChatRoom(Chatroom chatroom) {
        this.chatRooms.put(chatroom.getRoomId(), chatroom);
    }


    public Collection<Chatroom> getChatRooms() {
        return chatRooms.values();
    }

    private void sendMessageHelper(Chatroom chatroom, Message msg, User listener) {
        String urlString = "http://"+ listener.getIpAddress() + "/msg_in";

        HashMap<String, Object> data = new HashMap<>();
        data.put("chatroomId", chatroom.getRoomId());
        data.put("msg", msg.toDict());

        MyHttpClient.post(urlString, data);
    }

    public void sendMessage(Chatroom chatroom, Message msg) {
        // assume the parameters are all correct when calling this
        User chatRoomOwner = chatroom.getOwner();
        sendMessageHelper(chatroom, msg, chatRoomOwner);
    }

    public void broadcastMessage(Chatroom chatroom, Message msg) {
        for (User listener:
             chatroom.getListeners()) {
            sendMessageHelper(chatroom, msg, listener);
        }
    }

    public String storeMessage(String chatroomId, String msgString, User sender, String msgId) {
        System.out.println(this.chatRooms);
        if (!this.chatRooms.containsKey(chatroomId)){
            return "[FAIL]" + "chatroomId" + chatroomId + "not found";
        }

        String msgType = msgString.substring(0, 4);
        String msgText = msgString.substring(4);

        Chatroom chatroom = chatRooms.get(chatroomId);
        Message msg = null;
        if (msgType.equals("txt=")){
            msg = messageMaker(msgText, sender);
            chatroom.addMessage(msg);
        } else {
            return "[FAIL]" + "Message type " + msgType + " unsupported";
        }

        if (msgId != null){
            msg.setMsgId(msgId);
        }

        if (sender != owner){
            this.sendMessage(chatroom, msg);
        } else {
            this.broadcastMessage(chatroom, msg);
        }

        return "[SUCCESS]";
    }

    public ArrayList<String> getMessages(String chatroomId) {
        return chatRooms.get(chatroomId).getMessages();
    }

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
