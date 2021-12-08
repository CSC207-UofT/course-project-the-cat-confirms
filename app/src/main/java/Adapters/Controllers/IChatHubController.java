package Adapters.Controllers;

import UseCases.Chatroom;

public interface IChatHubController {
    void setOwnerIPAddress(String ipAddress);
    String createChatRoom(String roomName);
    String storeMessage(String chatroomId, String msgString, String senderId);
    void storeUser(String userId, String nickname, String ipAddress);
    String setOwnerName(String ownerName);
}
