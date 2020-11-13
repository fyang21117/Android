package com.example.administrator.WeChats.MyDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.appcompat.app.AlertDialog;

public class MyChromeClient extends WebChromeClient {
	String TAG = "MyChromeClient";
	Context chromeContext;
	public MyChromeClient(Context context) {
		super();
		chromeContext = context;
	}

	@Override
	public void onProgressChanged(WebView view, int newProgress) {
		super.onProgressChanged(view, newProgress);
//		if (newProgress < 100) {
//			String progress = newProgress + "%";
//			Log.e("测试", "加载进度:"+progress);
//			webProgress.setProgress(newProgress);
//		}
	}

	@Override
	public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
		Log.e("测试", "onJsAlert："+url+",message:"+message+",jsResult:"+result.toString());
		//重定向到别的页面
//		view.loadUrl("file:///android_asset/javascript.html");
//		result.confirm();
		return false;
//		return super.onJsAlert(view, url, message, result);


	}

	@Override
	public boolean onJsConfirm(final WebView view, String url, String message, final JsResult result) {
		Log.e("测试", "onJsConfirm："+url+",message:"+message+",jsResult:"+result.toString());
//		return super.onJsConfirm(view, url, message, result);
//		return false;
		new AlertDialog.Builder(chromeContext)
				.setTitle("拦截JsConfirm显示!")
				.setMessage(message)
				.setPositiveButton(android.R.string.ok,
						new AlertDialog.OnClickListener() {
							public void onClick(DialogInterface dialog,int which) {
								// do your stuff
								view.loadUrl("file:///android_asset/javascript.html");
								result.confirm();
							}
						}).setCancelable(false).create().show();
		return true;
//return false;
	}

	@Override
	public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
//		return super.onJsPrompt(view, url, message, defaultValue, result);
		Log.e("测试", "onJsPrompt："+url+",message:"+message+",jsResult:"+result.toString()+",defaultValue:"+defaultValue);

		return false;

	}

	@Override
	public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
		return super.onJsBeforeUnload(view, url, message, result);
	}

	@Override
	public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
		Log.e("测试", "consoleMessage："+consoleMessage.message());

		return true;
	}
}
