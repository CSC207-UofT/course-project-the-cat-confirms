package UseCases;

import Entities.Chatroom;

import java.util.ArrayList;

public class ChatroomManager {
    private final ArrayList<Chatroom> chatRooms;

    public ChatroomManager() {
        chatRooms = new ArrayList<>();
    }

    public void addChatRoom(Chatroom chatroom) {
        this.chatRooms.add(chatroom);
    }

    public ArrayList<Chatroom> getChatRooms() {
        return chatRooms;
    }

    public void sendTextMessage(String chatroomId, String msg) {

    }

    public ArrayList<String> getMessage(String chatroomId) {
        for (Chatroom chatroom :
                chatRooms) {
            if (chatroom.getRoomId().equals(chatroomId)) {
                return chatroom.getMessages();
            }
        }
        return null;
    }
}
