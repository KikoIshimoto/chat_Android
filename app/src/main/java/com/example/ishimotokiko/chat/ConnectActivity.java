package com.example.ishimotokiko.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * Created by abedaigorou on 2017/08/08.
 */

public class ConnectActivity extends Activity {
    public final static String KEY_NAME="KEY_NAME";
    public final static String KEY_ADDR="KEY_ADDR";
    public final static String KEY_PORT="KEY_PORT";
    public final static String KEY_ROOMID="KEY_ROOMID";
    public final static int CODE_REQUEST=123;

    private String TAG="ConnectActivity";

    private EditText nameEdit,IPEdit,portEdit,roomedit;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        nameEdit=(EditText)findViewById(R.id.name_edit);
        IPEdit=(EditText)findViewById(R.id.IP_edit);
        portEdit=(EditText)findViewById(R.id.port_edit);
        roomedit=(EditText)findViewById(R.id.edit_room);
    }

    public void onConnectClick(View v){
        Bundle bundle=new Bundle();
        Intent intent=new Intent();
        bundle.putString(KEY_NAME,nameEdit.getText().toString());
        bundle.putString(KEY_ADDR,IPEdit.getText().toString());
        bundle.putString(KEY_PORT,portEdit.getText().toString());
        bundle.putString(KEY_ROOMID,roomedit.getText().toString());
        intent.putExtras(bundle);
        setResult(CODE_REQUEST,intent);
        finish();
    }
}
