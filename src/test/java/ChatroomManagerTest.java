import Entities.Message.Message;
import Entities.Message.TextMessage;
import Entities.User;
import UseCases.Chatroom;
import Gateways.ChatroomManager;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

public class ChatroomManagerTest {

    User peter = new User("Peter");
    User junhao = new User("junhao");
    Chatroom chatroom1 = new Chatroom("cat_confirm", peter);
    ChatroomManager chatroomManager = new ChatroomManager(peter);
    Message<String> newmessage = new TextMessage("HELLO", peter);
    Message<String> newmessage2 = new TextMessage("HELLO", junhao);

    @Test
    public void TestAddChatRoom() {
        chatroomManager.addChatRoom(chatroom1);
        ArrayList<Chatroom> chatlist = new ArrayList<>();
        chatlist.add(chatroom1);
        Assertions.assertEquals(chatroomManager.getChatRooms().toString(), chatlist.toString());
    }
}