package com.example.administrator.WeChats.Fragment03;


import android.annotation.SuppressLint;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.administrator.WeChats.R;
import com.example.administrator.WeChats.ViewPageAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xww
 */
public class ThreeFragment extends Fragment {

	ViewPager viewPager;
	TabLayout tabLayout;
	List<View> views;
	List<String> titles;
	WebView webView03;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment01, container, false);
		return view;
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		viewPager = view.findViewById(R.id.one_view_pager);
		tabLayout = view.findViewById(R.id.tab_layout);

		View viewOne = LayoutInflater.from(view.getContext()).inflate(R.layout.page01, null);
		View viewTwo = LayoutInflater.from(view.getContext()).inflate(R.layout.page02, null);
		View viewThree = LayoutInflater.from(view.getContext()).inflate(R.layout.page03, null);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		webView03 = new WebView(getContext());
		webView03.setLayoutParams(params);
		WebSettings webSettings = webView03.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webView03.addJavascriptInterface(new Android2Js(),"func");//AndroidtoJS类对象映射到js的func 对象
		webView03.loadUrl("file:///android_asset/JsTwo.html");

		views = new ArrayList<>();
//		views.add(viewOne);
		views.add(webView03);
		views.add(viewTwo);
		views.add(viewThree);
		titles = new ArrayList<>();
		titles.add("储值套餐1");
		titles.add("储值套餐2");
		titles.add("储值套餐3");

		ViewPageAdapter adapter = new ViewPageAdapter(views, titles);

		for (String title : titles) {
			tabLayout.addTab(tabLayout.newTab().setText(title));
		}

		tabLayout.setupWithViewPager(viewPager);
		viewPager.setAdapter(adapter);
	}
}
