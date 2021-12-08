package ca.junhao.chathub;

import Adapters.ChatHubManager;
import Adapters.Controllers.IChatHubController;
import Adapters.Gateways.Repo.IUserRepo;
import Adapters.Gateways.Repo.UserRepo;
import Adapters.Gateways.Server;
import Adapters.Presenters.IChatHubViewer;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup webview for the web front
        WebView myWebView = findViewById(R.id.webview);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setAllowFileAccess(true);
        myWebView.getSettings().setAllowContentAccess(true);

        // Reference: https://stackoverflow.com/questions/18271991/html5-video-remove-overlay-play-icon
        // Reference: https://github.com/mozmorris/react-webcam/issues/211
        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                request.grant(request.getResources());
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d("MyApplication", consoleMessage.message() + " -- From line " +
                        consoleMessage.lineNumber() + " of " + consoleMessage.sourceId());
                return true;
            }
        });

        String dataFilesPath = getFilesDir().getPath();
        System.out.println(dataFilesPath);
        IUserRepo userRepo = new UserRepo(dataFilesPath + "/user_profile.json");

        IChatHubController chatHubController = new ChatHubManager(userRepo);
        IChatHubViewer chatHubViewer = (IChatHubViewer) chatHubController;

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Server srv = new Server(chatHubController, chatHubViewer);

            myWebView.loadUrl("file:///android_asset/web_build/index.html?port=" + srv.getPort());

            // Uncomment this to use the development server
            // myWebView.loadUrl("http://192.168.2.69:3000/?port="+srv.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}