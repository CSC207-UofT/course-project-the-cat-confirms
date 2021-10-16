package Server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private final int port;
    protected HttpServer server;

    public Server() throws IOException {
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
        initServer();
    }

    public Server(int port) throws IOException {
        this.port = port;
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

        this.server.setExecutor(null); // creates a default executor
        this.server.start();
    }
}