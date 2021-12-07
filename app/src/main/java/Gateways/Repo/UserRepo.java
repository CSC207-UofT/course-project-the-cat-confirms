package Gateways.Repo;

import java.util.HashMap;
import java.util.List;

public class UserRepo extends JSONRepo implements IUserRepo {
    public UserRepo(String path) {
        super(path);
    }

    @Override
    public HashMap<String, Object> getOwnerInfo() {
        return (HashMap<String, Object>) this.get("owner");
    }

    @Override
    public void setOwnerInfo(HashMap<String, Object> ownerInfo) {
        this.set("owner", ownerInfo);
        this.saveRepo();
    }

    @Override
    public HashMap<String, HashMap<String, Object>> getUserInfos() {
        return (HashMap<String, HashMap<String, Object>>) this.get("users");
    }

    @Override
    public void setUserInfos(HashMap<String, HashMap<String, Object>> userInfos) {
        this.set("users", userInfos);
        this.saveRepo();
    }
}