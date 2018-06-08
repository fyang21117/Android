package com.example.administrator.qqdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
//import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;

    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //----------------------force offline----------------------
        Button forceOffline = findViewById(R.id.forceoffline);
        forceOffline.setOnClickListener(new View.OnClickListener()
         {
              @Override
             public void onClick(View v)
              {
                  Intent intent = new Intent("com.example.administrator.qqdemo.FORCE_OFFLINE");// send broadcast
                  sendBroadcast(intent);
                  Toast.makeText(MainActivity.this," sendBroadcast(Force to offline!)",Toast.LENGTH_SHORT).show();
              }
          });
        //------------------------Msgs------------------------------
        initMsgs();
        inputText = findViewById(R.id.input_text);
        send = findViewById(R.id.send);
        msgRecyclerView = findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String content = inputText.getText().toString();
                if(!"".equals(content))
                {
                    Msg msg = new Msg(content,Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1);
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    inputText.setText("");
                }
            }
        });
        //------------network------------------
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);
    }
    //------------network------------------
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }
    class NetworkChangeReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            ConnectivityManager connectionManager =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
             NetworkInfo networkInfo= connectionManager.getActiveNetworkInfo();
            if(networkInfo !=null && networkInfo.isAvailable())
            { Toast.makeText(context,"network is available",Toast.LENGTH_SHORT).show();}
            else
                Toast.makeText(context,"network is unavailable",Toast.LENGTH_SHORT).show();

        }
    }

    private void initMsgs()
    {
        Msg msg1 = new Msg("hello QQdemo.",Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("2018/5/26 22:04.",Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("this is 1043 day for us.",Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }
}
