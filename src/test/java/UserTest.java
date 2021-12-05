import Entities.User;
import org.junit.jupiter.api.*;

public class UserTest {
    User user1 = new User("peter");
    User user2 = new User("123","yifan","90016");


    @Test
    public void testGetUserId(){
        String id = user1.getUserId();
        Assertions.assertEquals("123", user2.getUserId());
        Assertions.assertEquals(user1.getUserId(), id);
    }

    @Test
    public void testSetUsername(){
        user1.setUsername("Junhao");
        user2.setUsername("Jackson");
        Assertions.assertEquals("Junhao",user1.getNickname());
        Assertions.assertEquals("Jackson",user2.getNickname());
    }

    @Test
    public void testSetIpAddress(){
        user1.setIpAddress("123");
        user2.setIpAddress("345");
        Assertions.assertEquals("123",user1.getIpAddress());
        Assertions.assertEquals("345",user2.getIpAddress());
    }
}
