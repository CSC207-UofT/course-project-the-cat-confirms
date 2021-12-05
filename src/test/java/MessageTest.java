import Entities.Message.ActionMessage;
import Entities.Message.ImageMessage;
import Entities.Message.TextMessage;
import Entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class MessageTest {
    @Test
    public void TestMessage(){
        User owner = new User("Varun");
        TextMessage tmsg = new TextMessage("Ice cream is awesome, like if you agree!", owner);
        ActionMessage amsg = new ActionMessage("Like", owner, tmsg);
        File sourceimage = new File("test.png");
        ImageMessage imsg = new ImageMessage(sourceimage, owner);
        String result1 = amsg.toString();
        String result2 = imsg.toString();

        Assertions.assertEquals("ActionMessage{sender='Varun'action='Like'the original message='TextMessage{sender='Varun'msg='Ice cream is awesome, like if you agree!'}'}", result1);
        Assertions.assertEquals("ImageMessage{sender='Varun'image='test.png'}",result2);
    }
}
