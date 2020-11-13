package com.example.administrator.WeChats;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public  class FragmentAdapter extends FragmentPagerAdapter {

	private List<Fragment> mFragments;
//	public static List<Fragment> mFrags = new ArrayList<>();

	Fragment fragment4 = new FourFragment();
	Fragment fragment5 = new FiveFragment();

	public FragmentAdapter(@NonNull FragmentManager fm, int behavior) {
		super(fm, behavior);
	}

	public FragmentAdapter(List<Fragment> fragments, FragmentManager fm) {
		super(fm);
		this.mFragments = fragments;
	}

	public FragmentAdapter(FragmentManager fragmentManager){
		super(fragmentManager);
//		mFrags.add(fragment4);
//		mFrags.add(fragment5);
	}



	@Override
	public Fragment getItem(int i) {
		return mFragments.get(i);
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}
}