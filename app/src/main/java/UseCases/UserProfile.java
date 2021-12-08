package UseCases;

import Adapters.Gateways.Repo.IUserRepo;
import Entities.IUser;
import Entities.User;

import java.util.HashMap;

public class UserProfile implements IUserProfile {
    private final IUser owner;
    private final HashMap<String, IUser> users;

    private final IUserRepo userRepo;

    public UserProfile(IUserRepo userRepo) {
        this.userRepo = userRepo;

        this.owner = importOwner();
        this.users = importUsers();
    }

    /**
     * Import owner with best efforts
     *
     * @return the imported owner. if the import was unsuccessful, create a new User with empty name
     */
    private IUser importOwner() {
        HashMap<String, Object> ownerInfo = userRepo.getOwnerInfo();

        IUser owner;
        if (ownerInfo == null) {
            owner = new User("");
        } else {
            owner = new User(ownerInfo);
        }
        return owner;
    }

    /**
     * Import users with best efforts
     *
     * @return the imported users + the owner. if the import was unsuccessful, return the hashmap as-is.
     */
    private HashMap<String, IUser> importUsers() {
        // import users with best effort
        HashMap<String, IUser> users = new HashMap<>();
        HashMap<String, HashMap<String, Object>> userInfos = userRepo.getUserInfos();
        if (userInfos != null) {
            for (String userId : userInfos.keySet()) {
                HashMap<String, Object> userInfo = userInfos.get(userId);
                IUser user = new User(userInfo);
                users.put(user.getUserId(), user);
            }
        }

        // the owner should be put into the user list as well
        users.put(this.owner.getUserId(), this.owner);

        return users;
    }

    @Override
    public HashMap<String, Object> getOwnerDict() {
        return this.owner.toDict();
    }

    @Override
    public void setOwnerIPAddress(String ownerIPAddress) {
        this.owner.setIpAddress(ownerIPAddress);
    }

    @Override
    public void setOwnerName(String ownerName) {
        this.owner.setUsername(ownerName);
        this.userRepo.setOwnerInfo(this.owner.toDict());
    }

    @Override
    public void addUser(IUser user) {
        this.users.put(user.getUserId(), user);

        HashMap<String, HashMap<String, Object>> usersMap = new HashMap<>();
        for (String userId : this.users.keySet()) {
            usersMap.put(userId, this.users.get(userId).toDict());
        }

        userRepo.setUserInfos(usersMap);
    }

    @Override
    public String getNickname(String userId) {
        IUser user = users.get(userId);
        if (user != null) {
            return user.getNickname();
        }

        return "";
    }

    @Override
    public IUser getOwner() {
        return owner;
    }
}
