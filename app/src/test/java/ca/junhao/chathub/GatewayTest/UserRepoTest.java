package ca.junhao.chathub.GatewayTest;

import Adapters.Gateways.Repo.UserRepo;
import Entities.User;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;

import static org.junit.Assert.*;

public class UserRepoTest {
    UserRepo PeterProfile = new UserRepo("Peter.cn.com");
    UserRepo JunhaoProfile = new UserRepo("Junhao.junhao");
    UserRepo JacksonProfile = new UserRepo("Jaclson.jackson");
    HashMap<String, Object> JunhaoInfo = new HashMap<>();
    HashMap<String, Object> JacksonInfo = new HashMap<>();

    @Before
    public void Set(){
        JunhaoInfo.put("Junhao", "ECE");
        JunhaoProfile.setOwnerInfo(JunhaoInfo);
        JacksonInfo.put("Jackson", "CSC");
        JacksonProfile.setOwnerInfo(JacksonInfo);
    }
    /**
     * Check Profile start with no UserInfo and no OwnerInfo
     */
    @Test
    public void testOwnerInfo(){

        assertNull(PeterProfile.getOwnerInfo());
        assertNull(PeterProfile.getUserInfos());
    }

    /**
     * Checking Add Owner Info to The User
     */
    @Test
    public void testSetOwnerInfo(){

        assertEquals(JunhaoProfile.getOwnerInfo(),JunhaoInfo);
    }

    /**
     * Test add one User into UserInfo will return only that User
     */
    @Test
    public void testSetSingleUserInfo(){
        HashMap<String, HashMap<String, Object>> testUserInfo = new HashMap<>();
        testUserInfo.put("Junhao", JunhaoInfo);
        JunhaoProfile.setUserInfos(testUserInfo);
        assertEquals(JunhaoProfile.getUserInfos(),testUserInfo);
    }

    /**
     * Test add 2 User into UserInfo will return Both Users
     */
    @Test
    public void testSetDuoUserInfo(){
        HashMap<String, HashMap<String, Object>> testUserInfo = new HashMap<>();
        testUserInfo.put("Junhao", JunhaoInfo);
        testUserInfo.put("Jackson", JacksonInfo);
        JacksonProfile.setUserInfos(testUserInfo);
        assertEquals(JacksonProfile.getUserInfos(),testUserInfo);
    }


}
