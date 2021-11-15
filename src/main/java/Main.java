import Entities.User;
import Repo.UserRepo;
import Server.Server;
import UseCases.ChatroomManager;
import UseCases.UserProfile;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        SystemInOut systemInOut = new SystemInOut();
        try {
            UserRepo userRepo = new UserRepo();
            User owner = null;
            if (!userRepo.isReady()) {
                String ownerName = systemInOut.getLine("Initializing... Please enter your username:");
                owner = new User(ownerName);
                userRepo.initRepo(owner);
            } else {
                owner = userRepo.getOwner();
            }


            UserProfile userProfile = new UserProfile(owner);
            ChatroomManager chatroomManager = new ChatroomManager();

            Server srv = new Server(userProfile, chatroomManager);

            systemInOut.startInteract(userProfile, chatroomManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}