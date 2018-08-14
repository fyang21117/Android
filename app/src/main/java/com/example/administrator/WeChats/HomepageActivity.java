package com.example.administrator.WeChats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomepageActivity extends AppCompatActivity {

    private List<Chats> chatsList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstaceState)
    {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_homepage);

        initChats();
        ListView menuView = findViewById(R.id.menu_listview);
        ChatsAdapter chatsAdapter = new ChatsAdapter(HomepageActivity.this,R.layout.chats_item,chatsList);//activity_homepage
        menuView.setAdapter(chatsAdapter);

        //----------------------HomepageActivity-----------------------------
        ImageButton WeChat = findViewById(R.id.WeChat);
        ImageButton Contacts = findViewById(R.id.Contacts);
        ImageButton Discover = findViewById(R.id.Discover);
        ImageButton Me  = findViewById(R.id.Me);
        WeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomepageActivity.this,"WeChat",Toast.LENGTH_SHORT).show();
                Intent i1 = new Intent(HomepageActivity.this,ChatActivity.class);
                startActivity(i1);
                finish();
            }
        });
        Contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomepageActivity.this,"Contacts",Toast.LENGTH_SHORT).show();
            }
        });
        Discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomepageActivity.this,"Discover",Toast.LENGTH_SHORT).show();
            }
        });
        Me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomepageActivity.this,"Me",Toast.LENGTH_SHORT).show();
                Intent i2 = new Intent(HomepageActivity.this,MeActivity.class);
                startActivity(i2);
                finish();
            }
        });
    }

    private void initChats()
    {
        for(int i=0;i<6;i++)
        {
            Chats boy = new Chats("小明",R.drawable.bai);
            chatsList.add(boy);
            Chats girl = new Chats("小红",R.drawable.hong);
            chatsList.add(girl);

        }
    }
}
