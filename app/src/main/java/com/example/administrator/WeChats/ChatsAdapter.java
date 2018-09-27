package com.example.administrator.WeChats;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.WeChats.Activity.ActivityContacts;
import com.example.administrator.WeChats.Activity.ActivityDiscover;
import com.example.administrator.WeChats.Activity.ActivityMe;
import com.example.administrator.WeChats.Activity.ActivityWeChat;
import com.example.administrator.WeChats.data.Chats;

import java.util.List;

public class ChatsAdapter extends ArrayAdapter<Chats>
{
    private int resourceId;
    public ChatsAdapter(Context context, int textViewResourceId,List<Chats> objects) //构造函数
    {
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

/*    @Override
    public View getView(int position,View convertView,ViewGroup parent)             //重写方法
    {
        Chats chats = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView chatsImage = view.findViewById(R.id.chats_image);
        TextView chatsName = view.findViewById(R.id.chats_name);
        chatsImage.setImageResource(chats.getImageId());
        chatsName.setText(chats.getName());
        return view;
    }*/

    public static class FragmentIndicator extends Fragment implements ViewIndicator.OnIndicateListener
    {
        public static Fragment[] mFragments;
        public static FragmentManager fm;
        public Activity activity;
        public  Context context;
        public FragmentIndicator(){//创建对象，至少调用一个构造方法，与类同名

        }

        public  void setFragmentIndicator(  int whichIsDefault)
        {
            mFragments = new Fragment[4];
            mFragments[0] =fm.findFragmentById(R.id.fragment_wechat);
            mFragments[1] =fm.findFragmentById(R.id.fragment_contacts);
            mFragments[2] = fm.findFragmentById(R.id.fragment_discover);
            mFragments[3] = fm.findFragmentById(R.id.fragment_me);//getFragmentManager()

            fm.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3])
                    .show(mFragments[whichIsDefault]).commit();                 //显示默认的Fragment

            ViewIndicator mIndicator = getActivity().findViewById(R.id.indicator);   //隶属那个活动用findviewbyid();
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
                            ActivityWeChat.actionStart(getContext());
                            break;
                        case 1:
                            ActivityContacts.actionStart(getContext());
                            break;
                        case 2:
                            ActivityDiscover.actionStart(getContext());
                            break;
                        case 3:
                            ActivityMe.actionStart(getContext());
                            break;
                            default:break;
                    }
        }
    }
}
