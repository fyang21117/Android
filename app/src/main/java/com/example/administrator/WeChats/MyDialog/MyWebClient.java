package com.example.administrator.WeChats.MyDialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebClient extends WebViewClient{

	String TAG = "MyWebClient";
	Context mContext;

	public MyWebClient(Context context){
		mContext = context;
	}

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
		view.loadUrl("file:///android_asset/js_error.html");
	}

}
