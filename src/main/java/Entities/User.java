package Entities;

import Utils.JSONable;

import java.util.HashMap;
import java.util.UUID;

public class User implements JSONable {
    private final String userId;
    private String nickname;
    private String ipAddress;

    public User(HashMap<String, Object> dict) {
        this.userId = (String) dict.get("userId");
        this.nickname = (String) dict.get("nickname");

        System.out.println(this.userId + this.nickname);
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

    public String getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setUsername(String nickname) {
        this.nickname = nickname;
    }

    public void setIpAddress(String address){
        this.ipAddress = address;
    }

    public String getIpAddress() {
        return ipAddress;
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