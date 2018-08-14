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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends MeActivity
{
    private SharedPreferences pref;
    private  SharedPreferences.Editor editor;
    private CheckBox rememberPass;
    private EditText accountEdit;
    private EditText passwordEdit;
    private Button login;
    Boolean network_status;
    private NetworkChangeReceiver networkChangeReceiver;

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
        login = findViewById(R.id.login);
        boolean isRemember = pref.getBoolean("remember_password",false);
        if(isRemember){     //true
            String account = pref.getString("account","");
            String password = pref.getString("password","");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                if(account.equals("fyang") && password.equals("123456") && network_status) {
                    editor = pref.edit();
                    if(rememberPass.isChecked()){   //if checked,display account and passwd
                        editor.putBoolean("remember_password",true);
                        editor.putString("account",account);
                        editor.putString("password",password);
                    }else
                    {    editor.clear();   }
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this,HomepageActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(!network_status)
                     { Toast.makeText(LoginActivity.this,"network is invalid",Toast.LENGTH_SHORT).show(); }
                else
                    { Toast.makeText(LoginActivity.this,"account or password is invalid",Toast.LENGTH_SHORT).show(); }
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
            if(network_status=(networkInfo !=null && networkInfo.isAvailable()))
            { Toast.makeText(context,"network is available",Toast.LENGTH_SHORT).show();}
            else
                Toast.makeText(context,"network is unavailable",Toast.LENGTH_SHORT).show();
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
        }// ActivityCollector.removeActivity(this);
    }

}
