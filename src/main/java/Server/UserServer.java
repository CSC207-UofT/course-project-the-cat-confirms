package Server;

import Entities.User;
import UseCases.UserProfile;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.OutputStream;

public class UserServer extends Server {
    private final UserProfile userProfile;

    public UserServer(UserProfile userProfile) throws IOException {
        super(8000);
        this.userProfile = userProfile;
        this.server.createContext("/owner", new ProfileHandler());
    }

    class ProfileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            User owner = userProfile.getOwner();
            String response = JSONValue.toJSONString(owner.toDict());

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}