package com.example.ishimotokiko.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

/**
 * Created by ishimotokiko on 2017/07/22.
 */
/*
public class SocketSingleton {

        private static SocketSingleton instance;
        private static final String SERVER_ADDRESS = "http://1.2.3.4:1234";
    private SocketIO socket;
        private Context context;

        public static SocketSingleton get(Context context){
            if(instance == null){
                instance = getSync(context);
            }
            instance.context = context;
            return instance;
        }

        public static synchronized SocketSingleton getSync(Context context){
            if (instance == null) {
                instance = new SocketSingleton(context);
            }
            return instance;
        }

        public SocketIO getSocket(){
            return this.socket;
        }

        private SocketSingleton(Context context){
            this.context = context;
            this.socket = getChatServerSocket();
            //this.friends = new ArrayList<Friend>();
        }

        private SocketIO getChatServerSocket(){
            try {
                SocketIO socket = new SocketIO(new URL(SERVER_ADDRESS), new IOCallback() {
                    @Override
                    public void onDisconnect() {
                        System.out.println("disconnected");
                    }

                    @Override
                    public void onConnect() {
                        System.out.println("connected");
                    }

                    @Override
                    public void on(String event, IOAcknowledge ioAcknowledge, Object... objects) {
                        if (event.equals("chatMessage")) {

                        }
                    }
                    @Override
                    public void onError(SocketIOException e) {
                        e.printStackTrace();
                    }
                });
                return socket;
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
}*/