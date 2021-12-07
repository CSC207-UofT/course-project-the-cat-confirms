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
        System.out.println(this.users);
    }

    public String getNickname(String userId){
        System.out.println(this.users);

        User user = users.get(userId);
        if (user != null){
            System.out.println("hey");
            return user.getNickname();
        }

        System.out.println("damn");

        return "";
    }

    public User getOwner() {
        return owner;
    }
}
