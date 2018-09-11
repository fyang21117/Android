package com.example.administrator.WeChats;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DiscoverActivity extends AppCompatActivity implements View.OnClickListener {

    public static Fragment[] mFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        ActionBar actionBar=getSupportActionBar();
 //       actionBar.setDisplayHomeAsUpEnabled(true);

        setFragmentIndicator(2);
//        FragmentIndicator discover_fragment=new FragmentIndicator();
//        discover_fragment.setFragmentIndicator(DiscoverActivity.this,2);

        Button circle = findViewById(R.id.circle);
        Button scan = findViewById(R.id.scan);
        Button search = findViewById(R.id.search);
        circle.setOnClickListener(this);
        scan.setOnClickListener(this);
        search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.circle:
            {
                Intent circle = new Intent(DiscoverActivity.this,CircleActivity.class);
                circle.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(circle);
            }break;
            case R.id.scan:
            {
                Toast.makeText(this,"scan",Toast.LENGTH_SHORT).show();
            }break;
            case R.id.search:
            {
                Toast.makeText(this,"search",Toast.LENGTH_SHORT).show();
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
    private void setFragmentIndicator(int whichIsDefault) {
        mFragments = new Fragment[4];
        mFragments[0] = getSupportFragmentManager().findFragmentById(R.id.fragment_wechat);
        mFragments[1] = getSupportFragmentManager().findFragmentById(R.id.fragment_contacts);
        mFragments[2] = getSupportFragmentManager().findFragmentById(R.id.fragment_discover);
        mFragments[3] = getSupportFragmentManager().findFragmentById(R.id.fragment_me);

//        getSupportFragmentManager().beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]).show(mFragments[whichIsDefault]).commit();

        ViewIndicator mIndicator =  findViewById(R.id.indicator);
        ViewIndicator.setIndicator(whichIsDefault);
        mIndicator.setOnIndicateListener(new ViewIndicator.OnIndicateListener() {
            @Override
            public void onIndicate(View v, int which) {
                getSupportFragmentManager().beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]).show(mFragments[which]).commit();
                switch (which) {
                    case 0:
                        Toast.makeText(DiscoverActivity.this, "wechat", Toast.LENGTH_SHORT).show();
                        Intent i0 = new Intent(DiscoverActivity.this, WeChatActivity.class);
                        i0.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i0);
                        break;
                    case 1:
                        Toast.makeText(DiscoverActivity.this, "contacts", Toast.LENGTH_SHORT).show();
                        Intent i1 = new Intent(DiscoverActivity.this, ContactsActivity.class);
                        i1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i1);
                        break;
                    case 2: break;
                    case 3:
                        Toast.makeText(DiscoverActivity.this, "me", Toast.LENGTH_SHORT).show();
                        Intent i3 = new Intent(DiscoverActivity.this, MeActivity.class);
                        i3.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i3);
                        break;
                }

            }
        });
    }
}
