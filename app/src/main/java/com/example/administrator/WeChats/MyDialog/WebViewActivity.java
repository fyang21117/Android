package com.example.administrator.WeChats.MyDialog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.administrator.WeChats.R;

public class WebViewActivity extends AppCompatActivity {

    AlertDialog.Builder builder;
    String TAG = "WebViewActivity";
    public static WebviewDialog dialog;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 非dialog实现js调用Android
         * */
//        setContentView(R.layout.activity_webview);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        WebView mWebView = new WebView(getApplicationContext());
//        mWebView.setLayoutParams(params);
//        showWebView(mWebView,"file:///android_asset/javascript.html");

//        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_webview, null);
//        viewGroup.addView(mWebView,0,params);

////        mWebView = findViewById(R.id.muiltiWebView);
//        setContentView(viewGroup);

        /**
         * 系统dialog实现js调用Android
         * */
        setContentView(R.layout.activity_webview);
        //主活动创建方式
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);//
//        WebView mWebView = new WebView(getApplicationContext());
//        mWebView.setLayoutParams(params);
//        showWebView(mWebView,"file:///android_asset/javascript.html");
//
//        builder = new AlertDialog.Builder(this);
//        builder.setView(mWebView);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        }).show();

        //自定义dialog实现

        /**
         * 自定义dialog实现js调用Android
         * */
        dialog = new WebviewDialog(this);
        dialog.show();


    }
}