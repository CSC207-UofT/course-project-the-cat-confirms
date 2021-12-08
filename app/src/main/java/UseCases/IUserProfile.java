package UseCases;

import Entities.User;

import java.util.HashMap;

public interface IUserProfile {
    HashMap<String, Object> getOwnerDict();

    void setOwnerIPAddress(String ownerIPAddress);

    void setOwnerName(String ownerName);

    void addUser(String newUserId, String nickname, String ipAddress);

    String getNickname(String userId);

    User getOwner();
}
