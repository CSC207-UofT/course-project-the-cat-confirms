import Entities.User;
import Gateways.Repo.UserRepo;
import Gateways.Server;
import Gateways.ChatroomManager;
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
            ChatroomManager chatroomManager = new ChatroomManager(owner);

            Server srv = new Server(userProfile, chatroomManager);
            owner.setIpAddress(srv.getHostIP()+':'+srv.getPort());

            systemInOut.startInteract(userProfile, chatroomManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}