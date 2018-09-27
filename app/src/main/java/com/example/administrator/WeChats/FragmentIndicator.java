package com.example.administrator.WeChats;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import com.example.administrator.WeChats.Activity.ActivityContacts;
import com.example.administrator.WeChats.Activity.ActivityDiscover;
import com.example.administrator.WeChats.Activity.ActivityMe;
import com.example.administrator.WeChats.Activity.ActivityWeChat;

public class FragmentIndicator extends Fragment
{
    public static Fragment[] mFragments;
    public Context context=getContext();
    public static FragmentManager fm;
    public FragmentIndicator(){                     //创建对象，至少调用一个构造方法，与类同名
            super.getContext();
    }

    public static ViewIndicator mIndicator;

    public  static void setFragmentIndicator( int whichIsDefault) {

        mFragments = new Fragment[4];
        mFragments[0] = fm.findFragmentById(R.id.fragment_wechat);
        mFragments[1] = fm.findFragmentById(R.id.fragment_contacts);
        mFragments[2] = fm.findFragmentById(R.id.fragment_discover);
        mFragments[3] = fm.findFragmentById(R.id.fragment_me);
        fm.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]).show(mFragments[whichIsDefault]).commit();

        ViewIndicator.setIndicator(whichIsDefault);
        mIndicator.setOnIndicateListener(new ViewIndicator.OnIndicateListener() {
            @Override
            public void onIndicate(View v, int which) {
                fm.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]).show(mFragments[which]).commit();
                switch (which) {
                    case 0:
                        ActivityWeChat.actionStart(v.getContext());
                        break;
                    case 1:
                        ActivityContacts.actionStart(v.getContext());
                        break;
                    case 2:
                        ActivityDiscover.actionStart(v.getContext());
                        break;
                    case 3:
                        ActivityMe.actionStart(v.getContext());
                        break;
                }
            }
        });
    }
}
