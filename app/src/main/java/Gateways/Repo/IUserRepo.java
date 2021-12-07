package Gateways.Repo;

import java.util.HashMap;
import java.util.List;

public interface IUserRepo {
    HashMap<String, Object> getOwnerInfo();
    void setOwnerInfo(HashMap<String, Object> ownerInfo);
    HashMap<String, HashMap<String, Object>> getUserInfos();
    void setUserInfos(HashMap<String, HashMap<String, Object>> users);
}
