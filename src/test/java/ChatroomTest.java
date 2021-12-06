import Entities.Message.Message;
import Entities.Message.TextMessage;
import UseCases.Chatroom;
import Entities.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ChatroomTest {

    User peter = new User("peter");
    User junhao = new User("junhao");
    User jackson = new User("jackson");
    Chatroom chatroom1 = new Chatroom("room1", peter);
    Message<String> firstmessage = new TextMessage("Hello", peter);
    Message<String> secondmessage = new TextMessage("I am Peter", peter);

    @Test
    public void testSetRoomId() {
        chatroom1.setRoomId("123");
        Assertions.assertEquals("123",chatroom1.getRoomId());
    }

    @Test
    public void testSetRoomName() {
        chatroom1.setRoomName("newsroom1");
        Assertions.assertEquals("newsroom1", chatroom1.getRoomName());
    }

    @Test
    public void testAddMessage() {

        chatroom1.addMessage(firstmessage);
        chatroom1.addMessage(secondmessage);
        List<String> msg = new ArrayList<>();
        msg.add(firstmessage.toString());
        msg.add(secondmessage.toString());
        Assertions.assertEquals(chatroom1.getMessages(), msg);
    }

    @Test
    public void testGetOwner() {
        Assertions.assertEquals(chatroom1.getOwner(), peter);
    }

    @Test
    public void testAddListener(){
        chatroom1.addListener(junhao);
        chatroom1.addListener(jackson);
        List<User> listener = new ArrayList<>();
        listener.add(junhao);
        listener.add(jackson);
        Assertions.assertEquals(chatroom1.getListeners(), listener);
    }

}
