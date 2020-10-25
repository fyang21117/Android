package com.example.administrator.WeChats.Fragment03;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.ViewPager;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.administrator.WeChats.R;
import com.example.administrator.WeChats.ViewPageAdapter;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
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
	WebView webView031;
	WebSettings webSettings031;
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

		View viewOne = LayoutInflater.from(view.getContext()).inflate(R.layout.page01, null);
		View viewTwo = LayoutInflater.from(view.getContext()).inflate(R.layout.page02, null);
		View viewThree = LayoutInflater.from(view.getContext()).inflate(R.layout.page03, null);


		//viewOne：android调用js方法
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		webView031 = new WebView(getContext());
		webView031.setLayoutParams(params);
		webSettings031 = webView031.getSettings();
		webSettings031.setJavaScriptEnabled(true);
		webView031.addJavascriptInterface(new Android2Js(),"func");//AndroidtoJS类对象映射到js的func 对象
		webView031.loadUrl("file:///android_asset/JsTwo.html");

		//viewTwo：图片加载
		webView032 = new WebView(getContext());
		webView032.setLayoutParams(params);
		webSettings032 = webView032.getSettings();
		webSettings032.setAllowUniversalAccessFromFileURLs(true);
		webSettings032.setAllowFileAccess(true);
		webSettings032.setAllowFileAccessFromFileURLs(true);
		webSettings032.setSupportZoom(true); //支持缩放
		webSettings032.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
		webSettings032.setDisplayZoomControls(true); //隐藏原生的缩放控件
		webSettings032.setJavaScriptEnabled(true);
		webSettings032.setDefaultTextEncodingName("utf-8");
		webView032.setWebChromeClient(new chromClient());
		webView032.loadUrl("file:///android_asset/JsThree.html");

		//viewThree视频加载



		views = new ArrayList<>();
//		views.add(viewOne);
		views.add(webView031);
//		views.add(viewTwo);
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

	private class chromClient extends WebChromeClient {
			// For Android < 3.0
			public void openFileChooser(ValueCallback<Uri> uploadMsg) {
				WebCameraHelper.getInstance().mUploadMessage = uploadMsg;
				WebCameraHelper.getInstance().showOptions(getActivity());
			}

			// For Android > 4.1.1
			public void openFileChooser(ValueCallback<Uri> uploadMsg,String acceptType, String capture) {
				WebCameraHelper.getInstance().mUploadMessage = uploadMsg;
				WebCameraHelper.getInstance().showOptions(getActivity());
			}

			// For Android > 5.0支持多张上传
			@Override
			public boolean onShowFileChooser(WebView webView,ValueCallback<Uri[]> uploadMsg,FileChooserParams fileChooserParams) {
				WebCameraHelper.getInstance().mUploadCallbackAboveL = uploadMsg;
				WebCameraHelper.getInstance().showOptions(getActivity());
				return true;
			}
	}
	private void runWebView(final String url){
		webView032.post(new Runnable() {
			@Override
			public void run() {
				webView032.loadUrl(url);
			}
		});
	}
}
