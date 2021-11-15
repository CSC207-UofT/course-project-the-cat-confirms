package UseCases;

import Entities.Chatroom;
import Entities.Message.TextMessage;
import Entities.User;

import java.util.ArrayList;
import java.util.HashMap;

import static Entities.Message.Message.messageMaker;


public class ChatroomManager {
    private final HashMap<String, Chatroom> chatRooms;

    public ChatroomManager() {
        chatRooms = new HashMap<>();
    }

    public void addChatRoom(String roomName, User admin) {
        Chatroom chatroom = new Chatroom(roomName, admin);

        this.chatRooms.put(chatroom.getRoomId(), chatroom);
    }

    public ArrayList<Chatroom> getChatRooms() {
        return (ArrayList<Chatroom>) chatRooms.values();
    }

    public String sendMessage(String chatroomId, String msgString, User sender) {
        if (!this.chatRooms.containsKey(chatroomId)){
            return "[FAIL]" + "chatroomId" + chatroomId + "not found";
        }

        String msgType = msgString.substring(0, 4);
        String msgText = msgString.substring(4);

        if (msgType.equals("txt=")){
            TextMessage msg = (TextMessage) messageMaker(msgText, sender);
            chatRooms.get(chatroomId).addMessage(msg);
        } else {
            return "[FAIL]" + "Message type " + msgType + " unsupported";
        }

        return "[SUCCESS]";
    }

    public ArrayList<String> getMessages(String chatroomId) {
        return chatRooms.get(chatroomId).getMessages();
    }
}
