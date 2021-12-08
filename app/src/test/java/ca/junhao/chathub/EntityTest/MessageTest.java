package ca.junhao.chathub.EntityTest;

import Entities.Message.Message;
import Entities.User;
import org.junit.Test;

import java.util.Date;

import static Entities.Message.IMessage.messageMaker;
import static org.junit.Assert.assertEquals;

/**
 * Check the type of Message is the Type of the message we needed.
 */
public class MessageTest {
    @Test
    public void testGetRightMessageTpe() {
        User Peter = new User("Peter");
        Message firstmessage = messageMaker("txt", "Hello", Peter.getNickname());
        assertEquals("TextMessage{sender='Peter'msg='Hello'timestamp=" + "'" + new Date() + "'}"
                , firstmessage.toString());
    }

}
