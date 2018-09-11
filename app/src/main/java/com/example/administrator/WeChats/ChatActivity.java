package com.example.administrator.WeChats;


import android.content.Context;
import android.os.Bundle;
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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{

    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private RecyclerView msgRecyclerView;
    private MsgAdapter chat_adapter;
    private Button send;
    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_talk);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        inputText = findViewById(R.id.input_text);
        String input_Text = load();
        if (!TextUtils.isEmpty(input_Text)) {
            inputText.setText(input_Text);
            inputText.setSelection(input_Text.length());
            Toast.makeText(ChatActivity.this, "Restoring succeeded", Toast.LENGTH_SHORT).show();
        }
        //------------------------Msgs------------------------------
        initMsgs();
        msgRecyclerView = findViewById(R.id.msg_recycler_view);
        LinearLayoutManager chat_layoutManager = new LinearLayoutManager(ChatActivity.this);
        chat_adapter = new MsgAdapter(msgList);

        msgRecyclerView.setLayoutManager(chat_layoutManager);
        msgRecyclerView.setAdapter(chat_adapter);

        send = findViewById(R.id.send);
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
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
    private void initMsgs()
    {
        for(int i=0;i<6;i++) {

            Msg msg1 = new Msg("hello QQdemo.", Msg.TYPE_RECEIVED);
            msgList.add(msg1);
            Msg msg2 = new Msg("2018/5/26 22:04.", Msg.TYPE_SENT);
            msgList.add(msg2);
            Msg msg3 = new Msg("this is 1043 day for us.", Msg.TYPE_RECEIVED);
            msgList.add(msg3);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();// ActivityCollector.removeActivity(this);
        String input_Text = inputText.getText().toString();
        save(input_Text);
    }
    private void save(String input_text)
    {
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
        switch (item.getItemId())
        {
            case android.R.id.home:
            {
                finish();
            }break;
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
