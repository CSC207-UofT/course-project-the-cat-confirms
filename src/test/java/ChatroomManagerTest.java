import Entities.Message.Message;
import Entities.User;
import UseCases.Chatroom;
import Utils.MyHttpClient;
import Gateways.ChatroomManager;
import org.json.simple.JSONValue;

import static org.junit.Assert.*;
import org.junit.jupiter.api.*;

//public class ChatroomManagerTest {
//    @Test
//    public void TestAddChatRoom(){
//        User peter = new User("Peter");
//        Chatroom chatroom1 = new Chatroom("cat_confirm", peter);
//        ChatroomManager chatroomManager = new ChatroomManager(peter);
//        chatroomManager.addChatRoom(chatroom1.getRoomName(), peter, chatroom1.getRoomId());
//        Assertions.assertTrue(chatroomManager.);
//    }
//}
