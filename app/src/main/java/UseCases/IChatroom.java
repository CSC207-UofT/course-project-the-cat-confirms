package UseCases;

import Entities.User;
import Utils.JSONable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public interface IChatroom extends JSONable {
    String getRoomId();

    void setRoomId(String roomId);

    String getRoomName();

    void setRoomName(String roomName);

    void addMessage(String msgType, String msgData, String senderId);

    ArrayList<HashMap<String, Object>> getMessagesSince(Date timestamp);

    User getOwner();
}
