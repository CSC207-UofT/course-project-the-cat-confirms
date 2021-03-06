package ca.junhao.chathub.UsecaseTest;

import Entities.Message.Message;
import Entities.User;
import UseCases.Chatroom;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static Entities.Message.IMessage.messageMaker;
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

        Message msg1 = messageMaker("txt", msgData1, Peter.getUserId());
        Message msg2 = messageMaker("txt", msgData2, Junhao.getUserId());

        chatroom1.addMessage(msg1);
        chatroom1.addMessage(msg2);


        ArrayList<HashMap<String, Object>> messages = chatroom1.getMessagesSince(new Date(0));

        assertEquals(messages.get(0).get("msgString"), "txt=" + msgData1);
        assertEquals(messages.get(1).get("msgString"), "txt=" + msgData2);
    }

}