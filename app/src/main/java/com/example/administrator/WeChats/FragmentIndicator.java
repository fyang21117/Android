package com.example.administrator.WeChats;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;


public class FragmentIndicator extends Fragment implements ViewIndicator.OnIndicateListener
{
    public static Fragment[] mFragments;

    public void onCreate(){}                        //考虑用

    public FragmentIndicator(){                     //创建对象，至少调用一个构造方法，与类同名
        //     super.getContext();
    }

    public  void setFragmentIndicator(  Activity activity, int whichIsDefault)
    {
        mFragments = new Fragment[4];
        mFragments[0] = getFragmentManager().findFragmentById(R.id.fragment_wechat);
        mFragments[1] = getFragmentManager().findFragmentById(R.id.fragment_contacts);
        mFragments[2] = getFragmentManager().findFragmentById(R.id.fragment_discover);
        mFragments[3] = getFragmentManager().findFragmentById(R.id.fragment_me);

        getFragmentManager().beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3])
                .show(mFragments[whichIsDefault]).commit();                 //显示默认的Fragment

        ViewIndicator mIndicator = activity.findViewById(R.id.indicator);   //隶属那个活动用findviewbyid();
        ViewIndicator.setIndicator(whichIsDefault);
        mIndicator.setOnIndicateListener(this);
    }

    @Override
    public void onIndicate(View v, int which)
    {
        getFragmentManager().beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3])
                        .show(mFragments[which]).commit();
                switch (which) {
                    case 0:
             //           ActivityWeChat.actionStart(this);
                        Toast.makeText(getContext(),"wechat",Toast.LENGTH_SHORT).show();
                        Intent i0 = new Intent(getContext(), ActivityWeChat.class);
                        i0.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i0);
                        break;
                    case 1:
                        Toast.makeText(getContext(),"contacts",Toast.LENGTH_SHORT).show();
                        Intent i1 = new Intent(getContext(), ActivityContacts.class);
                        i1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i1);
                        break;
                    case 2:
                        Toast.makeText(getContext(), "discover", Toast.LENGTH_SHORT).show();
                        Intent i2 = new Intent(getContext(), ActivityDiscover.class);
                        i2.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i2);
                        break;
                    case 3:
                        Toast.makeText(getContext(), "me", Toast.LENGTH_SHORT).show();
                        Intent i3 = new Intent(getContext(), ActivityMe.class);
                        i3.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i3);
                        break;
                }
    }
}
