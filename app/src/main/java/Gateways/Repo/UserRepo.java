package Gateways.Repo;

import Entities.User;

import java.util.ArrayList;
import java.util.HashMap;

public class UserRepo extends JSONRepo {
    private User owner;

    public UserRepo(String path) {
        super(path);

        if (!path.equals("")) {
            this.owner =  new User((HashMap<String, Object>) this.get("owner"));
        }
    }

    public void initRepo(String ownerName) {
        User owner = new User(ownerName);
        this.set("owner", owner.toDict());

        this.owner = owner;

        this.saveRepo();
        loaded = true;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
        this.set("owner", owner.toDict());
        this.saveRepo();
    }
}