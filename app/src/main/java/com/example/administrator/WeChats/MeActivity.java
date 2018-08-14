package com.example.administrator.WeChats;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MeActivity extends AppCompatActivity
{
    private ForceOfflineReceiver forceOfflineReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        //----------------------force offline----------------------
        Button forceOffline = findViewById(R.id.forceoffline);
        forceOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent("com.example.administrator.WeChats.FORCE_OFFLINE");// send broadcast
                sendBroadcast(intent);
                Toast.makeText(MeActivity.this," sendBroadcast(Force to offline!)",Toast.LENGTH_SHORT).show();
            }
        });
    }

    class ForceOfflineReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(final Context context,Intent intent)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);     //创建构建器
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

    @Override
    protected  void onResume()
    {
        super.onResume();
        //----------------------force_offline----------------------
        IntentFilter intentFilter_force = new IntentFilter();
        intentFilter_force.addAction("com.example.administrator.WeChats.FORCE_OFFLINE");//receive broadcast
        forceOfflineReceiver = new ForceOfflineReceiver();
        registerReceiver(forceOfflineReceiver,intentFilter_force);
        // Toast.makeText(MeActivity.this,"registerReceiver_ForceOffline!",Toast.LENGTH_SHORT).show();
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

}
