package com.example.administrator.WeChats.WebViewDialog;

import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class BaseWebview extends WebView {

//	private static WebView webView;
	public BaseWebview baseWebview;
	public Context context;
	private synchronized BaseWebview getInstance(){
		return baseWebview == null? new BaseWebview(context):baseWebview;
	}

	public BaseWebview(Context context) {
		super(context);
		initBaseView();
	}

	private void initBaseView() {
		WebSettings webSettings = baseWebview.getSettings();

	}

}
