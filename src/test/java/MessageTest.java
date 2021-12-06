import Entities.Message.ActionMessage;
import Entities.Message.ImageMessage;
import Entities.Message.Message;
import Entities.Message.TextMessage;
import Entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class MessageTest {
    @Test
    public void TestMessage(){
        User owner = new User("Varun");
        Message msg = Message.messageMaker("123", owner, null);

        TextMessage tmsg = new TextMessage("Ice cream is awesome, like if you agree!", owner);
        Message msg2 = Message.messageMaker(":)", owner, tmsg);
        Message msg3 = Message.messageMaker("Dislike", owner, tmsg);
        ActionMessage amsg = new ActionMessage("Like", owner, tmsg);
        File sourceimage = new File("test.png");
        ImageMessage imsg = new ImageMessage(sourceimage, owner);
        String result1 = amsg.toString();
        String result2 = imsg.toString();
        String result3 = msg.toString();
        String result4 = msg2.toString();
        String resilt5 = msg3.toString();

        Assertions.assertEquals("ActionMessage{sender='Varun'action='Like'the original message='TextMessage{sender='Varun'msg='Ice cream is awesome, like if you agree!'}'}", result1);
        Assertions.assertEquals("ImageMessage{sender='Varun'image='test.png'}",result2);
        Assertions.assertEquals("TextMessage{sender='Varun'msg='123'}",result3);
        Assertions.assertEquals("ActionMessage{sender='Varun'action=':)'the original message='TextMessage{sender='Varun'msg='Ice cream is awesome, like if you agree!'}'}", result4);
        Assertions.assertEquals("ActionMessage{sender='Varun'action='Dislike'the original message='TextMessage{sender='Varun'msg='Ice cream is awesome, like if you agree!'}'}",resilt5);
    }
}
