package com.example.administrator.WeChats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class WeChatActivity extends AppCompatActivity {

    public static Fragment[] mFragments;
    private List<Chats> chatsList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstaceState)
    {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_wechat);

        setFragmentIndicator(0);

        initChats();
        ListView menuView = findViewById(R.id.menu_listview);           //定义初始化
        ChatsAdapter chatsAdapter = new ChatsAdapter(WeChatActivity.this,R.layout.chats_item,chatsList);//应用，获取资源，//activity_homepage崩溃
        menuView.setAdapter(chatsAdapter);
    }

    private void initChats()
    {
        for(int i=0;i<6;i++)
        {
            Chats boy = new Chats("小明"+i,R.drawable.bai);
            chatsList.add(boy);
            Chats girl = new Chats("小红"+i,R.drawable.hong);
            chatsList.add(girl);
        }
    }

    private void setFragmentIndicator(int whichIsDefault) {     //初始化fragment
        mFragments = new Fragment[4];
        mFragments[0] = getSupportFragmentManager().findFragmentById(R.id.fragment_wechat);
        mFragments[1] = getSupportFragmentManager().findFragmentById(R.id.fragment_contacts);
        mFragments[2] = getSupportFragmentManager().findFragmentById(R.id.fragment_discover);
        mFragments[3] = getSupportFragmentManager().findFragmentById(R.id.fragment_me);

        getSupportFragmentManager().beginTransaction().hide(mFragments[0]).hide(mFragments[1])      //显示默认的Fragment
                .hide(mFragments[2]).hide(mFragments[3]).show(mFragments[whichIsDefault]).commit();

        ViewIndicator mIndicator =  findViewById(R.id.indicator);//绑定自定义的菜单栏组件
        ViewIndicator.setIndicator(whichIsDefault);
        mIndicator.setOnIndicateListener(new ViewIndicator.OnIndicateListener() {
            @Override
            public void onIndicate(View v, int which) {
                getSupportFragmentManager().beginTransaction().hide(mFragments[0]).hide(mFragments[1])
                        .hide(mFragments[2]).hide(mFragments[3]).show(mFragments[which]).commit();

                if(which == 1)
                {
                Intent i1 = new Intent(WeChatActivity.this,ChatActivity.class);
                startActivity(i1);
                finish();
                }
                if(which == 3)
                {
                Intent i2 = new Intent(WeChatActivity.this,MeActivity.class);
                startActivity(i2);
                finish();
                }
            }
        });
    }
}
