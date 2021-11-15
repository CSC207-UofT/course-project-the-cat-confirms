package Gateways.Repo;

import Entities.User;

import java.util.ArrayList;
import java.util.HashMap;

public class UserRepo extends JSONRepo {
    public UserRepo() {
        super("resources/user_repo.json");
    }

    public void initRepo(User owner) {
        this.set("owner", owner.toDict());
        this.set("knownUsers", new ArrayList<User>());

        this.saveRepo();
        loaded = true;
    }

    public void addKnownUser(User user){
        ArrayList<User> knowUsers = (ArrayList<User>) this.get("knownUsers");
        knowUsers.add(user);

        this.saveRepo();
    }

    public User getOwner() {
        return new User((HashMap<String, Object>) this.get("owner"));
    }
}