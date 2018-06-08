package com.example.administrator.qqdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
//import android.support.v7.app.AlertDialog;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity
{
    private ForceOfflineReceiver receiver;
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
            builder.create().getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            builder.show();
        }
    }

    @Override
    protected void onCreate(Bundle saveInstaceState)
    {
        super.onCreate(saveInstaceState);
        ActivityCollector.addActivity(this);
    }
    @Override
    protected  void onResume()
    {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.administrator.qqdemo.FORCE_OFFLINE");//receive broadcast
        receiver = new ForceOfflineReceiver();
        registerReceiver(receiver,intentFilter);
        Toast.makeText(BaseActivity.this,"receiveBroadcast(Force to offline!)",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected  void onPause()
    {
        super.onPause();
        if(receiver !=null)
        {
            unregisterReceiver(receiver);
            receiver=null;
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

}
