package Utils;

import org.json.simple.JSONValue;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class MyHttpClient {
    public static String get(String urlString, HashMap<String, String> params) {
        try{
            StringBuilder builder = new StringBuilder(urlString);
            if (params != null){
                builder.append("?");
                for (String key:
                        params.keySet()) {
                    builder.append(key);
                    builder.append('=');
                    builder.append(params.get(key));
                    builder.append('&');
                }
            }

            URL url = new URL(builder.toString());
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("GET");
            http.setDoOutput(true);

            http.connect();
            System.out.println("code="+http.getResponseCode());
            return new String(http.getInputStream().readAllBytes());
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
        public static void post(String urlString, HashMap<String, Object> data){
        try{
            URL url = new URL(urlString);
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            byte[] out = JSONValue.toJSONString(data).getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();
            OutputStream os = http.getOutputStream();
            os.write(out);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
