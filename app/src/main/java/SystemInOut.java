import Entities.User;
import Gateways.ChatroomManager;
import UseCases.UserProfile;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import static Utils.JSONable.toMap;

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

            if (cmd.equals("new")){
                String roomName = args[1];
//                chatroomManager.addChatRoom(roomName);

             } else if (cmd.equals("chat")) {
                String roomId = args[1];
                String[] msgWord = Arrays.copyOfRange(args, 2, args.length);
                String msgString = String.join(" ", msgWord);
                String status = chatroomManager.storeMessage(roomId, msgString, owner.getUserId());
                System.out.println(status);
            } else if(cmd.equals("enroll")){
                String ipAddress = args[1];
                String roomId = args[2];

                HashMap<String, String> params = new HashMap<>();
                params.put("roomId", roomId);
                params.put("userId", owner.getUserId());
                params.put("nickname", owner.getNickname());
                params.put("ipAddress", owner.getIpAddress());

//                try{
////                    String response = MyHttpClient.get(ipAddress, params);
//                    JSONParser jsonParser = new JSONParser();
//                    JSONObject respJson = (JSONObject) jsonParser.parse(response);
//                    HashMap<String, Object> respDict = toMap(respJson);
//
//                    String roomName = (String) respDict.get("roomName");
//                    System.out.println(respDict.get("User"));
//                    chatroomManager.addChatRoom(roomName, new User("123"), roomId);
//                } catch (Exception e){
//                    e.printStackTrace();
//                }

            } else {
                System.out.println(
                        "[Help Menu]\n"+
                        "You may issue the following commands: \n"+
                                "\tnew ROOM_NAME\n"+
                                "\tchat ROOM_NAME MSG_TYPE=MSG\n"+
                                "\t\tMSG_TYPE is one of (txt, img, act)\n"+
                                "\tenrol ENROLLMENT_SECRET\n"
                        );
            }

            System.out.println("Please enter a command:");
        }

    }
}
