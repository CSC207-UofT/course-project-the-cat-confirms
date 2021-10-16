import Entities.User;
import Repo.UserRepo;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;

public class MainTest {
    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    public void testProfileRead() {
        UserRepo userRepo = new UserRepo();
        User owner = null;
        if (!userRepo.isLoaded()) {
            String ownerName = systemInOut.getLine("Initializing... Please enter your username:");
            owner = new User(ownerName);
            userRepo.initRepo(owner);
        } else {
            owner = userRepo.getUser();
        }
        System.out.println(owner.toString());
    }
}