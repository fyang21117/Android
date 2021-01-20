package com.example.administrator.WeChats.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class WebDialogFragment extends DialogFragment {

	static WebDialogFragment webDialogFragment;

	public synchronized static WebDialogFragment getInstance(){
		return webDialogFragment==null? webDialogFragment = new WebDialogFragment():webDialogFragment;
	}
	public WebDialogFragment() {
		super();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}