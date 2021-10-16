import Entities.Chatroom;
import Entities.Message.TextMessage;
import UseCases.ChatroomManager;
import UseCases.UserProfile;

import java.util.Scanner;

public class SystemInOut {
    Scanner scanner;

    public SystemInOut() {
        scanner = new Scanner(System.in);
    }

    public String getLine(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    public void startInteract(UserProfile userProfile, ChatroomManager chatroomManager) {
        System.out.println("Please enter a command:");
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] args = line.split(" ");
            String cmd = args[0];
            if (cmd.equals("chat")) {
                String sub_cmd = args[1];
                if (sub_cmd.equals("new")) {
                    String roomName = args[2];
                    Chatroom chatroom = new Chatroom(roomName, userProfile.getOwner());
                    chatroomManager.addChatRoom(chatroom);
                } else {
                    String roomName = sub_cmd;

                    String msgText = args[2];
                    TextMessage msg = new TextMessage(msgText, userProfile.getOwner());

                    for (Chatroom chatroom :
                            chatroomManager.getChatRooms()) {
                        chatroom.addMessage(msg);
                    }
                }
            }


            System.out.println("Please enter a command:");
        }

    }
}