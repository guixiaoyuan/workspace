package com.deeal.tools;

import com.deeal.fragment.Fragment_fashionshow;
import com.deeal.fragment.Fragment_fashionshuo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter
{

	public static String[] TITLES = new String[]
	{ "FASHION秀", "FASHIO说" };

	public TabAdapter(FragmentManager fm)
	{
		super(fm);
	}

	@Override
	public Fragment getItem(int position)
	{	Fragment fragment = null;
		switch(position){
		case 0:
			fragment=new Fragment_fashionshow();
			break;
		case 1:
			fragment=new Fragment_fashionshuo();
			break;
		}
		return fragment;
		
	}

	@Override
	public int getCount()
	{
		return TITLES.length;
	}

	@Override
	public CharSequence getPageTitle(int position)
	{
		return TITLES[position];
	}

}
