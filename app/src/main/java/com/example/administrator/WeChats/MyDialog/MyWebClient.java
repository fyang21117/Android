package com.example.administrator.WeChats.MyDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

public class MyWebClient extends WebViewClient{
	String TAG = "MyWebClient";
	Context mContext;

	public MyWebClient(Context context){
		mContext = context;
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		super.shouldOverrideUrlLoading(view, url);
		Log.e(TAG,"shouldOverrideUrlLoading");

		if (url != null) {
			if (!(url.startsWith("http") || url.startsWith("https"))) {
				return true;
			}
			//重定向到别的页面
			//view.loadUrl("file:///android_asset/javascript.html");
			//区别不同链接加载
			view.loadUrl(url);
		}
		return true;
	}

	@Override
	public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
		Log.e(TAG,"onReceivedError");
		view.loadUrl("file:///android_asset/js_error.html");
	}

	@Override
	public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setMessage("notification_error_ssl_cert_invalid");
		builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				handler.proceed();
			}
		});
		builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				handler.cancel();
			}
		});
		final AlertDialog dialog = builder.create();
		dialog.show();
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		super.onPageStarted(view, url, favicon);
		Log.e(TAG, "onPageStarted:" + url);
	}

	@Override
	public void onLoadResource(WebView view, String url) {
		super.onLoadResource(view, url);
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
		Log.e(TAG, "onPageFinished: " + url);
	}

	@Nullable
	@Override
	public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//		Log.e(TAG, "shouldInterceptRequest " );
		return super.shouldInterceptRequest(view, request);
	}
}
