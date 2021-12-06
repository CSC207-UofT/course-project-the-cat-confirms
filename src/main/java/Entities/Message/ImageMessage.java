package Entities.Message;

import Entities.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

public class ImageMessage extends Message<File>{
    private final File image;
    private final String encodedString;
    private final BufferedImage buffimg;

    public ImageMessage(File img, User sender) throws IOException {
        this.image = img;
        this.sender = sender;
        byte[] fileContent = Files.readAllBytes(img.toPath());
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        this.encodedString = encodedString;
        BufferedImage buffimg = ImageIO.read(new ByteArrayInputStream(fileContent));
        this.buffimg = buffimg;
    }

    @Override
    public HashMap<String, Object> toDict() {
        return null;
    }
  
    @Override
    public String toString() {
        return "ImageMessage{" +
                "sender='" + this.sender.getNickname() + '\'' +
                "image='" + this.getImagePath() + '\'' +
                '}';
    }

    public String getImagePath(){
            return image.getPath();
        }

        public void showImage(){
            JFrame frame = new JFrame();
            ImageIcon icon = new ImageIcon(this.getImagePath());
            JLabel label = new JLabel(icon);
            frame.add(label);
            frame.setDefaultCloseOperation
                    (JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        }
}

