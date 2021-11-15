import Entities.Message.TextMessage;
import Entities.User;
import Entities.Message.ActionMessage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ActionMessageTest {
    @Test
    public void TestActionMessage(){
        User owner = new User("Varun");
        TextMessage tmsg = new TextMessage("Ice cream is awesome, like if you agree!", owner);
        ActionMessage amsg = new ActionMessage("Like", owner, tmsg);
        String result = amsg.toString();
        Assertions.assertEquals("ActionMessage{sender='Varun'action='Like' the original message='TextMessage{sender='Varun'msg='Ice cream is awesome, like if you agree!'}'}", result);
    }
}
