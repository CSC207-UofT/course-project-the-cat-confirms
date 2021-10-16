package UseCases;

import Entities.User;

public class UserProfile {
    private final User owner;

    public UserProfile(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }


}
