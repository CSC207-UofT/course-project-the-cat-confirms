import Entities.User;
import UseCases.ChatroomManager;
import UseCases.UserProfile;

import java.util.Arrays;
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
        User owner = userProfile.getOwner();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] args = line.split(" ");
            String cmd = args[0];
            if (cmd.equals("chat")) {
                String sub_cmd = args[1];
                if (sub_cmd.equals("new")) {
                    String roomName = args[2];
                    try{
                        chatroomManager.addChatRoom(roomName, owner);
                    } catch (IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }
                } else {
                    String[] msgWord = Arrays.copyOfRange(args, 2, args.length);
                    String msgString = String.join(" ", msgWord);
                    String status = chatroomManager.sendMessage(sub_cmd, msgString, owner);
                    System.out.println(status);
                }
            }


            System.out.println("Please enter a command:");
        }

    }
}
