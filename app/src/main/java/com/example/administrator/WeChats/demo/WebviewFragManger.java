package com.example.administrator.WeChats.demo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class WebviewFragManger extends FragmentActivity {
	WebviewFragManger manger;
	FragmentManager fragmentManager;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fragmentManager = getSupportFragmentManager();
	}


}
