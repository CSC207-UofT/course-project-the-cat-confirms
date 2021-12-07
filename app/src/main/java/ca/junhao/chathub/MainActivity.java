package ca.junhao.chathub;

import Entities.User;
import Gateways.ChatroomManager;
import Gateways.Repo.UserRepo;
import Gateways.Server;
import UseCases.UserProfile;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.*;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.IOException;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    static int FILECHOOSER_RESULTCODE = 999;

    private ValueCallback<Uri[]> mUploadMessage;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) return;

            Uri result = intent == null || resultCode != RESULT_OK ? null
                    : intent.getData();

            System.out.println(intent.getExtras().containsKey("data"));

            if (result != null){
                mUploadMessage.onReceiveValue(new Uri[]{result});
            } else{
                mUploadMessage.onReceiveValue(null);
            }
            mUploadMessage = null;
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
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
//            @Override
//            public Bitmap getDefaultVideoPoster() {
//                return Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888);
//            }

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
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mUploadMessage = filePathCallback;

                startActivityForResult(intent, FILECHOOSER_RESULTCODE);
                return true;
            }
        });
        String dataFilesPath = getFilesDir().getPath();
        System.out.println(dataFilesPath);
        UserRepo userRepo = new UserRepo(""); // ("dataFilesPath+"/user_profile.json"");
        if (!userRepo.isReady()) {
            System.out.println("damn");
            userRepo.initRepo("");
        }
        User owner = userRepo.getOwner();
        System.out.println(owner);

        UserProfile userProfile = new UserProfile(owner);
        ChatroomManager chatroomManager = new ChatroomManager(owner);

        Server srv = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            srv = new Server(userProfile, chatroomManager, userRepo);
            owner.setIpAddress(srv.getHostIP()+':'+srv.getPort());
            System.out.println("Listening on: " + owner.getIpAddress());

//            myWebView.loadUrl("http://192.168.2.69:3000/?port="+srv.getPort());
            myWebView.loadUrl("file:///android_asset/web_build/index.html?port="+srv.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}