package Adapters.Gateways;

import Adapters.Controllers.IChatHubController;
import Adapters.Presenters.IChatHubViewer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static Utils.JSONable.toMap;
import static Utils.NetworkHelper.getLocalIpAddress;
import static Utils.ServerHelper.getBodyText;
import static Utils.ServerHelper.queryToMap;
import static Utils.ServerHelper.sendHTTPResponse;

public class Server {
    private final int port;
    private final String hostIP;
    private final IChatHubController chatHubController;
    private final IChatHubViewer chatHubViewer;
    protected HttpServer server;

    public Server(IChatHubController chatHubController, IChatHubViewer chatHubViewer) throws IOException {
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

        this.chatHubController = chatHubController;
        this.chatHubViewer = chatHubViewer;

        this.chatHubController.setOwnerIPAddress(this.hostIP + ':' + this.port);

        initServer();
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

    public int getPort() {
        return port;
    }

    class ChatRoomCreateHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
            String roomName = params.get("roomName");

            String response = chatHubController.createChatRoom(roomName);

            sendHTTPResponse(t, response);
        }
    }

    class ChatRoomViewHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
            String chatRoomId = params.get("roomId");
            String timestampStr = params.get("timestamp");
            long timestampValue = timestampStr != null ? Long.parseLong(timestampStr) : 0;

            Date timestamp = new Date(timestampValue);

            String response = chatHubViewer.getMessageSince(chatRoomId, timestamp);

            sendHTTPResponse(t, response);
        }
    }

    class ChatRoomSendHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            Map<String, String> params = queryToMap(t.getRequestURI().getQuery());

            String roomId = params.get("roomId");
            String senderId = params.get("senderId");
            String msgString = getBodyText(t);

            String response = chatHubController.storeMessage(roomId, msgString, senderId);

            sendHTTPResponse(t, response);
        }
    }

    class ProfileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = chatHubViewer.getOwner();

            sendHTTPResponse(t, response);
        }
    }

    class ChangeOwnerNameHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
            String newName = params.get("newName");

            String response = chatHubController.setOwnerName(newName);

            sendHTTPResponse(t, response);
        }
    }

    class EnrollHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            Map<String, String> params = queryToMap(t.getRequestURI().getQuery());

            String chatRoomId = params.get("roomId");
            String requestBodyStr = getBodyText(t);

            HashMap<String, Object> bodyParams = null;
            try {
                JSONParser jsonParser = new JSONParser();
                JSONObject json = (JSONObject) jsonParser.parse(requestBodyStr);
                bodyParams = toMap(json);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String userId = (String) bodyParams.get("userId");
            String nickname = (String) bodyParams.get("nickname");
            String ipAddress = (String) bodyParams.get("ipAddress");
            chatHubController.storeUser(userId, nickname, ipAddress);

            String response = chatHubViewer.getChatRoom(chatRoomId);
            sendHTTPResponse(t, response);
        }
    }
}