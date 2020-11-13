package com.example.administrator.WeChats.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.example.administrator.WeChats.FiveFragment;
import com.example.administrator.WeChats.FourFragment;
import com.example.administrator.WeChats.FragmentAdapter;
import com.example.administrator.WeChats.TwoFragment;
import com.example.administrator.WeChats.R;
import com.example.administrator.WeChats.Fragment03.ThreeFragment;
import com.example.administrator.WeChats.Fragment01;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.List;
import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class ActivityMain extends AppCompatActivity {

    NavigationView navigationView;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(FLAG_FULLSCREEN , FLAG_FULLSCREEN);

        navigationView = findViewById(R.id.nav_view);
        viewPager = findViewById(R.id.view_pager);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new Fragment01());
        fragments.add(new TwoFragment());
        fragments.add(new ThreeFragment());
        fragments.add(new FourFragment());
        fragments.add(new FiveFragment());

        FragmentAdapter adapter = new FragmentAdapter(fragments, getSupportFragmentManager());

        viewPager.setAdapter(adapter);
//        viewPager.setBackground(getDrawable(R.drawable.background37));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int menuId = menuItem.getItemId();
                // 跳转指定页面：Fragment
                switch (menuId) {
                    case R.id.tab_one:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.tab_two:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.tab_three:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.tab_four:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.tab_five:
                        viewPager.setCurrentItem(4);
                        break;
                }
                return false;
            }
        });

        // ViewPager 滑动事件监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                //将滑动到的页面对应的 menu 设置为选中状态
                navigationView.getMenu().getItem(i).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }
}