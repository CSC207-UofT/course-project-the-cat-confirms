package Gateways;

import Entities.User;
import UseCases.UserProfile;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.*;

import static Utils.JSONable.toMap;

public class Server {
    private final int port;
    private final String hostIP;
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
        this.hostIP = InetAddress.getLocalHost().getHostAddress();

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
        this.server.createContext("/msg", new ChatRoomViewHandler());
        this.server.createContext("/msg_in", new ChatRoomRecvHandler());
        this.server.createContext("/enroll", new EnrollHandler());

        this.server.setExecutor(null); // creates a default executor
        this.server.start();
    }

    public String getHostIP() {
        return hostIP;
    }

    public int getPort(){
        return port;
    }

    class ChatRoomViewHandler implements HttpHandler {
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

    class ChatRoomRecvHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            byte[] rawBytes = t.getRequestBody().readAllBytes();
            String request = new String(rawBytes);
            JSONParser jsonParser = new JSONParser();
            try {
                JSONObject json = (JSONObject) jsonParser.parse(request);
                HashMap<String, Object> parsed = toMap(json);

                String chatroomId = (String) parsed.get("chatroomId");
                HashMap<String, Object> msg = (HashMap<String, Object>) parsed.get("msg");
                String msgString = (String) msg.get("msgString");
                String msgId = (String) msg.get("msgId");
                // FIXME
                User sender = new User("123");
                chatroomManager.storeMessage(chatroomId, msgString, sender, msgId);

                t.sendResponseHeaders(200, 0);
            } catch (ParseException e) {
                e.printStackTrace();
                t.sendResponseHeaders(400, 0);
            }
            t.close();
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

    class EnrollHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange t) throws IOException {
            Map<String, String> params = queryToMap(t.getRequestURI().getQuery());

            String chatRoomId = params.get("roomId");
            String userId = params.get("userId");
            String nickname = params.get("nickname");
            String ipAddress = params.get("ipAddress");

            String response = chatroomManager.enrollUser(chatRoomId, userId, nickname, ipAddress);

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}