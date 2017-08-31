package com.example.ishimotokiko.chat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import android.os.Handler;
import android.app.Activity;
//import android.util.Log;

import android.view.View;
import android.view.Window;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.github.bassaer.chatmessageview.models.Message;
import com.github.bassaer.chatmessageview.models.User;
import com.github.bassaer.chatmessageview.utils.ChatBot;
import com.github.bassaer.chatmessageview.views.ChatView;
import com.github.bassaer.chatmessageview.views.MessageView;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.emitter.Emitter.Listener;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MainActivity extends Activity {

    //private EditText editText;
    //private ArrayAdapter<String> adapter;
    //private SocketIO socket;

    String roomID = "room1";

    String IPAddress = "http://172.16.12.85:8080";
    MessageView messageView;
    ChatView mChatView;
    int yourId = 2;
    int myId = 1;
    int serverId = 0;
    Bitmap myIcon = null;
    //User name
    String myName = null;
    Bitmap yourIcon = null;
    String yourName = null;
    Bitmap serverIcon = null;
    String serverName = null;

    User[] users = new User[20];
    User me = null;
    User you = null;
    User server = null;

    private Socket mSocket;
    private OverlayManager om;

    private static String convertToOiginal(String unicode)
    {
        String[] codeStrs = unicode.split("%%%u");
        int[] codePoints = new int[codeStrs.length - 1]; // 最初が空文字なのでそれを抜かす
        System.out.println();
        for (int i = 0; i < codePoints.length; i++) {
            codePoints[i] = Integer.parseInt(codeStrs[i + 1], 16);
        }
        String encodedText = new String(codePoints, 0, codePoints.length);
        return encodedText;
    }

    public Emitter.Listener onAddedPost = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    boolean isRight=false;
                    String userid = null;
                    String message =null;
                    String name = null;
                    System.out.println(message);

                    JSONObject data = null;
                    try {
                        data = new JSONObject( (String)args[0] );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        userid = data.getString("id");
                        message = data.getString("text");
                        name = data.getString("name");
                    } catch (JSONException e) {
                        return;
                    }

                    System.out.println(message);
                    if(userid .equals("0")){userid = "0";}
                    else if(!myName.equals(name) && !name.equals("SERVER")){userid = "2";}
                    else isRight=true;

                    System.out.println(userid);
                    User user = new User(Integer.parseInt(userid), name, yourIcon);
                    final Message receivedMessage = new Message.Builder()
                            .setUser(user)
                            .setRightMessage(Integer.parseInt(userid) == myId)
                            .setMessageText(message)
                            .hideIcon(true)
                            .build();

                    mChatView.receive(receivedMessage);


                    int a[]=MessageUtil.getLastMessageLocation(messageView);
                    om.setOverlayArea(a[0],a[1],isRight);
                    om.show();
                    // add the message to view
                }
            });
        }

    };
    public Emitter.Listener onJoinMsg = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("onjoinMessage");
                    String username = null;
                    String message = null;
                    String id = null;
                    JSONObject data = null;
                    try {
                        data = new JSONObject( (String)args[0] );

                        try {
                            username = data.getString("name");
                            message = new String(data.getString("text").getBytes("UTF-8"), "UTF-8");
                            id = data.getString("id");
                        } catch (JSONException e) {
                            return;
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    System.out.println(username);
                    System.out.println(message);
                    System.out.println(id);

                    final Message receivedMessage = new Message.Builder()
                            .setUser(server)
                            .setRightMessage(false)
                            .setMessageText(message)
                            .hideIcon(true)
                            .build();

                    mChatView.receive(receivedMessage);
                    int a[]=MessageUtil.getLastMessageLocation(messageView);
                    om.setOverlayArea(a[0],a[1],false);
                    om.show();
                    // add the message to view
                }
            });
        }

    };

    public Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonOneData = null;
                    try {
                        JSONObject jsonObject = new JSONObject();

                        // jsonデータの作成
                        jsonOneData = new JSONObject();
                        jsonOneData.put("room",roomID);
                        jsonOneData.put("name", myName);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    System.out.println(jsonOneData);
                    mSocket.emit("join", jsonOneData);
                }
            });
        }

    };
    /*User makeuser(String name){
        you = new User(yourId, yourName, yourIcon);
    }*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //mSocket.on

        startActivityForResult(new Intent(this,ConnectActivity.class),ConnectActivity.CODE_REQUEST);
        messageView = (MessageView) findViewById(R.id.message_view);
        mChatView = (ChatView) findViewById(R.id.chat_view);



        myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_2);
        //User name
        myName = "Android";

        yourIcon = BitmapFactory.decodeResource(getResources(), R.drawable.face_1);
        yourName = "iPhone";

        serverIcon = null;
        serverName = "SERVER";

        you = new User(yourId, yourName, yourIcon);
        server = new User(serverId,serverName,serverIcon);







        //User id
        //User icon

        mChatView = (ChatView)findViewById(R.id.chat_view);

        //Set UI parameters if you need
        mChatView.setRightBubbleColor(ContextCompat.getColor(this, R.color.green500));
        mChatView.setLeftBubbleColor(Color.WHITE);
        mChatView.setBackgroundColor(ContextCompat.getColor(this, R.color.blueGray500));
        mChatView.setSendButtonColor(ContextCompat.getColor(this, R.color.cyan900));
        mChatView.setSendIcon(R.drawable.ic_action_send);
        mChatView.setRightMessageTextColor(Color.WHITE);
        mChatView.setLeftMessageTextColor(Color.BLACK);
        mChatView.setUsernameTextColor(Color.WHITE);
        mChatView.setSendTimeTextColor(Color.WHITE);
        mChatView.setDateSeparatorColor(Color.WHITE);
        mChatView.setInputTextHint("new message...");
        mChatView.setMessageMarginTop(5);
        mChatView.setMessageMarginBottom(5);



        //Click Send Button
        mChatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONObject jsonOneData = null;
                try {
                    JSONObject jsonObject = new JSONObject();
                    // jsonデータの作成
                    jsonOneData = new JSONObject();
                    jsonOneData.put("room",roomID);
                    jsonOneData.put("id",String.valueOf(me.getId()));
                    jsonOneData.put("text", mChatView.getInputText());
                    jsonOneData.put("name", myName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println(jsonOneData);
                mSocket.emit("post", jsonOneData);
                //Reset edit text
                mChatView.setInputText("");

            }

        });
        om=new OverlayManager(this);

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        Bundle b=data.getExtras();
        switch (requestCode){
            case ConnectActivity.CODE_REQUEST:
                IPAddress="http://"+b.getString(ConnectActivity.KEY_ADDR)+":"+b.getString(ConnectActivity.KEY_PORT);
                myName=b.getString(ConnectActivity.KEY_NAME);
                roomID = b.getString(ConnectActivity.KEY_ROOMID);
                me = new User(myId, myName, myIcon);

                {
                    try {
                        mSocket = IO.socket(IPAddress);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }System.out.println("onConnect");
                }

                mSocket.on("connect", onConnect);
                mSocket.on("Message", onAddedPost);
                mSocket.on("joinMessage", onJoinMsg);
                mSocket.connect();



        }
    }
}
