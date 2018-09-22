package com.example.administrator.WeChats;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityLogin extends AppCompatActivity
{
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPass;
    private EditText accountEdit;
    private EditText passwordEdit;
    private NetworkChangeReceiver networkChangeReceiver;
    Boolean network_status;

    public  static  void actionStart(Context context)
    {
        Intent intent = new Intent(context,ActivityLogin.class);
        context.startActivity(intent);
    }
    @Override
    protected  void onCreate(Bundle savedInstaceState)
    {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_login);
        //----------------------network----------------------
        IntentFilter intentFilter_network = new IntentFilter();
        intentFilter_network.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter_network);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        accountEdit = findViewById(R.id.account);
        passwordEdit = findViewById(R.id.password);
        rememberPass = findViewById(R.id.remember_pass);
        Button login = findViewById(R.id.login);

        boolean isRemember = pref.getBoolean("remember_password",false);
        if(isRemember){
            String account = pref.getString("account","");
            String password = pref.getString("password","");        //读取键值对
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                if(account.equals("fyang") && password.equals("123456") && network_status) {//密码加密！改进
                    editor = pref.edit();
                    if(rememberPass.isChecked()){
                        editor.putBoolean("remember_password",true);
                        editor.putString("account",account);                    //存储键值对
                        editor.putString("password",password);
                    }else
                         {    editor.clear();   }
                    editor.apply();


                    Intent login = new Intent(ActivityLogin.this,ActivityWeChat.class);
                    startActivity(login);
                    finish();
                }
                else if(!network_status)
                     { Toast.makeText(ActivityLogin.this,"network is invalid",Toast.LENGTH_SHORT).show(); }
                else
                    { Toast.makeText(ActivityLogin.this,"account or password is invalid",Toast.LENGTH_SHORT).show(); }
            }
        });
    }
    //-------------------------network-------------------------------
    class NetworkChangeReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            ConnectivityManager connectionManager =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo= connectionManager.getActiveNetworkInfo();
            network_status=(networkInfo !=null && networkInfo.isAvailable());
//            if()
//                { Toast.makeText(context,"network is available",Toast.LENGTH_SHORT).show();}
//            else
//                Toast.makeText(context,"network is unavailable",Toast.LENGTH_SHORT).show();
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
    }
}
