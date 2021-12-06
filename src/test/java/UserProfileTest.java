import Entities.User;
import UseCases.UserProfile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserProfileTest {
    User peter = new User("peter");
    @Test
    public void testUserProfile(){
        UserProfile peterspofile = new UserProfile(peter);
        Assertions.assertEquals(peterspofile.getOwner(), peter);
    }
}
