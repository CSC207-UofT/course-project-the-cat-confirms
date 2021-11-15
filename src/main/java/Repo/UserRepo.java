package Repo;

import Entities.User;

import java.util.HashMap;

public class UserRepo extends JSONRepo {
    public UserRepo() {
        super("resources/user_repo.json");
    }

    public void initRepo(User owner) {
        this.set("owner", owner.toDict());

        this.saveRepo();
        loaded = true;
    }

    public User getOwner() {
        return new User((HashMap<String, Object>) this.get("owner"));
    }
}