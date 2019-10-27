package com.example.administrator.WeChats.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.WeChats.R;
import com.example.administrator.WeChats.data.Msg;
import com.example.administrator.WeChats.MsgAdapter;


import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class ActivityTalk extends AppCompatActivity
        implements View.OnClickListener
{
    public static String number;
    public static  String displayName;
    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private RecyclerView msgRecyclerView;
    private MsgAdapter chat_adapter;
    public static void actionStart(Context context,String string) {
        Intent intent = new Intent(context,ActivityTalk.class);
        context.startActivity(intent);
        displayName=string;
    }

    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_talk);

        //-------------------------------------------------------
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(displayName);

        //--------------------------------------------------------
        inputText = findViewById(R.id.input_text);
        String input_Text = load();
        if (!TextUtils.isEmpty(input_Text)) {
            inputText.setText(input_Text);
            inputText.setSelection(input_Text.length());
            Toast.makeText(ActivityTalk.this, "Restoring succeeded", Toast.LENGTH_SHORT).show();
        }

        //------------------------Msgs------------------------------
        initMsgs();
        LinearLayoutManager chat_layoutManager = new LinearLayoutManager(ActivityTalk.this);
        chat_adapter = new MsgAdapter(msgList);

        msgRecyclerView = findViewById(R.id.msg_recycler_view);
        msgRecyclerView.setLayoutManager(chat_layoutManager);
        msgRecyclerView.setAdapter(chat_adapter);

       Button send = findViewById(R.id.send);
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.send:
                {
                    String content = inputText.getText().toString();
                    if (!"".equals(content)) {
                        Msg msg = new Msg(content, Msg.TYPE_SENT);
                        msgList.add(msg);
                        chat_adapter.notifyItemInserted(msgList.size() - 1);
                        msgRecyclerView.scrollToPosition(msgList.size() - 1);
                        inputText.setText("");
                    }
                }break;
            default:break;
        }
    }
    private void initMsgs() {
        for(int i=0;i<3;i++) {
            Msg msg1 = new Msg("hello QQdemo.", Msg.TYPE_RECEIVED);
            msgList.add(msg1);
            Msg msg2 = new Msg("2019/7/26 22:04.", Msg.TYPE_SENT);
            msgList.add(msg2);
            Msg msg3 = new Msg("这是一个例子", Msg.TYPE_RECEIVED);
            msgList.add(msg3);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();// ActivityCollector.removeActivity(this);
        String input_Text = inputText.getText().toString();
        save(input_Text);
    }
    private void save(String input_text) {
        FileOutputStream out ;
        BufferedWriter writer = null;
        try{
            out = openFileOutput("DataSavedInFile", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(input_text);
        }catch (IOException e)  {
            e.printStackTrace();
        } finally {
            try{
                if(writer !=null)
                    writer.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chats_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent= new Intent(ActivityTalk.this,ActivityWeChat.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            default :break;
        }
        return super.onOptionsItemSelected(item);
    }

    public String load(){
        FileInputStream in ;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try{
            in = openFileInput("DataSavedInFile");
            reader = new BufferedReader(new InputStreamReader(in));
            String line ;
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
}
