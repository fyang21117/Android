package com.example.administrator.WeChats;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.WeChats.Activity.ActivityContacts;
import com.example.administrator.WeChats.Activity.ActivityDiscover;
import com.example.administrator.WeChats.Activity.ActivityMe;
import com.example.administrator.WeChats.Activity.ActivityWeChat;

public class FragmentIndicator
{
    private static Fragment[] mFragments;
    private static ViewIndicator mIndicator;
    private static FragmentManager fm;
    public FragmentIndicator(){                     //创建对象，至少调用一个构造方法，与类同名
           // super.getContext();
    }

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
                Context context=v.getContext();
                if(context instanceof Activity) {
                    fm.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]).show(mFragments[which]).commit();
                    switch (which) {
                        case 0:
                            ActivityWeChat.actionStart(context);
                            break;
                        case 1:
                            ActivityContacts.actionStart(context);
                            break;
                        case 2:
                            ActivityDiscover.actionStart(context);
                            break;
                        case 3:
                            ActivityMe.actionStart(context);
                            break;
                        default:
                            break;
                    }
                }
                else
                    Toast.makeText(context,"context is not Activity",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
