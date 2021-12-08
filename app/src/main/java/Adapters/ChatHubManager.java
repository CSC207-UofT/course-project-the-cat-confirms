package Adapters;

import Adapters.Controllers.IChatHubController;
import Adapters.Gateways.Repo.IUserRepo;
import Adapters.Presenters.IChatHubViewer;
import UseCases.Chatroom;
import UseCases.UserProfile;
import org.json.simple.JSONValue;

import java.util.Date;
import java.util.HashMap;

public class ChatHubManager implements IChatHubViewer, IChatHubController {
    private final HashMap<String, Chatroom> chatRooms;
    private final UserProfile userProfile;

    public ChatHubManager(IUserRepo userRepo) {
        this.chatRooms = new HashMap<>();
        this.userProfile = new UserProfile(userRepo);
    }

    @Override
    public String setOwnerName(String ownerName) {
        userProfile.getOwner().setUsername(ownerName);

        return this.getOwner();
    }

    @Override
    public void setOwnerIPAddress(String ipAddress) {
        this.userProfile.setOwnerIPAddress(ipAddress);
    }

    @Override
    public String createChatRoom(String roomName) {
        Chatroom chatroom = new Chatroom(roomName, userProfile.getOwner());

        this.chatRooms.put(chatroom.getRoomId(), chatroom);

        return JSONValue.toJSONString(chatroom.toDict());
    }

    @Override
    public String storeMessage(String chatroomId, String msgString, String senderId) {
        Chatroom chatroom = chatRooms.get(chatroomId);

        if (chatroom == null) {
            return "[FAIL]" + "chatroomId" + chatroomId + "not found";
        }

        // decouple the type from the data
        String msgType = msgString.substring(0, 3);
        String msgData = msgString.substring(4);

        // pass the arguments to the use case
        String senderName = userProfile.getNickname(senderId);
        chatroom.addMessage(msgType, msgData, senderName);

        return "[SUCCESS]";
    }

    @Override
    public void storeUser(String userId, String nickname, String ipAddress) {
        userProfile.addUser(userId, nickname, ipAddress);
    }

    @Override
    public String getOwner() {
        return JSONValue.toJSONString(userProfile.getOwner().toDict());
    }

    @Override
    public String getChatRoom(String roomId) {
        HashMap<String, Object> chatRoomDict = this.chatRooms.get(roomId).toDict();
        return JSONValue.toJSONString(chatRoomDict);
    }

    @Override
    public String getMessageSince(String chatroomId, Date timestamp) {
        Chatroom chatroom = chatRooms.get(chatroomId);

        if (chatroom == null) {
            return "[FAIL]" + "chatroomId" + chatroomId + "not found";
        }

        return JSONValue.toJSONString(chatroom.getMessagesSince(timestamp));
    }
}
