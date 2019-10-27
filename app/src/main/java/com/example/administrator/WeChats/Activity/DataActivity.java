package com.example.administrator.WeChats.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.sql.RowId;
import java.util.ArrayList;
import java.util.List;

import static org.litepal.LitePal.find;

public class DataActivity extends AppCompatActivity implements View.OnClickListener
{
    public static void actionStart(Context context) {
        Intent intent=new Intent(context,DataActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
    }
    //------------------------------------------------------
    public static long i=0,returnId;
    private ListView mDataListView;
    private DataArrayAdapter mAdapter;
    private List<List<String>> mList = new ArrayList<>();
    public List<String> returnList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar !=null)
            actionBar.setDisplayHomeAsUpEnabled(true);

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
        Friends friends = new Friends();

        switch (v.getId()) {
            case R.id.createdb:
                LitePal.getDatabase();
                break;
            case R.id.addData:{
                try {
                    i++;
                    friends.setId((int)i);
                    friends.setName("f"+i);
                    friends.setNumber((int)i);
                    friends.setSex(((int)i)%3 ==0);
                    friends.save();
                    returnList=refreshListView(friends.getId(), friends.getName(),friends.getNumber(), friends.isSex());
                    Toast.makeText(this,Integer.toString(friends.getId()),Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(this,"addData error",Toast.LENGTH_SHORT).show();
                }
                }break;
            case R.id.deleteData: {
                try {
                   // LitePal.deleteAll(Friends.class, "sex= ?" , "false");
                    returnId=friends.getId();
                    LitePal.delete(Friends.class, i--);
                    //deleteListView(returnList,i--, friends.getName(),friends.getNumber(), friends.isSex());
                    Toast.makeText(this,Integer.toString(friends.getId()),Toast.LENGTH_SHORT).show();
                    populateDataFromDB();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "delteData error", Toast.LENGTH_SHORT).show();
                }
            }break;
            default :break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
            }break;
            default :break;
        }
        return super.onOptionsItemSelected(item);
    }

    //	填充来自数据库中的数据
    private void populateDataFromDB() {//每次都刷掉了旧数据！！！！
        new Thread(new Runnable() {
            @Override
            public void run() {
                mList.clear();
                List<String> columnList = new ArrayList<>();
                columnList.add("id");
                columnList.add("name");
                columnList.add("number");
                columnList.add("sex");
                mList.add(columnList);
                Cursor cursor = null;
                try {
                    cursor = Connector.getDatabase().rawQuery("select * from friend order by id",null);
                    if (cursor.moveToFirst()) {
                        do {
                           long id = cursor.getLong(cursor.getColumnIndex("id"));
                           String name = cursor.getString(cursor.getColumnIndex("name"));
                           int number = cursor.getInt(cursor.getColumnIndex("number"));
                           int sex = cursor.getInt(cursor.getColumnIndex("sex"));

                            List<String> stringList = new ArrayList<>();
                           stringList.add(String.valueOf(id));
                            stringList.add(name);
                            stringList.add(String.valueOf(number));
                            stringList.add(String.valueOf(sex));
                            mList.add(stringList);
/*                            stringList.remove(String.valueOf(id));
                            stringList.remove(name);
                            stringList.remove(String.valueOf(number));
                            stringList.remove(String.valueOf(sex));
                            mList.remove(stringList);*/
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

    private List<String> refreshListView(long id, String name, int number, boolean sex) {
         List<String> stringList = new ArrayList<>();
        stringList.add(String.valueOf(id));
        stringList.add(name);
        stringList.add(String.valueOf(number));
        stringList.add(String.valueOf(sex));
        mList.add(stringList);
        //mreturnList.add(stringList);
        mAdapter.notifyDataSetChanged();
        mDataListView.setSelection(mList.size());
        return stringList;
    }
    private void deleteListView( List<String> returnList,long id, String name, int number, boolean sex) {
        returnList.remove(String.valueOf(id));
        returnList.remove(name);
        returnList.remove(String.valueOf(number));
        returnList.remove(String.valueOf(sex));
        mList.remove(returnList);
        mAdapter.notifyDataSetChanged();
        mDataListView.setSelection(mList.size()-1);
    }
}
