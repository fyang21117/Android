package com.example.administrator.WeChats.Activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.WeChats.ActivityCollector;
import com.example.administrator.WeChats.R;
import com.example.administrator.WeChats.ViewIndicator;

public class ActivityMe extends AppCompatActivity
        implements View.OnClickListener,ViewIndicator.OnIndicateListener
{
    private ForceOfflineReceiver forceOfflineReceiver;
    public static Fragment[] mFragments;

    public static void actionStart(Context context) {
        Intent intent=new Intent(context,ActivityMe.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
           actionBar.setDisplayHomeAsUpEnabled(false);
        setFragmentIndicator(3);
//        FragmentIndicator me_fragment=new FragmentIndicator();
//        me_fragment.setFragmentIndicator(ActivityMe.this,3);

        //--------------------------------------------
        Button forceOffline = findViewById(R.id.forceoffline);
        Button about_me = findViewById(R.id.about_me);
        Button setting = findViewById(R.id.settings);
        Button logout = findViewById(R.id.logout);
        forceOffline.setOnClickListener(this);
        about_me.setOnClickListener(this);
        setting.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forceoffline: {
                Intent intent = new Intent("com.example.administrator.WeChats.FORCE_OFFLINE");// send broadcast
                sendBroadcast(intent);
                Toast.makeText(ActivityMe.this," sendBroadcast(Force to offline!)",Toast.LENGTH_SHORT).show();
                }break;

            case R.id.about_me: {
                Toast.makeText(this,"ID:21117",Toast.LENGTH_SHORT).show();
                }break;
            case R.id.settings: {
                Toast.makeText(this,"systems",Toast.LENGTH_SHORT).show();
            }break;
            case R.id.logout: {
                ActivityLogin.actionStart(this);
                finish();
            }break;
            default:break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search: {
                Toast.makeText(this,"menu_search",Toast.LENGTH_SHORT).show();
            }break;

            case R.id.menu_add: {
                Toast.makeText(this,"menu_add",Toast.LENGTH_SHORT).show();
            }break;
            default :break;
        }
        return super.onOptionsItemSelected(item);
    }
    class ForceOfflineReceiver extends BroadcastReceiver implements DialogInterface.OnClickListener
    {
        @Override
        public void onReceive(final Context context,Intent intent)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);     //创建构建器
            builder.setTitle("Warning");
            builder.setMessage("You are forced to be offline.Please try to login again");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", this);
            builder.show();
        }
        @Override
        public void onClick(DialogInterface dialog, int which) {
            ActivityCollector.finishAll();
            ActivityLogin.actionStart(ActivityMe.this);
        }
    }

    @Override
    protected  void onResume() {
        super.onResume();
        //----------------------force_offline----------------------
        IntentFilter intentFilter_force = new IntentFilter();
        intentFilter_force.addAction("com.example.administrator.WeChats.FORCE_OFFLINE");//receive broadcast
        forceOfflineReceiver = new ForceOfflineReceiver();
        registerReceiver(forceOfflineReceiver,intentFilter_force);
    }
    @Override
    protected  void onPause() {
        super.onPause();
        if(forceOfflineReceiver != null) {
            unregisterReceiver(forceOfflineReceiver);
            forceOfflineReceiver = null;
        }
    }

    private void setFragmentIndicator(int whichIsDefault) {     //初始化fragment
        mFragments = new Fragment[4];
        mFragments[0] = getSupportFragmentManager().findFragmentById(R.id.fragment_wechat);
        mFragments[1] = getSupportFragmentManager().findFragmentById(R.id.fragment_contacts);
        mFragments[2] = getSupportFragmentManager().findFragmentById(R.id.fragment_discover);
        mFragments[3] = getSupportFragmentManager().findFragmentById(R.id.fragment_me);
        getSupportFragmentManager().beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]).show(mFragments[whichIsDefault]).commit();

        ViewIndicator mIndicator = findViewById(R.id.indicator);//绑定自定义的菜单栏组件
        ViewIndicator.setIndicator(whichIsDefault);
        mIndicator.setOnIndicateListener(this);
    }
            @Override
            public void onIndicate(View v, int which) {
                getSupportFragmentManager().beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]).show(mFragments[which]).commit();
                switch (which) {
                    case 0:ActivityWeChat.actionStart(this);
                        break;
                    case 1: ActivityContacts.actionStart(this);
                        break;
                    case 2: ActivityDiscover.actionStart(this);
                        break;
                    case 3: ActivityMe.actionStart(this);
                        break;
                    default:break;
                }
            }
}
