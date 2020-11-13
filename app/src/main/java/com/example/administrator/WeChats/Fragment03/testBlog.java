package com.example.administrator.WeChats.Fragment03;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
public class testBlog extends Fragment {

	ViewPager viewPager;
	TabLayout tabLayout;
	List<View> views;
	List<String> titles;
	WebView webView;
	WebSettings webSettings;
	WebView webView032;
	WebSettings webSettings032;
	WebView webView033;
	WebSettings webSettings033;
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

		View viewThree = LayoutInflater.from(view.getContext()).inflate(R.layout.page03, null);
		String urls = "https://zzcool.com/hd/20200612_wonderful/?screen_type=1&popup_template=https://imgcs.s98s2.com/common/1590137250phpnuTcyi.png&close_button=https://imgcs.s98s2.com/common/1590137255phpGxIoK2.png&popup_background=https://imgcs.s98s2.com/common/1590137250phpnuTcyi.png";

		//viewOne：android调用js方法
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		this.webView = new WebView(getContext());
		this.webView.setLayoutParams(params);
		webSettings = this.webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		this.webView.addJavascriptInterface(new Android2Js(),"func");
		this.webView.loadUrl(urls);

		//viewTwo：图片加载
		webView032 = new WebView(getContext());
		webView032.setLayoutParams(params);
		webSettings032 = webView032.getSettings();
		webSettings032.setAllowUniversalAccessFromFileURLs(true);
		webSettings032.setAllowFileAccess(true);
		webSettings032.setAllowFileAccessFromFileURLs(true);
		webSettings032.setSupportZoom(true);
		webSettings032.setBuiltInZoomControls(true);
		webSettings032.setDisplayZoomControls(true);
		webSettings032.setJavaScriptEnabled(true);
		webSettings032.setDefaultTextEncodingName("utf-8");
		webView032.setWebChromeClient(new WebChromeClient());
		webView032.loadUrl("file:///android_asset/JsThree.html");

		views = new ArrayList<>();
		views.add(this.webView);
		views.add(webView032);
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

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		WebCameraHelper.getInstance().onActivityResult(requestCode,resultCode,data);
	}
}

