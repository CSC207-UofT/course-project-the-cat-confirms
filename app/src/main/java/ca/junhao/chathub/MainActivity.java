package ca.junhao.chathub;

import Adapters.ChatHubManager;
import Adapters.Controllers.IChatHubController;
import Adapters.Gateways.Repo.IUserRepo;
import Adapters.Gateways.Repo.UserRepo;
import Adapters.Gateways.Server;
import Adapters.Presenters.IChatHubViewer;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.webkit.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    static int CHOOSE_FILE_REQUEST_CODE = 1;
    private ValueCallback<Uri[]> mFilePathCallback = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CHOOSE_FILE_REQUEST_CODE){

            Uri result = data == null || resultCode != RESULT_OK ? null
                    : data.getData();
            if (mFilePathCallback != null) {
                mFilePathCallback.onReceiveValue((result == null)? null : new Uri[]{result});
            }
            mFilePathCallback = null;
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

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

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                Intent chooseImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
                chooseImageIntent.addCategory(Intent.CATEGORY_OPENABLE);
                chooseImageIntent.setType("image/*");

                mFilePathCallback = filePathCallback;

                startActivityForResult(chooseImageIntent, CHOOSE_FILE_REQUEST_CODE);
                return true;
            }
        });

        // setup DB
        String dataFilesPath = getFilesDir().getPath();
        System.out.println(dataFilesPath);
        IUserRepo userRepo = new UserRepo(dataFilesPath + "/user_profile.json");

        // setup controller & presenter
        IChatHubController chatHubController = new ChatHubManager(userRepo);
        IChatHubViewer chatHubViewer = (IChatHubViewer) chatHubController;

        Server srv = null;
        try {
            // this is required for the server to run on Android in a same thread
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            srv = new Server(chatHubController, chatHubViewer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        myWebView.loadUrl("file:///android_asset/web_build/index.html?port=" + srv.getPort());
        // Uncomment this to use the development server
//         myWebView.loadUrl("http://192.168.2.220:3000/?port="+srv.getPort());
    }
}