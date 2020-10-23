package com.example.administrator.WeChats;


import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xww
 */
public class FiveFragment extends Fragment implements View.OnClickListener {

	ViewPager viewPager;
	TabLayout tabLayout;
	List<View> views;
	List<String> titles;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment01, container, false);
		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		viewPager = view.findViewById(R.id.one_view_pager);
		tabLayout = view.findViewById(R.id.tab_layout);

		View viewOne = LayoutInflater.from(view.getContext()).inflate(R.layout.page01, null);
		View viewTwo = LayoutInflater.from(view.getContext()).inflate(R.layout.page02, null);
		View viewThree = LayoutInflater.from(view.getContext()).inflate(R.layout.page03, null);
		View viewFour = LayoutInflater.from(view.getContext()).inflate(R.layout.page04, null);
		View viewFive = LayoutInflater.from(view.getContext()).inflate(R.layout.page05, null);

		views = new ArrayList<>();
		views.add(viewOne);
		views.add(viewTwo);
		views.add(viewThree);
		views.add(viewFour);
		views.add(viewFive);
		titles = new ArrayList<>();
		titles.add("1号客服");
		titles.add("2号客服");
		titles.add("3号客服");
		titles.add("4号客服");
		titles.add("5号客服");

		viewOne.setOnClickListener(this);
		viewTwo.setOnClickListener(this);
		viewThree.setOnClickListener(this);
		viewFour.setOnClickListener(this);
		viewFive.setOnClickListener(this);

		ViewPageAdapter adapter = new ViewPageAdapter(views, titles);

		for (String title : titles) {
			tabLayout.addTab(tabLayout.newTab().setText(title));
		}

		tabLayout.setupWithViewPager(viewPager);
		viewPager.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.page01:{
				Toast.makeText(getContext(),"1号客服",Toast.LENGTH_SHORT).show();
				Log.e("测试","1号客服");
			}break;

			case R.id.page02:{
				Toast.makeText(getContext(),"2号客服",Toast.LENGTH_SHORT).show();
			}break;

			case R.layout.page03:{
				Toast.makeText(getContext(),"3号客服",Toast.LENGTH_SHORT).show();
			}break;

			case R.layout.page04:{
				Toast.makeText(getContext(),"4号客服",Toast.LENGTH_SHORT).show();
			}break;

			case R.layout.page05:{
				Toast.makeText(getContext(),"5号客服",Toast.LENGTH_SHORT).show();
			}break;
			default:break;
		}
	}
}
