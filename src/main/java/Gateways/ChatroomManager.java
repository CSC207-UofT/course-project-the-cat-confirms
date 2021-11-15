package Gateways;

import Entities.Message.Message;
import Entities.Message.TextMessage;
import Entities.User;
import UseCases.Chatroom;
import Utils.MyHttpClient;
import org.json.simple.JSONValue;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import static Entities.Message.Message.messageMaker;


public class ChatroomManager {
    private final HashMap<String, Chatroom> chatRooms;
    private final User owner;

    public ChatroomManager(User owner) {
        this.chatRooms = new HashMap<>();
        this.owner = owner;
    }

    public void addChatRoom(String roomName, User admin) {
        Chatroom chatroom = new Chatroom(roomName, admin);

        this.chatRooms.put(chatroom.getRoomId(), chatroom);
    }

    public ArrayList<Chatroom> getChatRooms() {
        return (ArrayList<Chatroom>) chatRooms.values();
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
        User chatRoomOwner = chatroom.getAdmin();
        sendMessageHelper(chatroom, msg, chatRoomOwner);
    }

    public void broadcastMessage(Chatroom chatroom, Message msg) {
        for (User listener:
             chatroom.getListeners()) {
            sendMessageHelper(chatroom, msg, listener);
        }
    }

    public String storeMessage(String chatroomId, String msgString, User sender, String msgId) {
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
            return "[FAIL]" + "chatroomId" + chatroomId + "not found";
        }

        User listener = new User(userId, nickname, ipAddress);
        Chatroom chatroom = chatRooms.get(chatroomId);
        chatroom.addListener(listener);

        return "[SUCCESS]";
    }
}
