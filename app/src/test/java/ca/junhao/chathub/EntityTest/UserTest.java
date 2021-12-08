package ca.junhao.chathub.EntityTest;

import Entities.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UserTest {
    User user1 = new User("Peter");
    User user2 = new User("123", "Yifan", "90016");

    /**
     * A new user should be able to store a userId when specified
     */
    @Test
    public void testGetUserId() {
        assertEquals("123", user2.getUserId());
    }

    /**
     * A new user should be able to store her/his own nickname
     */
    @Test
    public void testGetNickname() {
        assertEquals("Peter", user1.getNickname());
        assertEquals("Yifan", user2.getNickname());
    }

    /**
     * Each user should be assigned a unique user id
     */
    @Test
    public void testUniqueUserId() {
        User newUser = new User("Peter");

        // only comparing with user1 because user2's id is manually assigned
        assertNotEquals(newUser.getUserId(), user1.getUserId());
    }

    /**
     * Each user should be able to set her/his own name
     */
    @Test
    public void testSetNickname() {
        String nickname1 = "Junhao";
        String nickname2 = "Jackson";
        assertNotEquals(nickname1, nickname2);

        user1.setUsername(nickname1);
        user2.setUsername(nickname2);

        assertEquals(nickname1, user1.getNickname());
        assertEquals(nickname2, user2.getNickname());
        assertNotEquals(user1.getNickname(), user2.getNickname());
    }

    /**
     * An IP address without a port number should be stored
     */
    @Test
    public void testSetIpAddressWithoutPort() {
        String ipAddress = "http://0.0.0.0";
        user1.setIpAddress(ipAddress);
        assertEquals(ipAddress, user1.getIpAddress());
    }

    /**
     * An IP address with a port number should be stored
     */
    @Test
    public void testSetIpAddressWithPort() {
        String ipAddress = "http://0.0.0.0:1234";
        user2.setIpAddress(ipAddress);
        assertEquals(ipAddress, user2.getIpAddress());

    }
}