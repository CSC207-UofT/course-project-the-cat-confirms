import Entities.Message.LikeMessage;
import Entities.Message.TextMessage;
import Entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LikeMessageTest {
    @Test
    public void TestLikeMessage(){
        User owner = new User("Varun");
        TextMessage tmsg = new TextMessage("Ice cream is awesome, like if you agree!", owner);
        User liker = new User("Johnny");
        LikeMessage lmsg = new LikeMessage(tmsg, liker);
        String result = lmsg.toString();
        Assertions.assertEquals("LikeMessage{sender='Johnny'Number of Likes'1'the original message='TextMessage{sender='Varun'msg='Ice cream is awesome, like if you agree!'}'}", result);
    }
}
