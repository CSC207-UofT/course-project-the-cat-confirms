import Entities.User;
import Repo.UserRepo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainTest {
    @Test
    public void TestProfileRead() {
        SystemInOut systemInOut = new SystemInOut();
        UserRepo userRepo = new UserRepo();
        User owner = null;
        if (!userRepo.isLoaded()) {
            owner = new User("Junhao");
            userRepo.initRepo(owner);
        } else {
            owner = userRepo.getUser();
        }
        Assertions.assertEquals(owner.getNickname(),"Junhao");
    }
}