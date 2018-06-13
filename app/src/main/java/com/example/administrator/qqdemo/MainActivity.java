package com.example.administrator.qqdemo;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
//import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
//import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    private NetworkChangeReceiver networkChangeReceiver;
    private ForceOfflineReceiver forceOfflineReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.input_text);
        String input_Text  = load();
        if(!TextUtils.isEmpty(input_Text)){
            inputText.setText(input_Text);
            inputText.setSelection(input_Text.length());
            Toast.makeText(this,"Restoring succeeded",Toast.LENGTH_SHORT).show();
        }
        //------------------------Msgs------------------------------
        initMsgs();
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
        //----------------------network----------------------
        IntentFilter intentFilter_network = new IntentFilter();
        intentFilter_network.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter_network);
        //  Toast.makeText(MainActivity.this,"registerReceiver_NetworkChange!",Toast.LENGTH_SHORT).show();

        //----------------------force offline----------------------
        Button forceOffline = findViewById(R.id.forceoffline);
        forceOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent("com.example.administrator.qqdemo.FORCE_OFFLINE");// send broadcast
                sendBroadcast(intent);
                Toast.makeText(MainActivity.this," sendBroadcast(Force to offline!)",Toast.LENGTH_SHORT).show();
            }
        });
    }

    class ForceOfflineReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(final Context context,Intent intent)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Warning");
            builder.setMessage("You are forced to be offline.Please try to login again");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCollector.finishAll();
                    Intent intent = new Intent(context,LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
         //   builder.create().getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            builder.show();
        }
    }
    //-------------------------network-------------------------------
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

    @Override
    protected  void onResume()
    {
        super.onResume();
        //----------------------force_offline----------------------
        IntentFilter intentFilter_force = new IntentFilter();
        intentFilter_force.addAction("com.example.administrator.qqdemo.FORCE_OFFLINE");//receive broadcast
        forceOfflineReceiver = new ForceOfflineReceiver();
        registerReceiver(forceOfflineReceiver,intentFilter_force);
        // Toast.makeText(MainActivity.this,"registerReceiver_ForceOffline!",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected  void onPause()
    {
        super.onPause();
        if(forceOfflineReceiver != null)
        {
            unregisterReceiver(forceOfflineReceiver);
            forceOfflineReceiver = null;
        }
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(networkChangeReceiver != null)
        {
            unregisterReceiver(networkChangeReceiver);
            networkChangeReceiver = null;
        }
       // ActivityCollector.removeActivity(this);
        String input_Text = inputText.getText().toString();
        save(input_Text);
    }

    private void save(String input_text)
    {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try{
            out = openFileOutput("DataSavedInFile",Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(input_text);
        }catch (IOException e){e.printStackTrace();}
        finally {
            try{
                if(writer !=null)
                    writer.close();
            }catch (IOException e){e.printStackTrace();}
        }
    }
    public String load(){
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try{
            in = openFileInput("DataSavedInFile");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while((line = reader.readLine())!= null){
                content.append(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch (IOException e){e.printStackTrace();}
            }
        }
        return content.toString();
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
