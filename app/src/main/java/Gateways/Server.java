package Gateways;

import Entities.Message.Message;
import Entities.User;
import Gateways.Repo.UserRepo;
import UseCases.Chatroom;
import UseCases.UserProfile;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
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

        this.port = freePort;
        this.hostIP = getLocalIpAddress();

        this.userProfile = userProfile;
        this.userProfile.setOwnerIPAddress(this.hostIP + ':' + this.port);

        this.chatroomManager = chatroomManager;

        initServer();
    }

    // Reference: https://stackoverflow.com/questions/6064510/how-to-get-ip-address-of-the-device-from-code
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
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
        this.server.createContext("/changeOwnerName", new ChangeOwnerNameHandler());

        this.server.createContext("/chatroom_create", new ChatRoomCreateHandler());
        this.server.createContext("/chatroom_view", new ChatRoomViewHandler());
        this.server.createContext("/chatroom_send", new ChatRoomSendHandler());
        this.server.createContext("/enroll", new EnrollHandler());

        System.out.println("Starting to listen on port:" + port);
        this.server.setExecutor(null); // creates a default executor
        this.server.start();
    }

    public String getHostIP() {
        return hostIP;
    }

    public int getPort(){
        return port;
    }

    class ChatRoomCreateHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
            String roomName = params.get("roomName");

            Chatroom chatroom = chatroomManager.createChatRoom(roomName);
            String response = JSONValue.toJSONString(chatroom.toDict());

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    class ChatRoomViewHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
            String chatRoomId = params.get("roomId");
            String timestampStr = params.get("timestamp");
            long timestampValue = timestampStr != null? Long.parseLong(timestampStr): 0;

            Date timestamp = new Date(timestampValue);

            String response = chatroomManager.getMessageSince(chatRoomId, timestamp);

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static public String getBodyText(HttpExchange t) throws IOException {
        InputStream ios = t.getRequestBody();
        StringBuilder msgStringBuilder = new StringBuilder();
        int i;
        while ((i = ios.read()) != -1) {
            msgStringBuilder.append((char) i);
        }
        return msgStringBuilder.toString();
    }

    class ChatRoomSendHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            Map<String, String> params = queryToMap(t.getRequestURI().getQuery());

            String roomId = params.get("roomId");
            String senderId = params.get("senderId");
            String msgString = getBodyText(t);
            System.out.println(senderId);
            String response = chatroomManager.storeMessage(roomId, msgString, userProfile.getNickname(senderId));

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    class ProfileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            User owner = userProfile.getOwner();

            String response = JSONValue.toJSONString(owner.toDict());

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();

        }
    }

    class ChangeOwnerNameHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
            String newName = params.get("newName");

            userProfile.setOwnerName(newName);


            String response = JSONValue.toJSONString(userProfile.getOwnerDict());

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }



    class EnrollHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange t) throws IOException {
            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            Map<String, String> params = queryToMap(t.getRequestURI().getQuery());

            String chatRoomId = params.get("roomId");
            String requestBodyStr = getBodyText(t);

            try {

                JSONParser jsonParser = new JSONParser();
                JSONObject json = (JSONObject) jsonParser.parse(requestBodyStr);
                HashMap<String, Object> bodyParams = toMap(json);
                String userId = (String) bodyParams.get("userId");
                String nickname = (String) bodyParams.get("nickname");
                String ipAddress = (String) bodyParams.get("ipAddress");

                userProfile.addUser(userId, nickname, ipAddress);
                HashMap<String, Object> chatroomDict = chatroomManager.getChatRoomDict(chatRoomId);
                String response = JSONValue.toJSONString(chatroomDict);

                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (IOException | ParseException e) {
            e.printStackTrace();
            }
        }
    }
}