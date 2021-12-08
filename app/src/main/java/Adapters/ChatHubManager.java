package Adapters;

import Adapters.Controllers.IChatHubController;
import Adapters.Gateways.Repo.IUserRepo;
import Adapters.Presenters.IChatHubViewer;
import Entities.IUser;
import Entities.Message.IMessage;
import Entities.User;
import UseCases.Chatroom;
import UseCases.IChatroom;
import UseCases.IUserProfile;
import UseCases.UserProfile;
import org.json.simple.JSONValue;

import java.util.Date;
import java.util.HashMap;

import static Entities.Message.IMessage.messageMaker;

public class ChatHubManager implements IChatHubViewer, IChatHubController {
    private final HashMap<String, IChatroom> chatRooms;
    private final IUserProfile userProfile;

    /**
     * Create a new empty list of chatroom
     * @param userRepo gateway to store/fetch user info
     */
    public ChatHubManager(IUserRepo userRepo) {
        this.chatRooms = new HashMap<>();
        this.userProfile = new UserProfile(userRepo);
    }

    /**
     * Set the owner name to indicate the app has been inited
     * @param ownerName new owner name
     * @return info about the owner
     */
    @Override
    public String setOwnerName(String ownerName) {
        userProfile.setOwnerName(ownerName);

        return this.getOwner();
    }


    /**
     * Set the owner IP address for sharing owner info
     * @param ipAddress the address acquired by the Server
     */
    @Override
    public void setOwnerIPAddress(String ipAddress) {
        this.userProfile.setOwnerIPAddress(ipAddress);
    }

    /**
     * Create a new chatroom with a given name
     * @param roomName new chatroom name
     * @return the chatroom's info
     */
    @Override
    public String createChatRoom(String roomName) {
        IChatroom chatroom = new Chatroom(roomName, userProfile.getOwner());

        this.chatRooms.put(chatroom.getRoomId(), chatroom);

        return JSONValue.toJSONString(chatroom.toDict());
    }

    /**
     * Given a chatroomId, store a message into the chatroom
     * @param chatroomId the chatroom's id
     * @param msgString the message
     * @param senderId who sent the message
     * @return a code indicating whether the action is successful
     */
    @Override
    public String storeMessage(String chatroomId, String msgString, String senderId) {
        IChatroom chatroom = chatRooms.get(chatroomId);

        if (chatroom == null) {
            return "[FAIL]" + "chatroomId" + chatroomId + "not found";
        }

        // decouple the type from the data
        String msgType = msgString.substring(0, 3);
        String msgData = msgString.substring(4);

        // pass the arguments to the use case
        String senderName = userProfile.getNickname(senderId);

        IMessage msg = messageMaker(msgType, msgData, senderName);
        chatroom.addMessage(msg);

        return "[SUCCESS]";
    }

    /**
     * Store a user into the user profile for getting sender nicknames
     * @param userId the user's id
     * @param nickname the user's nickname
     * @param ipAddress the user's ip address
     */
    @Override
    public void storeUser(String userId, String nickname, String ipAddress) {
        IUser user = new User(userId, nickname, ipAddress);
        userProfile.addUser(user);
    }

    /**
     * @return info about the owner
     */
    @Override
    public String getOwner() {
        return JSONValue.toJSONString(userProfile.getOwner().toDict());
    }

    /**
     * @param roomId the chatroom id to be found
     * @return info about the chatroom
     */
    @Override
    public String getChatRoom(String roomId) {
        HashMap<String, Object> chatRoomDict = this.chatRooms.get(roomId).toDict();
        return JSONValue.toJSONString(chatRoomDict);
    }

    /**
     * given a timestamp, get all messages sent after this time
     * @param chatroomId the chatroom's id
     * @param timestamp the time
     * @return all messages sent after the time
     */
    @Override
    public String getMessageSince(String chatroomId, Date timestamp) {
        IChatroom chatroom = chatRooms.get(chatroomId);

        if (chatroom == null) {
            return "[FAIL]" + "chatroomId" + chatroomId + "not found";
        }

        return JSONValue.toJSONString(chatroom.getMessagesSince(timestamp));
    }
}
