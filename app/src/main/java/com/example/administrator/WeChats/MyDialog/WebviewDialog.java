package com.example.administrator.WeChats.MyDialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.example.administrator.WeChats.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.KeyEvent.KEYCODE_BACK;
import static com.example.administrator.WeChats.MyDialog.WebViewActivity.dialog;
import static com.example.administrator.WeChats.MyDialog.WebviewInterface.tools;

public class WebviewDialog extends Dialog{

	WebView mWebView;
	ImageView imageView;
	Context webviewContext;

	public WebviewDialog(@NonNull Context context) {
		super(context);
		webviewContext = context;
	}
	float screenPercent = 0.9f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mWebView = new WebView(getContext());
		showWebView(mWebView,"file:///android_asset/javascript.html");

		RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(100,100);
		imageView = new ImageView(getContext());
		imageView.setImageResource(R.drawable.btclose);
		imageParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		imageParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		imageView.setLayoutParams(imageParams);

        final ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.activity_webview, null);
		viewGroup.addView(mWebView,0);
		viewGroup.addView(imageView,1);

        setContentView(viewGroup);
		setWebviewMargin();

		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//关闭dialog。待修改：全局static变量形式---独立开来
				dialog.dismiss();
			}
		});
	}

	@Override
	public void show() {
		super.show();
	}

	@Override
	public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
		if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@SuppressLint("SetJavaScriptEnabled")
	public void showWebView(WebView webView, String url) {
		webView.loadUrl(url);
		webView.addJavascriptInterface(new WebviewInterface(getContext()),tools);
		webView.canGoBack();
		webView.canGoForward();

		webView.setWebViewClient(new MyWebClient(getContext()));

		webView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
//				if (newProgress < 100) {
//					String progress = newProgress + "%";
//					progress.setText(progress);
//				}
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
			}
		});

		WebSettings webSettings = webView.getSettings();
		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setDisplayZoomControls(true);
		webSettings.setAllowFileAccess(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setLoadsImagesAutomatically(true);
		webSettings.setDefaultTextEncodingName("utf-8");
	}

	private void setWebviewMargin() {
		DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;

		Window dialogWindow = getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.CENTER);
		lp.width = (int )(screenWidth * screenPercent);
		lp.height = (int )(screenHeight * screenPercent);

//		int margin_left_right_dp = 20;
//		lp.width = screenWidth - 2 * dip2px(margin_left_right_dp);
//		int margin_top_bottom_dp = 20;
//		lp.height = screenHeight - 2 * dip2px(margin_top_bottom_dp);
	}

	public static int dip2px(float dipValue) {
		final float scale = Resources.getSystem().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
}
