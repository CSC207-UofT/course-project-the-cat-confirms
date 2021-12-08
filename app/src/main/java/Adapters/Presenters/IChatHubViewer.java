package Adapters.Presenters;

import java.util.Date;

public interface IChatHubViewer {
    String getOwner();
    String getChatRoom(String roomId);
    String getMessageSince(String chatroomId, Date timestamp);
}
