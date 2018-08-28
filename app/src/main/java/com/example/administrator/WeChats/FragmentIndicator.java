package com.example.administrator.WeChats;
//import android.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

//import static com.example.administrator.WeChats.WeChatActivity.mFragments;

 public class FragmentIndicator extends Fragment implements ViewIndicator.OnIndicateListener
{
    public static Fragment[] mFragments;
    public  void setFragmentIndicator(Activity activity,int whichIsDefault)
    {
        mFragments = new Fragment[4];
        mFragments[0] = getFragmentManager().findFragmentById(R.id.fragment_wechat);
        mFragments[1] = getFragmentManager().findFragmentById(R.id.fragment_contacts);
        mFragments[2] = getFragmentManager().findFragmentById(R.id.fragment_discover);
        mFragments[3] = getFragmentManager().findFragmentById(R.id.fragment_me);

        getFragmentManager().beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3])
                .show(mFragments[whichIsDefault]).commit();                 //显示默认的Fragment

        ViewIndicator mIndicator = activity.findViewById(R.id.indicator);
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
                        Toast.makeText(getContext(),"wechat",Toast.LENGTH_SHORT).show();
                        Intent i0 = new Intent(getContext(), WeChatActivity.class);
                        i0.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i0);
                        break;
                    case 1:
                        Toast.makeText(getContext(),"contacts",Toast.LENGTH_SHORT).show();
                        Intent i1 = new Intent(getContext(), ContactsActivity.class);
                        i1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i1);
                        break;
                    case 2:
                        Toast.makeText(getContext(), "discover", Toast.LENGTH_SHORT).show();
                        Intent i2 = new Intent(getContext(), DiscoverActivity.class);
                        i2.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i2);
                        break;
                    case 3:
                        Toast.makeText(getContext(), "me", Toast.LENGTH_SHORT).show();
                        Intent i3 = new Intent(getContext(), MeActivity.class);
                        i3.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i3);
                        break;
                }
    }
}
