package com.example.administrator.WeChats;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class TwoFragment extends Fragment {

	ViewPager viewPager;
	TabLayout tabLayout;
	List<View> views;
	List<String> titles;
	WebView webView02;
	Button BtAndroidCallJs;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		Log.e("测试","onCreateView");

		View view = inflater.inflate(R.layout.fragment01, container, false);
//		AndroidCallJs = view.findViewById(R.id.AndroidCallJs);
		return view;
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Log.e("测试","onViewCreated");

		viewPager = view.findViewById(R.id.one_view_pager);
		tabLayout = view.findViewById(R.id.tab_layout);

		View viewOne = LayoutInflater.from(view.getContext()).inflate(R.layout.page01, null);
		View viewTwo = LayoutInflater.from(view.getContext()).inflate(R.layout.page02, null);


		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		webView02 = new WebView(getContext());
		webView02.setLayoutParams(params);
		WebSettings webSettings = webView02.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);




		webView02.loadUrl("file:///android_asset/JsOne.html");// 先载入JS代码,格式规定为:file:///android_asset/文件名.html
		webView02.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
				AlertDialog.Builder b = new AlertDialog.Builder(requireContext());
				b.setTitle("Alert");
				b.setMessage(message);
				b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						result.confirm();
					}
				});
				b.setCancelable(false);
				b.create().show();
				return true;
			}
		});

		//viewone的点击事件
		BtAndroidCallJs = viewOne.findViewById(R.id.AndroidCallJs);
		BtAndroidCallJs.setOnClickListener(new View.OnClickListener() {
			@JavascriptInterface
			@Override
			public void onClick(View v) {
				webView02.post(new Runnable() {
					@Override
					public void run() {
						webView02.loadUrl("javascript:callJS()");
					}
				});
			}
		});

		views = new ArrayList<>();
		views.add(viewOne);
		//viewTwo改为webview加载本地页面html
		views.add(webView02);
//		views.add(viewTwo);

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

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e("测试","onDestroy");

		ViewParent parent = webView02.getParent();
		if (parent != null) {
			((ViewGroup) parent).removeView(webView02);
		}
		webView02.stopLoading();
		webView02.getSettings().setJavaScriptEnabled(false);
		webView02.clearHistory();
		webView02.clearView();
		webView02.removeAllViews();
		webView02.destroy();
		webView02 =null;
	}
}
