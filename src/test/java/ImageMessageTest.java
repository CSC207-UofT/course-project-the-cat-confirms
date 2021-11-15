import Entities.Message.ImageMessage;
import Entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class ImageMessageTest{

    //test to check whether the toString method returns the required result.
    @Test
    public void TestImageMessage() throws IOException {
        User jacob = new User("Jacob");
        ImageMessage imgms = new ImageMessage(new File("resources/images/testimage1.png"), jacob);
        String result = imgms.toString();
        String expected = "ImageMessage{sender='Jacob'image='resources/images/testimage1.png'}";
        Assertions.assertEquals(expected, result);
    }

    // To see the image, call the main method below
    public static void main(String[] args) throws IOException{
        User jacob = new User("Jacob");
        ImageMessage imgms = new ImageMessage(new File("resources/images/testimage1.png"), jacob);
        imgms.showImage();
    }
}


