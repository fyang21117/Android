package com.example.administrator.WeChats.Fragment03;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class Android2Js extends Object {
	@JavascriptInterface
	public Android2Js fun(String msg){
		Log.e("测试","Js调用了Android的fun方法");
		return this;
	}
}
