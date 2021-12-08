package UseCases;

import Entities.IUser;

import java.util.HashMap;

public interface IUserProfile {
    HashMap<String, Object> getOwnerDict();

    void setOwnerIPAddress(String ownerIPAddress);

    void setOwnerName(String ownerName);

    void addUser(IUser user);

    String getNickname(String userId);

    IUser getOwner();
}
