import Entities.User;
import Gateways.Repo.UserRepo;
import Gateways.Server;
import Gateways.ChatroomManager;
import UseCases.UserProfile;

import java.io.IOException;

public class CommandLineClient {
    public static void main(String[] args) {
        SystemInOut systemInOut = new SystemInOut();
        try {
            UserRepo userRepo = new UserRepo("");
            if (!userRepo.isReady()) {
                System.out.println("damn");
                userRepo.initRepo("");
            }
            User owner = userRepo.getOwner();
            System.out.println(owner);

            UserProfile userProfile = new UserProfile(owner);
            ChatroomManager chatroomManager = new ChatroomManager(owner);


            Server srv = new Server(userProfile, chatroomManager, userRepo);
            owner.setIpAddress(srv.getHostIP()+':'+srv.getPort());

            systemInOut.startInteract(userProfile, chatroomManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}