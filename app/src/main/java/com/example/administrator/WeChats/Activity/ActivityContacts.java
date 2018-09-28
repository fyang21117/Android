package com.example.administrator.WeChats.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

//import com.example.administrator.WeChats.DataArrayAdapter;
import com.example.administrator.WeChats.R;
import com.example.administrator.WeChats.ViewIndicator;
import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.WeChats.FragmentIndicator.setFragmentIndicator;

public class ActivityContacts extends AppCompatActivity
        implements AdapterView.OnItemClickListener,ViewIndicator.OnIndicateListener
{
   public static Fragment[] mFragments;
    ArrayAdapter<String> contacts_adapter;
    private List<String> contactsList=new ArrayList<>();//数据源
    public static ViewIndicator mIndicator;
    public static void actionStart(Context context) {
        Intent intent=new Intent(context,ActivityContacts.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar !=null)
           actionBar.setDisplayHomeAsUpEnabled(false);
//-------------------- readContacts-----------------------------------------------------
        ListView ContactsView = findViewById(R.id.contacts_view);
        contacts_adapter = new ArrayAdapter<>(ActivityContacts.this, android.R.layout.simple_list_item_1, contactsList);
        ContactsView.setAdapter(contacts_adapter);
        if (ContextCompat.checkSelfPermission(ActivityContacts.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(ActivityContacts.this,new String[]{Manifest.permission.READ_CONTACTS}, 2);
         else
            readContacts();
        ContactsView.setOnItemClickListener(this);

        setFragmentIndicator(1);
        //mIndicator = findViewById(R.id.indicator);
        //FragmentIndicator.setFragmentIndicator(1);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position) + "";// 指定位置的内容
        Toast.makeText(ActivityContacts.this,text,Toast.LENGTH_SHORT).show();
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
    private void readContacts() {
        Cursor cursor =null;
        try{
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
            if(cursor != null) {
                while(cursor.moveToNext())
                {   String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactsList.add(displayName  + number+ "\n");
                }contacts_adapter.notifyDataSetChanged();
            }
        }catch (Exception e)    { e.printStackTrace(); }
        finally {
            if(cursor!=null)
                cursor.close();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
            @NonNull String[] permission,@NonNull int[] grantResults) {
        switch (requestCode) {
            case 2:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    readContacts();
                else
                    Toast.makeText(ActivityContacts.this, "you denied the permission", Toast.LENGTH_SHORT).show();
        }
    }


    public void setFragmentIndicator(int whichIsDefault)  {
        mFragments = new Fragment[4];
        mFragments[0] = getSupportFragmentManager().findFragmentById(R.id.fragment_wechat);
        mFragments[1] = getSupportFragmentManager().findFragmentById(R.id.fragment_contacts);
        mFragments[2] = getSupportFragmentManager().findFragmentById(R.id.fragment_discover);
        mFragments[3] = getSupportFragmentManager().findFragmentById(R.id.fragment_me);
        getSupportFragmentManager().beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]).show(mFragments[whichIsDefault]).commit();
        ViewIndicator mIndicator =  findViewById(R.id.indicator);
        ViewIndicator.setIndicator(whichIsDefault);
        mIndicator.setOnIndicateListener(this);
    }
        @Override
        public void onIndicate(View v, int which) {
            getSupportFragmentManager().beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]).show(mFragments[which]).commit();
            switch (which) {
                case 0:ActivityWeChat.actionStart(this);
                    break;
                case 1:
                    ActivityContacts.actionStart(this);
                    break;
                case 2:
                    ActivityDiscover.actionStart(this);
                    break;
                case 3:
                    ActivityMe.actionStart(this);
                    break;
                default:break;
            }
        }
}
