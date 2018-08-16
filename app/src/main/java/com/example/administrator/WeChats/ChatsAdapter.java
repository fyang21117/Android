package com.example.administrator.WeChats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class ChatsAdapter extends ArrayAdapter<Chats>
{
    private int resourceId;
    public ChatsAdapter(Context context, int textViewResourceId,List<Chats> objects) //构造函数
    {
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent)             //重写方法
    {
        Chats chats = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView chatsImage = view.findViewById(R.id.chats_image);
        TextView chatsName = view.findViewById(R.id.chats_name);
        chatsImage.setImageResource(chats.getImageId());
        chatsName.setText(chats.getName());
        return view;
    }
}
