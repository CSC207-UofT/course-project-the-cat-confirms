import Entities.Message.ReplyMessage;
import Entities.Message.TextMessage;
import Entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReplyMessageTest {
    @Test
    public void TestReplyMessage() {
        User owner = new User("Sammy");
        TextMessage tmsg = new TextMessage("When is everyone arriving at the party?", owner);
        User replier = new User("Kate");
        ReplyMessage rmsg = new ReplyMessage(tmsg, "I will be there at 4:15.", replier);
        String result = rmsg.toString();
        Assertions.assertEquals("ReplyMessage{sender='Kate'has replied with'I will be there at 4:15.'to the original message='TextMessage{sender='Sammy'msg='When is everyone arriving at the party?'}'}", result);
    }
}