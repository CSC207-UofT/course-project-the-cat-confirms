package UseCases;

import Entities.Message.IMessage;
import Entities.Message.Message;
import Utils.JSONable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public interface IChatroom extends JSONable {
    String getRoomId();

    void setRoomId(String roomId);

    String getRoomName();

    void setRoomName(String roomName);

    void addMessage(IMessage msg);

    ArrayList<HashMap<String, Object>> getMessagesSince(Date timestamp);
}
