import Entities.User;
import Gateways.Repo.UserRepo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainTest {
    @Test
    public void TestProfileRead() {
        SystemInOut systemInOut = new SystemInOut();
        UserRepo userRepo = new UserRepo();
        User owner = null;
        if (!userRepo.isReady()) {
            owner = new User("Junhao");
            userRepo.initRepo(owner);
        } else {
            owner = userRepo.getOwner();
        }
        Assertions.assertEquals("Junhao", owner.getNickname());
    }
}