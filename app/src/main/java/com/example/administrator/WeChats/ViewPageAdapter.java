package com.example.administrator.WeChats;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public final class ViewPageAdapter extends PagerAdapter {

	private List<View> mViews;
	private List<String> mTitles;

	public ViewPageAdapter(List<View> views) {
		this(views, null);
	}

	public ViewPageAdapter(List<View> views, List<String> titles) {
		this.mViews = views;
		this.mTitles = titles;
		if (mTitles == null) {
			mTitles = new ArrayList<>();
		}
	}

	@NonNull
	@Override
	public Object instantiateItem(@NonNull ViewGroup container, int position) {
		container.addView(mViews.get(position));
		return mViews.get(position);
	}

	@Override
	public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
		container.removeView(mViews.get(position));
	}

	@Nullable
	@Override
	public CharSequence getPageTitle(int position) {
		return mTitles.size() > 0 ? mTitles.get(position) : "";
	}

	@Override
	public int getCount() {
		return mViews.size();
	}

	@Override
	public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
		return view == obj;
	}
}
