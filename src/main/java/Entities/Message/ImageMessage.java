package Entities.Message;

import Entities.User;

import java.io.File;

public class ImageMessage extends Message<File>{
    private final File image;

    public ImageMessage(File img, User sender) {
        this.image = img;
        this.sender = sender;
    }

    @Override
    public HashMap<String, Object> toDict() {
        return null;
    }
  
    @Override
    public String toString() {
        return "ImageMessage{" +
                "sender='" + this.sender.getNickname() + '\'' +
                "image='" + this.image + '\'' +
                '}';
    }

    public String getImagePath(){
            return image.getPath();
        }
}
