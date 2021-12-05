package UseCases;

import Entities.User;

import java.util.HashMap;

public class UserProfile {
    private final User owner;
    private final HashMap<String, User> users;

    public UserProfile(User owner) {
        this.owner = owner;
        this.users = new HashMap<>();
        this.users.put(owner.getUserId(), owner);
    }

    public void addUser(String userId, String nickname, String ipAddress){
        User newUser = new User( userId,  nickname,  ipAddress);
        users.put(userId, newUser);
    }

    public String getNickname(String userId){
        User user = users.get(userId);
        if (user != null){
            return user.getNickname();
        }

        return "";
    }

    public User getOwner() {
        return owner;
    }
}
