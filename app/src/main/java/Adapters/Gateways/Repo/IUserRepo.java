package Adapters.Gateways.Repo;

import java.util.HashMap;

public interface IUserRepo {
    HashMap<String, Object> getOwnerInfo();

    void setOwnerInfo(HashMap<String, Object> ownerInfo);

    HashMap<String, HashMap<String, Object>> getUserInfos();

    void setUserInfos(HashMap<String, HashMap<String, Object>> users);
}
