package Repo;

import Entities.User;


import java.util.HashMap;

public class UserRepo extends Repo {
    public UserRepo() {
        super("resources/user_repo.json");

    }

    public static void main(String[] args) {
        UserRepo userRepo = new UserRepo();

        // userRepo.saveRepo();
    }

    public void initRepo(User owner) {
        this.repo.put("owner", owner.toDict());

        this.saveRepo();
        loaded = true;
    }

    public User getUser() {
        return new User((HashMap<String, Object>) this.repo.get("owner"));
    }
}