package ca.junhao.chathub.UsecaseTest;

import Entities.Message.Message;
import Entities.User;
import UseCases.Chatroom;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static Entities.Message.Message.messageMaker;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ChatroomTest {

    User Peter = new User("Peter");
    User Junhao = new User("Junhao");
    User Jackson = new User("jackson");
    Chatroom chatroom1 = new Chatroom("room1", Peter);
    Chatroom chatroom2 = new Chatroom("room2", Junhao);
    Message firstmessage = messageMaker("txt", "Hello", Peter.getNickname());
    Message secondmessage = messageMaker("txt", "I am Peter", Peter.getNickname());

    /**
     * Each room is available to set its own room ID
     */
    @Test
    public void testSetRoomId() {
        chatroom1.setRoomId("123");
        assertEquals("123", chatroom1.getRoomId());
    }

    /**
     * when creating new rooms. There room id should be different.
     */
    @Test
    public void testGetDiffRoomId() {
        assertNotEquals(chatroom1.getRoomId(), chatroom2.getRoomId());
    }


    /**
     * Each room should be able to set their room name.
     */
    @Test
    public void testSetRoomName() {
        String newroomname = "newroom1";
        chatroom1.setRoomName(newroomname);
        assertEquals(newroomname, chatroom1.getRoomName());
    }

    /**
     * each room should be able to add message to in it and return
     */
    @Test
    public void testAddMessage() {
        String msgData1 = "I am Peter";
        String msgData2 = "Hello Peter";

        chatroom1.addMessage("txt", msgData1, Peter.getUserId());
        chatroom1.addMessage("txt", msgData2, Junhao.getUserId());

        List<HashMap<String, Object>> msglist = new ArrayList<>();
//            HashMap<String, Object> msg1 = new HashMap<>();
//            msg1.put(msgData1, Peter.getUserId());
//
//            HashMap<String, Object> msg2 = new HashMap<>();
//            msg2.put(msgData2, Peter.getUserId());
//            msglist.add(msg1);
//            msglist.add(msg2);
        assertEquals(chatroom1.getMessagesSince(new Date(1900, 10, 20)), msglist);
    }

    /**
     * Check the owner is the one who created the room
     */
    @Test
    public void testGetOwner() {
        assertEquals(chatroom1.getOwner(), Peter);
    }

}