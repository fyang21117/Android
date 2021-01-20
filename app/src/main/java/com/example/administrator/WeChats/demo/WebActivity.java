package com.example.administrator.WeChats.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.utils.widget.MockView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import static com.example.administrator.WeChats.demo.WebviewInterface.tools;

public class WebActivity extends FragmentActivity {

    MockView mockView;
    AlertDialog.Builder builder;
    String TAG = "WebViewActivity";
    @SuppressLint("StaticFieldLeak")
    public static WebviewDialog dialog;
    NavigationView navigationView;
    ViewPager viewPager;
    private NetworkChangeReceiver networkChangeReceiver;
    WebView loadingFailedView;
    public static List<Fragment> fragment;
    public static FragmentManager fm;
    public static FragmentManager fm2;

    @NonNull
    @Override
    public FragmentManager getSupportFragmentManager() {
        return super.getSupportFragmentManager();
    }

    public FragmentManager MygetChildFragmentManager(Fragment fragment){
        return fragment.getFragmentManager();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "初始化失败: ", Toast.LENGTH_SHORT).show();

        //网络监听
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        /**
         * 第一种方式：非dialog实现js调用Android
         * */
//        setContentView(R.layout.activity_webview);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
//        final WebView mWebView = new WebView(getApplicationContext());
//        mWebView.setLayoutParams(params);
//        mWebView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Log.e("测试","shouldOverrideUrlLoading");
//                mWebView.loadUrl(url);
//                return super.shouldOverrideUrlLoading(view, url);
//            }
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                Log.e("测试","onPageStarted");
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                Log.e("测试","onPageFinished");
//            }
//
//            @Override
//            public void onPageCommitVisible(WebView view, String url) {
//                super.onPageCommitVisible(view, url);
//                Log.e("测试","onPageCommitVisible");
//            }
//
//            @Override
//            public void onLoadResource(WebView view, String url) {
//                super.onLoadResource(view, url);
//                Log.e("测试","onLoadResource");
//            }
//        });
//        mWebView.setWebChromeClient(new WebChromeClient(){
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//                Log.e("测试","onProgressChanged:"+newProgress);
//            }
//
//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//                super.onReceivedTitle(view, title);
//                Log.e("测试","onReceivedTitle:"+title);
//            }
//
//            @Override
//            public void onReceivedIcon(WebView view, Bitmap icon) {
//                super.onReceivedIcon(view, icon);
//                Log.e("测试","onReceivedIcon");
//            }
//
//            @Override
//            public void onCloseWindow(WebView window) {
//                super.onCloseWindow(window);
//                Log.e("测试","onCloseWindow");
//            }
//        });
//        setContentView(mWebView);
//        showWebView(mWebView,"https://www.baidu.com/");

//        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_webview, null);
//        viewGroup.addView(mWebView,0,params);

////        mWebView = findViewById(R.id.muiltiWebView);
//        setContentView(viewGroup);

        /**
         * 第二种方式：系统dialog实现js调用Android
         * */
//        setContentView(R.layout.activity_webview);
        //主活动创建方式
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);//
        WebView mWebView = new WebView(getApplicationContext());
        mWebView.setLayoutParams(params);
        showWebView(mWebView,"file:///android_asset/javascript.html");

        builder = new AlertDialog.Builder(this);
//        builder.setView(mWebView);
        builder.setTitle("应用需要适配系统风格");
        builder.setMessage("像弹出的Dialog、Loading框，需要适配系统风格；");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();

        //自定义dialog实现

        /**
         * 第三种方式：自定义dialog实现js调用Android
         * */
//        fm = getSupportFragmentManager() ;
//        fragment = new ArrayList<>();
//        fragment.add(new ThreeFragment());
//        fragment.add(new FourFragment());
//        fragment.add(new FiveFragment());
//        dialog = new WebviewDialog(this,fm,fragment);
//        dialog.show();

//        MyDialogFragment dialogFragment = new MyDialogFragment();
//        dialogFragment.show(getSupportFragmentManager(), "dialog");

//        对比显示底层view
//        setContentView(R.layout.activity_webview);
//        final NavigationView navWebview = findViewById(R.id.nav_webview);
//        final ViewPager webviewPager = findViewById(R.id.webview_pager);
//        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(new ThreeFragment());
//        fragments.add(new FourFragment());
//        fragments.add(new FiveFragment());
//        FragmentAdapter adapter = new FragmentAdapter(fragments,fm);
//        webviewPager.setAdapter(adapter);
//        navWebview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                int menuId = menuItem.getItemId();
//                switch (menuId) {
//                    case R.id.webTab0:
//                        webviewPager.setCurrentItem(0);
//                        break;
//                    case R.id.webTab1:
//                        webviewPager.setCurrentItem(1);
//                        break;
//                    case R.id.webTab2:
//                        webviewPager.setCurrentItem(2);
//                        break;
//                }
//                return false;
//            }
//        });
//        webviewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//                navWebview.getMenu().getItem(i).setChecked(true);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//            }
//        });


        /**
         * 第四种方式：添加Fragment+webview+dialog
         * */
//        setContentView(R.layout.activity_webview);
//        navigationView = findViewById(R.id.nav_webview);
//        viewPager = findViewById(R.id.webview_pager);
//
//        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(new FourFragment());
//        fragments.add(new FiveFragment());
//        FragmentAdapter adapter = new FragmentAdapter(fragments, getSupportFragmentManager());
//        viewPager.setAdapter(adapter);
//
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                int menuId = menuItem.getItemId();
//                switch (menuId) {
//                    case R.id.webTab0:
//                        viewPager.setCurrentItem(0);
//                        break;
//                    case R.id.webTab1:
//                        viewPager.setCurrentItem(1);
//                        break;
//                }
//                return false;
//            }
//        });
//
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//                //将滑动到的页面对应的 menu 设置为选中状态
//                navigationView.getMenu().getItem(i).setChecked(true);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//            }
//        });
    }

    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);  //得到系统服务类
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isAvailable()){
                Log.e("测试", "network is available");
            }else{
                Log.e("测试", "network is unavailable");
                loadingFailedView = new WebView(getApplicationContext());
                loadingFailedView.loadUrl("file:///android_asset/js_error.html");
            }
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }


    @SuppressLint("SetJavaScriptEnabled")
    public void showWebView(WebView webView, final String url) {
        webView.loadUrl(url);
        webView.addJavascriptInterface(new WebviewInterface(getApplicationContext()),tools);

//        webView.setWebViewClient(new WebViewClient());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.e("测试","shouldOverrideUrlLoading");
                return true;
//                if ("www.example.com".equals(Uri.parse(url).getHost())) {
//                    // This is my website, so do not override; let my WebView load the page
//                    return false;
//                }
//                // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(intent);
//                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.e("测试","onPageStarted");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("测试","onPageFinished");
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
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
}