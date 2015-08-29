package com.deeal.fragment;

import com.example.deeal.R;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class Fragment_clothmaterical_selector extends Fragment implements
		OnClickListener {
	private FragmentManager fm;
	private FragmentTransaction ft;
	private TextView tvTab1, tvTab2, tvTab3,tvTab4;
	private String type;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_clothmaterial_selector,
				null);
		Intent i = getActivity().getIntent();
		type = (String) i.getExtras().get("type");
		
		tvTab1 = (TextView) view.findViewById(R.id.tv_tab1);
		tvTab2 = (TextView) view.findViewById(R.id.tv_tab2);
		tvTab3 = (TextView) view.findViewById(R.id.tv_tab3);
		tvTab4 = (TextView) view.findViewById(R.id.tv_tab4);
		settab();
		tvTab1.setOnClickListener(this);
		tvTab2.setOnClickListener(this);
		tvTab3.setOnClickListener(this);
		tvTab4.setOnClickListener(this);
		
		return view;
	}

	public void resettab() {
		tvTab1.setBackgroundColor(Color.GRAY);
		tvTab2.setBackgroundColor(Color.GRAY);
		tvTab3.setBackgroundColor(Color.GRAY);
		tvTab4.setBackgroundColor(Color.GRAY);
		tvTab1.setTextColor(Color.WHITE);
		tvTab2.setTextColor(Color.WHITE);
		tvTab3.setTextColor(Color.WHITE);
		tvTab4.setTextColor(Color.WHITE);
	}
	
	public void settab() {
		tvTab1.setBackground(null);
		tvTab2.setBackgroundColor(Color.GRAY);
		tvTab3.setBackgroundColor(Color.GRAY);
		tvTab4.setBackgroundColor(Color.GRAY);
		tvTab1.setTextColor(Color.BLACK);
		tvTab2.setTextColor(Color.WHITE);
		tvTab3.setTextColor(Color.WHITE);
		tvTab4.setTextColor(Color.WHITE);
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		resettab();

		switch (v.getId()) {
		case R.id.tv_tab1:

			tvTab1.setBackground(null);
			tvTab1.setTextColor(Color.BLACK);
			break;
		case R.id.tv_tab2:

			tvTab2.setBackground(null);
			tvTab2.setTextColor(Color.BLACK);
			break;
		case R.id.tv_tab3:

			tvTab3.setBackground(null);
			tvTab3.setTextColor(Color.BLACK);
			break;
		case R.id.tv_tab4:

			tvTab4.setBackground(null);
			tvTab4.setTextColor(Color.BLACK);
			break;
		}
		

	}
}
