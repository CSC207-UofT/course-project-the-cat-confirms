package UseCases;

import Entities.User;
import Gateways.Repo.IUserRepo;

import java.util.HashMap;

public class UserProfile {
    private final User owner;
    private final HashMap<String, User> users;

    private final IUserRepo userRepo;

    /**
     * Import owner with best efforts
     * @return the imported owner. if the import was unsuccessful, create a new User with empty name
     */
    private User importOwner(){
        HashMap<String, Object> ownerInfo = userRepo.getOwnerInfo();

        User owner;
        if (ownerInfo == null){
            owner = new User("");
        } else {
            owner = new User(ownerInfo);
        }
        return owner;
    }

    /**
     * Import users with best efforts
     * @return the imported users + the owner. if the import was unsuccessful, return the hashmap as-is.
     */
    private HashMap<String, User> importUsers(){
        // import users with best effort
        HashMap<String, User> users = new HashMap<>();
        HashMap<String, HashMap<String, Object>> userInfos = userRepo.getUserInfos();
        if (userInfos != null){
            for (String userId: userInfos.keySet()){
                HashMap<String, Object> userInfo = userInfos.get(userId);
                User user = new User(userInfo);
                users.put(user.getUserId(), user);
            }
        }

        // the owner should be put into the user list as well
        users.put(this.owner.getUserId(), this.owner);

        return users;
    }

    public UserProfile(IUserRepo userRepo) {
        this.userRepo = userRepo;

        this.owner = importOwner();
        this.users = importUsers();
    }

    public HashMap<String, Object> getOwnerDict(){
        return this.owner.toDict();
    }

    public void setOwnerIPAddress(String ownerIPAddress){
        this.owner.setIpAddress(ownerIPAddress);
    }

    public void setOwnerName(String ownerName){
        this.owner.setUsername(ownerName);
        this.userRepo.setOwnerInfo(this.owner.toDict());
    }

    public void addUser(String newUserId, String nickname, String ipAddress){
        User newUser = new User( newUserId,  nickname,  ipAddress);
        this.users.put(newUserId, newUser);

        HashMap<String, HashMap<String, Object>> usersMap = new HashMap<>();
        for (String userId: this.users.keySet()){
            usersMap.put(userId, this.users.get(userId).toDict());
        }

        userRepo.setUserInfos(usersMap);
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
