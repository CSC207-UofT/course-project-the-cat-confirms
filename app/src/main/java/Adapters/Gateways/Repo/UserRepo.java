package Adapters.Gateways.Repo;

import java.util.HashMap;

public class UserRepo extends JSONRepo implements IUserRepo {
    /**
     * use the parent class to load the json file
     * @param path path to the user profile json file
     */
    public UserRepo(String path) {
        super(path);
    }

    /**
     * get info about the owner stored from the previous session
     * @return info about the owner stored from the previous session
     */
    @Override
    public HashMap<String, Object> getOwnerInfo() {
        return (HashMap<String, Object>) this.get("owner");
    }


    /**
     * set info about the owner in the current session
     * @param ownerInfo info about the owner
     */
    @Override
    public void setOwnerInfo(HashMap<String, Object> ownerInfo) {
        this.set("owner", ownerInfo);
        this.saveRepo();
    }

    /**
     * get info about the users stored from the previous session
     * @return info about the users stored from the previous session
     */
    @Override
    public HashMap<String, HashMap<String, Object>> getUserInfos() {
        return (HashMap<String, HashMap<String, Object>>) this.get("users");
    }

    /**
     * set info about the users in the current session
     * @param userInfos info about the owner
     */
    @Override
    public void setUserInfos(HashMap<String, HashMap<String, Object>> userInfos) {
        this.set("users", userInfos);
        this.saveRepo();
    }
}