package Server;

import Entities.User;
import UseCases.ChatroomManager;
import UseCases.UserProfile;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private final int port;
    protected HttpServer server;
    private final UserProfile userProfile;
    private final ChatroomManager chatroomManager;

    public Server(UserProfile userProfile, ChatroomManager chatroomManager) throws IOException {
        int freePort = 0;
        try {
            ServerSocket serverSocket = new ServerSocket(0);
            freePort = serverSocket.getLocalPort();
            serverSocket.close();
        } catch (IOException e) {
            throw new IOException("Port is not available");
        }
        System.out.println("listening on port:" + freePort);

        this.port = freePort;
        this.userProfile = userProfile;
        this.chatroomManager = chatroomManager;

        initServer();
    }

    // https://www.rgagnon.com/javadetails/java-get-url-parameters-using-jdk-http-server.html
    public static Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;
    }

    private void initServer() throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port), 0);

        this.server.createContext("/owner", new ProfileHandler());
        this.server.createContext("/msg", new ChatRoomHandler());

        this.server.setExecutor(null); // creates a default executor
        this.server.start();
    }

    class ChatRoomHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
            String chatRoomId = params.get("roomId");

            ArrayList<String> messages = chatroomManager.getMessages(chatRoomId);
            String response = JSONValue.toJSONString(messages);

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
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