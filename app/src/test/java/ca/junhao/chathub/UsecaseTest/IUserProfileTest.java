package ca.junhao.chathub.UsecaseTest;

import Adapters.Gateways.Repo.UserRepo;
import UseCases.IUserProfile;
import UseCases.UserProfile;
import org.junit.Test;
import static org.junit.Assert.*;

public class IUserProfileTest {

    UserRepo PeterRepo = new UserRepo("0.0.0.0.90016");
    IUserProfile PeterProfile = new UserProfile(PeterRepo);
    String userId = PeterProfile.getOwner().getUserId();

    /**
     * Test when initial the UserRepo, we get an User with an Empty nickname null
     * owner and a random UserId
     */
    @Test
    public void testInitialUserProfile(){

        assertEquals("",PeterProfile.getOwner().getNickname());
        assertNull(PeterProfile.getOwner().getIpAddress());
        assertNotNull(PeterProfile.getOwner().getUserId());
    }

    /**
     * Test the method set own IPAddress
     */
    @Test
    public void testSetOwnerIPAddress(){
        PeterProfile.setOwnerIPAddress("0.0.0.0.8000");
        assertEquals("0.0.0.0.8000", PeterProfile.getOwner().getIpAddress());
    }

    /**
     * Test the method set own Nickname
     */
    @Test
    public void testSetOwnerName(){
        PeterProfile.setOwnerName("Peter");
        assertEquals("Peter", PeterProfile.getNickname(userId));
    }

    /**
     * Test owner is already in the user Profile
     */
    @Test
    public void testOwnerInUser(){
        assertTrue(PeterRepo.getUserInfos().containsKey(userId));
    }

    /**
     * Test we addUser will add new users into the userRepo
     */
    @Test
    public void testAddUser(){
        PeterProfile.addUser("1004009957","Junhao","0.0.0.0.7000");
        assertTrue(PeterRepo.getUserInfos().containsKey("1004009957"));

    }
}
