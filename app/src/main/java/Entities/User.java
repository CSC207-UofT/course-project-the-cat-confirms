package Entities;

import Utils.JSONable;

import java.util.HashMap;
import java.util.UUID;

public class User implements IUser {
    private final String userId;
    private String nickname;
    private String ipAddress;

    public User(HashMap<String, Object> dict) {
        this.userId = (String) dict.get("userId");
        this.nickname = (String) dict.get("nickname");
    }

    public User(String nickname) {
        this.userId = UUID.randomUUID().toString();
        this.nickname = nickname;
    }

    public User(String userId, String nickname, String ipAddress) {
        this.userId = userId;
        this.nickname = nickname;
        this.ipAddress = ipAddress;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void setUsername(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getIpAddress() {
        return ipAddress;
    }

    @Override
    public void setIpAddress(String address) {
        this.ipAddress = address;
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> dict = new HashMap<>();

        dict.put("userId", userId);
        dict.put("nickname", nickname);
        dict.put("ipAddress", ipAddress);

        return dict;
    }

    @Override
    public String toString() {
        return "{" +
                "\"userId:\"" + userId + "\"," +
                "\"nickname:\"" + nickname + "\"," +
                "\"ipAddress:\"" + ipAddress + "\"," +
                '}';
    }
}