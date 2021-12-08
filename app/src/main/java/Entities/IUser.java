package Entities;

import Utils.JSONable;

import java.util.HashMap;

public interface IUser extends JSONable {
    String getUserId();

    String getNickname();

    void setUsername(String nickname);

    String getIpAddress();

    void setIpAddress(String address);

    @Override
    HashMap<String, Object> toDict();

    @Override
    String toString();
}
