import Entities.User;
import Repo.UserRepo;
import Server.ChatRoomServer;
import Server.UserServer;
import UseCases.ChatroomManager;
import UseCases.UserProfile;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        SystemInOut systemInOut = new SystemInOut();
        try {
            UserRepo userRepo = new UserRepo();
            User owner = null;
            if (!userRepo.isLoaded()) {
                String ownerName = systemInOut.getLine("Initializing... Please enter your username:");
                owner = new User(ownerName);
                userRepo.initRepo(owner);
            } else {
                owner = userRepo.getUser();
            }


            UserProfile userProfile = new UserProfile(owner);
            ChatroomManager chatroomManager = new ChatroomManager();

            UserServer userServer = new UserServer(userProfile);
            ChatRoomServer chatRoomServer = new ChatRoomServer(chatroomManager);

            systemInOut.startInteract(userProfile, chatroomManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}