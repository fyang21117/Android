package com.example.administrator.WeChats;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class OneFragment extends Fragment {

	String TAG = "OneFragment";
	ViewPager viewPager;
	TabLayout tabLayout;
	List<View> views;
	List<String> titles;
	MyWebView myWebView;
	WebView webView01;

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
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		webView01 = new WebView(getContext());
		webView01.setLayoutParams(params);
//		view.addView(webView);
//		webView = (WebView) viewOne.findViewById(R.id.webview);
		showWebView(webView01,"https://baidu.com");//加载apk包中的html页面."file:android_asset/JsOne.html"

		views = new ArrayList<>();
		views.add(webView01);
//		views.add(viewOne);

		titles = new ArrayList<>();
		titles.add("引导玩家手机号绑定");

		ViewPageAdapter adapter = new ViewPageAdapter(views, titles);
		for (String title : titles) {
			tabLayout.addTab(tabLayout.newTab().setText(title));
		}

		tabLayout.setupWithViewPager(viewPager);
		viewPager.setAdapter(adapter);
	}

	@SuppressLint("SetJavaScriptEnabled")
	public void showWebView(WebView webView, String url) {
		webView.loadUrl(url);

		/**
		 * WebViewClient
		 * 作用：处理各种通知 & 请求事件
		 * */
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				Log.e(TAG, "onPageStarted: " );
			}

			@Override
			public void onLoadResource(WebView view, String url) {
				super.onLoadResource(view, url);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				Log.e(TAG, "onPageFinished: " );
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
//				switch(errorCode) {
//					case HttpStatus.SC_NOT_FOUND:
//						view.loadUrl("file:///android_assets/error_handle.html");
//						break;
//				}
			}
		});


		/**
		 * WebChromeClient
		 * 作用：：辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等。
		 * */
		webView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress < 100) {
					String progress = newProgress + "%";
//					progress.setText(progress);
				} else {
				}
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
//				titleview.setText(title);
			}

			@Override
			public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
				new AlertDialog.Builder(getContext())
						.setTitle("JsAlert")
						.setMessage(message)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								result.confirm();
							}
						})
						.setCancelable(false)
						.show();
				return true;
			}

			@Override
			public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
				new AlertDialog.Builder(getContext())
						.setTitle("JsConfirm")
						.setMessage(message)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								result.confirm();
							}
						})
						.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								result.cancel();
							}
						})
						.setCancelable(false)
						.show();
// 返回布尔值：判断点击时确认还是取消
// true表示点击了确认；false表示点击了取消；
				return true;
			}

			@Override
			public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
				final EditText et = new EditText(getContext());
				et.setText(defaultValue);
				new AlertDialog.Builder(getContext())
						.setTitle(message)
						.setView(et)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								result.confirm(et.getText().toString());
							}
						})
						.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								result.cancel();
							}
						})
						.setCancelable(false)
						.show();

				return true;
			}

		});

		/**
		 * WebSettings
		 * 作用：对WebView进行配置和管理
		 * */
		WebSettings webSettings = webView.getSettings();
		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

		webSettings.setJavaScriptEnabled(true);
//		webSettings.setPluginsEnabled(true);
//		webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
//		webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
		webSettings.setSupportZoom(true); //支持缩放
		webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
		webSettings.setDisplayZoomControls(true); //隐藏原生的缩放控件

		webSettings.setAllowFileAccess(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setLoadsImagesAutomatically(true);
		webSettings.setDefaultTextEncodingName("utf-8");

		webView.canGoBack();
		webView.canGoForward();
	}

//	public boolean onKeyDown(int keyCode, KeyEvent event){
//		if(keyCode == KEYCODE_BACK  && webView.canGoBack()){
//			webView.goBack();;
//			return true;
//		}
//		return super.onKeyDown(keyCode,event);
//	}


	@Override
	public void onDestroy() {
		if (webView01 != null) {
			webView01.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
			webView01.clearHistory();

			((ViewGroup) webView01.getParent()).removeView(webView01);
			webView01.destroy();
			webView01 = null;
		}
		super.onDestroy();
	}
}
