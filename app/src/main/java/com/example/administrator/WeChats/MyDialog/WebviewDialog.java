package com.example.administrator.WeChats.MyDialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.administrator.WeChats.FragmentAdapter;
import com.example.administrator.WeChats.R;
import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.administrator.WeChats.MyDialog.WebViewActivity.dialog;
import static com.example.administrator.WeChats.MyDialog.WebviewInterface.tools;

public class WebviewDialog extends Dialog {

	WebView mWebView;
	ImageView imageView;
	Context webviewContext;
	FragmentManager webViewFM;
	NavigationView navWebview;
	ViewPager webviewPager;
	ProgressBar webProgress;
	boolean webLoaded = false;
	List<Fragment> webViewFragment;

	public WebviewDialog(Context context,FragmentManager fm,List<Fragment> fragments) {
		super(context);
		webviewContext = context;
		webViewFM = fm;
		webViewFragment = fragments;
	}
	float screenPercent = 0.9f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//单个webview加载
		LinearLayout.LayoutParams webviewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		mWebView = new WebView(getContext());
		mWebView.setLayoutParams(webviewParams);
//		String urls = "https://zzcool.com/hd/20200612_wonderful/?screen_type=1&popup_template=https://imgcs.s98s2.com/common/1590137250phpnuTcyi.png&close_button=https://imgcs.s98s2.com/common/1590137255phpGxIoK2.png&popup_background=https://imgcs.s98s2.com/common/1590137250phpnuTcyi.png";
//		showWebView(mWebView,urls);
//		showWebView(mWebView,"https://zzcool.com/hd/20200612_wonderful/?screen_type=1&popup_template=https://imgcs.s98s2.com/common/1590137250phpnuTcyi.png&close_button=https://imgcs.s98s2.com/common/1590137255phpGxIoK2.png&popup_background=https://imgcs.s98s2.com/common/1590137250phpnuTcyi.png&popup_background=https://imgcs.s98s2.com/common/1590137255phpGxIoK2.png");
//		showWebView(mWebView,"https://www.baidu.com/");
//		showWebView(mWebView,"file:///android_asset/javascript.html");

		//测试webchromeclient
//		showWebView(mWebView,"file:///android_asset/js_chromeclient.html");
//		showWebView(mWebView,"file:///android_asset/JsThree.html");


//		mWebView.evaluateJavascript("file:///android_asset/javascript.html",new ValueCallback<String>() {
//			@Override
//			public void onReceiveValue(String value) {
//				Log.e("测试", "onReceiveValue："+value );
//			}
//		});


		//关闭按钮
		RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(100,100);
		imageView = new ImageView(getContext());
		imageView.setImageResource(R.mipmap.ic_close);
		imageParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		imageParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		imageView.setLayoutParams(imageParams);

//		setContentView(R.layout.activity_webview);
		navWebview = findViewById(R.id.nav_webview);
		webviewPager = findViewById(R.id.webview_pager);

		ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.activity_webview, null);
		navWebview = viewGroup.getRootView().findViewById(R.id.nav_webview);
		webviewPager = viewGroup.getRootView().findViewById(R.id.webview_pager);
//		webProgress = viewGroup.getRootView().findViewById(R.id.webProgress);

		//需要在父布局先清除，才能添加到本布局中。
		if(webviewPager != null){
			ViewGroup pagerParent = (ViewGroup) webviewPager.getParent();
			if(pagerParent != null){
				pagerParent.removeView(webviewPager);
			}
		}
		if(navWebview != null){
			ViewGroup navWebviewParent = (ViewGroup) navWebview.getParent();
			if(navWebviewParent != null){
				navWebviewParent.removeView(navWebview);
			}
		}


		FragmentAdapter adapter = new FragmentAdapter(webViewFragment, webViewFM);

		webviewPager.setAdapter(adapter);
		adapter.notifyDataSetChanged();

//		viewGroup.addView(mWebView);
		viewGroup.addView(navWebview);
		viewGroup.addView(webviewPager);
		viewGroup.addView(imageView);

		openListeners();
		RelativeLayout.LayoutParams dialogParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

//		addContentView(viewGroup,dialogParams);
        setContentView(viewGroup);
		setWebviewMargin();
	}
	private void openListeners(){
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//关闭dialog。待修改:加接口，做成回调
				dialog.dismiss();
			}
		});

		navWebview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
				int menuId = menuItem.getItemId();
				switch (menuId) {
					case R.id.webTab0:
						webviewPager.setCurrentItem(0);
						break;
					case R.id.webTab1:
						webviewPager.setCurrentItem(1);
						break;
					case R.id.webTab2:
						webviewPager.setCurrentItem(2);
						break;
				}
				return false;
			}
		});

		webviewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int i, float v, int i1) {

			}

			@Override
			public void onPageSelected(int i) {
				navWebview.getMenu().getItem(i).setChecked(true);
			}

			@Override
			public void onPageScrollStateChanged(int i) {

			}
		});
	}

	@Override
	public void show() {
		super.show();
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}

	@Override
	public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
//		if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
//			mWebView.goBack();
//			return true;
//		}
		return super.onKeyDown(keyCode, event);
	}

	@SuppressLint("SetJavaScriptEnabled")
	public void showWebView(final WebView webView, String url) {
		webView.loadUrl(url);
		webView.addJavascriptInterface(new WebviewInterface(getContext()),tools);
		webView.canGoBack();
		webView.canGoForward();

		if(countDownTimer != null && !webLoaded){
			//添加线程处理
//			countDownTimer.start();
			Log.e("测试", "倒计时开始");
		}else {
			webView.stopLoading();
		}

		webView.setWebViewClient(new MyWebClient(getContext()));
//		webView.setWebViewClient(new WebViewClient(){
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				view.loadUrl(url);
//				return true;
//			}
//
//			@Override
//			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//				super.onReceivedSslError(view, handler, error);
//				handler.proceed();
//			}
//		});
//		webView.setWebChromeClient(new WebChromeClient(){
//			@Override
//			public void onProgressChanged(WebView view, int newProgress) {
////				if (newProgress < 100) {
////					String progress = newProgress + "%";
////					Log.e("测试", "加载进度:"+progress);
//////					webProgress.setProgress(newProgress);
////				}else {
////					webLoaded = true;
////					Log.e("测试", "加载完成，倒计时结束:"+newProgress);
////				}
//			}
//
//
//			@Override
//			public void onReceivedTitle(WebView view, String title) {
//			}
//		});

//		webView.setWebChromeClient(new WebChromeClient());
		webView.setWebChromeClient(new MyChromeClient(getContext()));
		if(webLoaded){
			countDownTimer.onFinish();
			countDownTimer.onFinish();
			countDownTimer = null;
		}
		WebSettings webSettings = webView.getSettings();
		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setDisplayZoomControls(true);

		webSettings.setAllowFileAccess(true);
		webSettings.setAllowFileAccessFromFileURLs(true);
		webSettings.setAllowUniversalAccessFromFileURLs(true);

		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setLoadsImagesAutomatically(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setDefaultTextEncodingName("utf-8");
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
			webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
		}
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

	private CountDownTimer countDownTimer = new CountDownTimer(7500, 1000) {//第一个参数表示总时间，第二个参数表示间隔时间。
		@Override
		public void onTick(long millisUntilFinished) {
			@SuppressLint("SimpleDateFormat")
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
			Log.e("测试", "当前时间" + df.format(new Date()) + ", timer " + (millisUntilFinished / 1000));
		}

		@Override
		public void onFinish() {
			showWebView(mWebView,"file:///android_asset/js_error.html");
			Log.e("测试", "倒计时结束,加载失败.");
		}
	};

	public  void  clear(ViewPager viewPager){
		if (viewPager!=null && viewPager.getAdapter() != null) {
			//获取FragmentManager实现类的class对象,这里指的就是FragmentManagerImpl
			Class<? extends FragmentManager> aClass = webViewFM.getClass();
			try {
				//1.获取其mAdded字段
				Field f = aClass.getDeclaredField("mAdded");
				f.setAccessible(true);
				//强转成ArrayList
				ArrayList<Fragment> list = (ArrayList) f.get(webViewFM);
				//清空缓存
				list.clear();

				//2.获取mActive字段
				f = aClass.getDeclaredField("mActive");
				f.setAccessible(true);
				//强转成SparseArray
				SparseArray<Fragment> array  = (SparseArray) f.get(webViewFM);
				//清空缓存
				array.clear();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
