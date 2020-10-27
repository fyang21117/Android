package com.example.administrator.WeChats.WebViewDialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebView extends WebView{
	private static MyWebView mInstance;

	public MyWebView(Context context) {
		super(context);
	}

	public static void showWebView(Context context, String url){

		final WebView webView = new WebView(context);
//		webView.findViewById(R.id.webview);
//		Webview webview = (WebView) findViewById(R.id.webView1);
		webView.loadUrl("https://baidu.com");


		/**
		 * WebViewClient
		 * 作用：
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
				Log.e("WebviewTest", "onPageStarted: " );
			}

			@Override
			public void onLoadResource(WebView view, String url) {
				super.onLoadResource(view, url);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				Log.e("WebviewTest", "onPageFinished: " );
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
		 * 作用：
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
		});


		/**
		 * WebSettings
		 * 作用：
		 * */
		WebSettings webSettings = webView.getSettings();
		webSettings.setSupportZoom(false);
		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

//		webSettings.setJavaScriptEnabled(true);
//		webSettings.setPluginsEnabled(true);
//		webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
//		webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//		webSettings.setSupportZoom(true); //支持缩放
//		webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
//		webSettings.setDisplayZoomControls(true); //隐藏原生的缩放控件

		webSettings.setAllowFileAccess(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setLoadsImagesAutomatically(true);
		webSettings.setDefaultTextEncodingName("utf-8");
	}

	public void setCurWebUrl(String url) {
	}
}
