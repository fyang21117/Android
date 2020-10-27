package com.example.administrator.WeChats.MyDialog;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.widget.Toast;
import androidx.core.content.ContextCompat;


public class WebviewInterface {

	private JsInterface mProxy;
	public static final String tools = "tools";

	Context mContext;
	public WebviewInterface(Context context){
		mContext = context;
	}
	public void setProxy(JsInterface baseJsInterface) {
		mProxy = baseJsInterface;
	}

	@JavascriptInterface
	public void closeView(){
		mProxy.close();
	}

	@JavascriptInterface
	public void invalidateAndroidBack(String value){
		mProxy.invalidateBack(value);
	}

	@JavascriptInterface
	public void showToast(String msg){
		Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
	}

	@JavascriptInterface
	public void callPhone(String num){
		if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {//已有权限
			Intent callIntent = new Intent();
			callIntent.setAction(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:" + num));
			mContext.startActivity(callIntent);
		} else {
			Toast.makeText(mContext,"请先允许拨打电话的权限！",Toast.LENGTH_SHORT).show();
//			ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.CALL_PHONE},10111);//需要activity context
		}
	}

	@JavascriptInterface
	public void callAndroidEmail(String addressee,String theme,String content){
		String uriText = "mailto:" + Uri.encode(addressee) +
				"?subject=" + Uri.encode(theme) +
				"&body=" + Uri.encode(content);
		Uri uri = Uri.parse(uriText);
		Intent callEmailIntent = new Intent(Intent.ACTION_SENDTO);
		callEmailIntent.setData(uri);

//		callEmailIntent.setData(Uri.parse("mailto:"+addressee));
//		callEmailIntent.putExtra(Intent.EXTRA_SUBJECT, "url原因不显示主题"+theme);
//		callEmailIntent.putExtra(Intent.EXTRA_TEXT, "url原因不显示内容"+content);
		mContext.startActivity(callEmailIntent);
	}

	@JavascriptInterface
	public void openAndroidApp(String pkgName){
		Intent openAppIntent = mContext.getPackageManager().getLaunchIntentForPackage(pkgName);
		mContext.startActivity(openAppIntent);
	}

	@JavascriptInterface
	public void copyContent(String text){
		ClipboardManager clip = (ClipboardManager)mContext.getSystemService("clipboard");
		clip.setText(text);
	}

	@JavascriptInterface
	public void refreshView(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				mProxy.refresh();
			}
		}).start();
	}

	public interface JsInterface {
		/**
		 * 关闭弹窗
		 */
		void close();

		/**
		 * 弹窗是否屏蔽返回键，默认否
		 * @param enable
		 */
		void invalidateBack(String enable);

		/**
		 * 刷新页面
		 */
		void refresh();
	}

}
