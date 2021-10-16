package Server;

import UseCases.ChatroomManager;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;

public class ChatRoomServer extends Server {
    private final ChatroomManager chatroomManager;

    public ChatRoomServer(ChatroomManager chatroomManager) throws IOException {
        super();
        this.chatroomManager = chatroomManager;
        this.server.createContext("/message", new ProfileHandler());
    }

    class ProfileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
            String chatRoomId = params.get("roomId");
            ArrayList<String> messages = chatroomManager.getMessage(chatRoomId);

            String response = JSONValue.toJSONString(messages);

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}