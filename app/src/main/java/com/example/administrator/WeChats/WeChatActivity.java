package com.example.administrator.WeChats;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WeChatActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static Fragment[] mFragments;
    private FragmentIndicator wechat_fragment;            //定义
    ArrayAdapter<String>talk_adapter;
    private List<String> talkList   = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstaceState)
    {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_wechat);
        setFragmentIndicator(0);

        ActionBar actionBar=getSupportActionBar();
   //     actionBar.setDisplayHomeAsUpEnabled(true);//返回箭头
 //       wechat_fragment=new FragmentIndicator();
//        wechat_fragment.setFragmentIndicator(WeChatActivity.this,0);

        ListView talkView = findViewById(R.id.talk_view);
        //talk_adapter = new ArrayAdapter<>(WeChatActivity.this, android.R.layout.simple_list_item_1, talkList);
        talk_adapter=new ArrayAdapter<>(WeChatActivity.this, android.R.layout.simple_list_item_1, talkList);
        talkView.setAdapter(talk_adapter);
        if (ContextCompat.checkSelfPermission(WeChatActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(WeChatActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        else
            readContacts();
        talkView.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_search:
            {
                Toast.makeText(this,"menu_search",Toast.LENGTH_SHORT).show();
            }break;

            case R.id.menu_add:
            {
               Toast.makeText(this,"menu_add",Toast.LENGTH_SHORT).show();
            }break;
            default :break;
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent talk = new Intent(WeChatActivity.this, ChatActivity.class);
        startActivity(talk);
        finish();
    }

    private void readContacts()
    {
        Cursor cursor = null;
        try{
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
            if(cursor != null)
            {
                while(cursor.moveToNext())
                {
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    talkList.add(displayName + "\n" + number);
                }talk_adapter.notifyDataSetChanged();
            }
        }catch (Exception e)    {e.printStackTrace();}
        finally {
            if(cursor!=null)    cursor.close();
        }
    }

    @Override
    public void  onRequestPermissionsResult(int requestCode, String[] permission, int[] grantResults)
    {
        switch (requestCode)
        {
            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    readContacts();
                else
                    Toast.makeText(WeChatActivity.this, "you denied the permission", Toast.LENGTH_SHORT).show();
        }
    }

    public void setFragmentIndicator(int whichIsDefault)  {                  //初始化fragment
        mFragments = new Fragment[4];
        mFragments[0] = getSupportFragmentManager().findFragmentById(R.id.fragment_wechat);
        mFragments[1] = getSupportFragmentManager().findFragmentById(R.id.fragment_contacts);
        mFragments[2] = getSupportFragmentManager().findFragmentById(R.id.fragment_discover);
        mFragments[3] = getSupportFragmentManager().findFragmentById(R.id.fragment_me);

 //       getSupportFragmentManager().beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]).show(mFragments[whichIsDefault]).commit();                 //显示默认的Fragment

        ViewIndicator mIndicator =  findViewById(R.id.indicator);           //绑定自定义的菜单栏组件
        ViewIndicator.setIndicator(whichIsDefault);
        mIndicator.setOnIndicateListener( new ViewIndicator.OnIndicateListener() {
            @Override
            public void onIndicate(View v, int which) {
                getSupportFragmentManager().beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]).show(mFragments[which]).commit();
                    switch (which) {
                        case 0: break;
                        case 1:
                            Toast.makeText(WeChatActivity.this, "contacts", Toast.LENGTH_SHORT).show();
                            Intent i1 = new Intent(WeChatActivity.this, ContactsActivity.class);
                            i1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(i1);
                            break;
                        case 2:
                            Toast.makeText(WeChatActivity.this, "discover", Toast.LENGTH_SHORT).show();
                            Intent i2 = new Intent(WeChatActivity.this, DiscoverActivity.class);
                            i2.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(i2);
                            break;
                        case 3:
                            Toast.makeText(WeChatActivity.this, "me", Toast.LENGTH_SHORT).show();
                            Intent i3 = new Intent(WeChatActivity.this, MeActivity.class);
                            i3.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(i3);
                            break;
                    }
            }
        });
    }
}
