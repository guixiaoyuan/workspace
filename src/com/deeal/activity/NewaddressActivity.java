package com.deeal.activity;

import com.deeal.model.Address;
import com.example.deeal.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class NewaddressActivity extends Activity {
	private Button bt_return;
	private TextView save;
	private EditText et_newaddress_name,et_newaddress_phone,et_newaddress_addrcode,et_newaddress_detail_address;
	private Spinner selectcity,selectstreet;
	private Address address;
	private String addr,name,phonenumber,addrcode,street,city;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newaddress);
		Inititems();
		setListeners();
	}
	
	private void Inititems(){
		this.bt_return=(Button) findViewById(R.id.btn_newaddress_return);
		this.save=(TextView) findViewById(R.id.save);
		this.et_newaddress_addrcode=(EditText) findViewById(R.id.et_newaddress_addrcode);
		this.et_newaddress_detail_address=(EditText) findViewById(R.id.et_newaddress_detail_address);
		this.et_newaddress_name=(EditText) findViewById(R.id.et_newaddress_name);
		this.et_newaddress_phone=(EditText) findViewById(R.id.et_newpassword);
		this.selectcity=(Spinner) findViewById(R.id.selectcity);
		this.selectstreet=(Spinner) findViewById(R.id.selectstreet);
	}
	private void setListeners(){
		bt_return.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				saveinfos();
				finish();
			}
		});
 
		
	}
	private void saveinfos(){
		addr=et_newaddress_detail_address.getText().toString();
		name=et_newaddress_name.getText().toString();
		phonenumber=et_newaddress_phone.toString();
		addrcode=et_newaddress_addrcode.getText().toString();
		address.setAddress(addr);
		address.setName(name);
		address.setTel(phonenumber);
		
		
		
		Intent intent=new Intent();
		Bundle mBundle = new Bundle(); 
		mBundle.putSerializable("newaddr",address);
		intent.putExtras(mBundle);
		intent.setClass(this, AddrManageActivity.class);
		startActivity(intent);
		
	}
}
