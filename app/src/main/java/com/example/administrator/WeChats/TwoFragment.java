package com.example.administrator.WeChats;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TwoFragment extends Fragment {

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

		views = new ArrayList<>();
		views.add(viewOne);
		views.add(viewTwo);
		titles = new ArrayList<>();
		titles.add("充10送1");
		titles.add("充100送20");

		ViewPageAdapter adapter = new ViewPageAdapter(views, titles);

		for (String title : titles) {
			tabLayout.addTab(tabLayout.newTab().setText(title));
		}

		tabLayout.setupWithViewPager(viewPager);
		viewPager.setAdapter(adapter);
	}
}
