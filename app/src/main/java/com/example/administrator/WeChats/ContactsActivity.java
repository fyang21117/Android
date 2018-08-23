package com.example.administrator.WeChats;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {
    public static Fragment[] mFragments;
    ArrayAdapter<String> contacts_adapter;
    private List<String> contactsList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        setFragmentIndicator(1);
        ListView ContactsView = findViewById(R.id.contacts_view);
        contacts_adapter = new ArrayAdapter<>(ContactsActivity.this, android.R.layout.simple_list_item_1, contactsList);
        ContactsView.setAdapter(contacts_adapter);
        if (ContextCompat.checkSelfPermission(ContactsActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(ContactsActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 2);
        else readContacts();

        ContactsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ContactsActivity.this,"displayname",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void readContacts()
    {
        Cursor cursor =null;
        try{
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
            if(cursor != null)
            {
                while(cursor.moveToNext())
                {   String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactsList.add(displayName  + number+ "\n");
                }contacts_adapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permission,int[] grantResults)
    {
        switch (requestCode)
        {
            case 2:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    readContacts();
                else
                    Toast.makeText(ContactsActivity.this, "you denied the permission", Toast.LENGTH_SHORT).show();
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
                switch (which) {
                    case 0:
                        Toast.makeText(ContactsActivity.this, "wechat", Toast.LENGTH_SHORT).show();
                        Intent i0 = new Intent(ContactsActivity.this, WeChatActivity.class);
                        i0.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i0);
                        break;
                    case 1: break;
                    case 2:
                        Toast.makeText(ContactsActivity.this, "discover", Toast.LENGTH_SHORT).show();
                        Intent i2 = new Intent(ContactsActivity.this, DiscoverActivity.class);
                        i2.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i2);
                        break;
                    case 3:
                        Toast.makeText(ContactsActivity.this, "me", Toast.LENGTH_SHORT).show();
                        Intent i3 = new Intent(ContactsActivity.this, MeActivity.class);
                        i3.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i3);
                        break;
                }

            }
        });
    }
}
