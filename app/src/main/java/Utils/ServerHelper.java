package Utils;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class ServerHelper {
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

    static public String getBodyText(HttpExchange t) throws IOException {
        InputStream ios = t.getRequestBody();
        StringBuilder msgStringBuilder = new StringBuilder();
        int i;
        while ((i = ios.read()) != -1) {
            msgStringBuilder.append((char) i);
        }
        return msgStringBuilder.toString();
    }

    static public void sendHTTPResponse(HttpExchange t, String response) throws IOException{
        t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
