package com.example.administrator.WeChats.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.WeChats.DataArrayAdapter;
import com.example.administrator.WeChats.R;
import com.example.administrator.WeChats.data.Friends;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataActivity extends AppCompatActivity implements View.OnClickListener
{
    public static void actionStart(Context context) {
        Intent intent=new Intent(context,DataActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
    }
    //------------------------------------------------------
    public static int i=0;
    private ListView mDataListView;
    private DataArrayAdapter mAdapter;
    private List<List<String>> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar !=null)
            actionBar.setDisplayHomeAsUpEnabled(true);//返回箭头显示出来
        //---------------------------------------------------------
        Button createDatabase = findViewById(R.id.createdb);
        Button addData =findViewById(R.id.addData);
        Button deleteData=findViewById(R.id.deleteData);
        createDatabase.setOnClickListener(this);
        addData.setOnClickListener(this);
        deleteData.setOnClickListener(this);

        mDataListView = findViewById(R.id.data_list_view);
        mAdapter = new DataArrayAdapter(this, 0, mList);
        mDataListView.setAdapter(mAdapter);
        populateDataFromDB();
    }
    @Override
    public void onClick(View v) {
        Friends f = new Friends();

        switch (v.getId()) {
            case R.id.createdb:
            {
                LitePal.getDatabase();//MyApplication
            }break;
            case R.id.addData:
            { try {
                    i++;
                    f.setId(1995);
                    f.setName("old friends: "+i);
                    f.setNumber(i);
                    f.setSex(true);
                    f.save();
                    refreshListView(f.getId(), f.getName(),f.getNumber(), f.isSex());
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(this,"addData error",Toast.LENGTH_SHORT).show();
                }
            }break;
            case R.id.deleteData:
                try {//LitePal.deleteAll(Friends.class);
                     LitePal.delete(Friends.class,i);
                    i--;
                    refreshListView(f.getId(), f.getName(),f.getNumber(), f.isSex());
                } catch (Exception e) {
                    e.printStackTrace();
                }break;
            default :break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            {
                finish();
            }break;
            default :break;
        }
        return super.onOptionsItemSelected(item);
    }

    //	填充来自数据库中的数据
    private void populateDataFromDB() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mList.clear();
                List<String> columnList = new ArrayList<String>();
                columnList.add("id");
                columnList.add("name");
                columnList.add("number");
                columnList.add("sex");
                mList.add(columnList);
                Cursor cursor = null;
                try {
                    cursor = Connector.getDatabase().rawQuery("select * from friend order by id",
                            null);
                    if (cursor.moveToFirst()) {
                        do {
                            long id = cursor.getLong(cursor.getColumnIndex("id"));
                            String name = cursor.getString(cursor.getColumnIndex("name"));
                            int number = cursor.getInt(cursor.getColumnIndex("number"));
                            int sex = cursor.getInt(cursor.getColumnIndex("sex"));
                            List<String> stringList = new ArrayList<String>();
                            stringList.add(String.valueOf(id));
                            stringList.add(name);
                            stringList.add(String.valueOf(number));
                            stringList.add(String.valueOf(sex));
                            mList.add(stringList);
                        } while (cursor.moveToNext());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();//重绘当前可见区域
                        }
                    });
                }
            }
        }).start();
    }

    private void refreshListView(long id, String name, int number, boolean sex) {
        List<String> stringList = new ArrayList<String>();
        stringList.add(String.valueOf(id));
        stringList.add(name);
        stringList.add(String.valueOf(number));
        stringList.add(String.valueOf(sex));
        mList.add(stringList);
        mAdapter.notifyDataSetChanged();
        mDataListView.setSelection(mList.size());
    }
}
