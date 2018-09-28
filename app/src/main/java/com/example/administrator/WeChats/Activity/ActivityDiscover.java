package com.example.administrator.WeChats.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.WeChats.DataArrayAdapter;
import com.example.administrator.WeChats.FragmentIndicator;
import com.example.administrator.WeChats.R;
import com.example.administrator.WeChats.ViewIndicator;
import com.example.administrator.WeChats.data.Friends;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.WeChats.FragmentIndicator.setFragmentIndicator;

public class ActivityDiscover extends AppCompatActivity
        implements View.OnClickListener,ViewIndicator.OnIndicateListener
{
    public static Fragment[] mFragments;
    public static ViewIndicator mIndicator;
    public static void actionStart(Context context) {
        Intent intent=new Intent(context,ActivityDiscover.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar !=null)
            actionBar.setDisplayHomeAsUpEnabled(false);

        setFragmentIndicator(2);
        //mIndicator = findViewById(R.id.indicator);
        //FragmentIndicator.setFragmentIndicator(2);

        Button circle = findViewById(R.id.circle);
        Button scan = findViewById(R.id.scan);
        Button search = findViewById(R.id.search);
        Button dataActivity = findViewById(R.id.dataActivity);
        circle.setOnClickListener(this);
        scan.setOnClickListener(this);
        search.setOnClickListener(this);
        dataActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.circle:
                ActivityCircle.actionStart(this);
                break;
            case R.id.scan:
            {
                Toast.makeText(this,"scan",Toast.LENGTH_SHORT).show();
            }break;
            case R.id.search:
            {
                Toast.makeText(this,"search",Toast.LENGTH_SHORT).show();
            }break;
            case R.id.dataActivity:
            {
                DataActivity.actionStart(this);
            }break;
            default :break;
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


    private void setFragmentIndicator(int whichIsDefault) {
        mFragments = new Fragment[4];
        mFragments[0] = getSupportFragmentManager().findFragmentById(R.id.fragment_wechat);
        mFragments[1] = getSupportFragmentManager().findFragmentById(R.id.fragment_contacts);
        mFragments[2] = getSupportFragmentManager().findFragmentById(R.id.fragment_discover);
        mFragments[3] = getSupportFragmentManager().findFragmentById(R.id.fragment_me);
        getSupportFragmentManager().beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]).show(mFragments[whichIsDefault]).commit();
        ViewIndicator mIndicator = findViewById(R.id.indicator);
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
