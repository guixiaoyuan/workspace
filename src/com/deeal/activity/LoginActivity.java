package com.deeal.activity;

import com.deeal.model.login.LoginUserInfo;
import com.deeal.model.login.UserInfo;
import com.deeal.tools.login.Login;
import com.deeal.tools.register.PermissionTokenGenerator;
import com.example.deeal.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText input_id, input_password;
	private Button btn_login;
	private TextView tv_forgetpassword;
	private ImageButton bt_return;
	private String id;
	private String password;
	private Intent lastIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		InitItems();
		setListeners();
		this.lastIntent = getIntent();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	

	private void InitItems() {
		this.bt_return = (ImageButton) this.findViewById(R.id.bt_return);
		this.btn_login = (Button) this.findViewById(R.id.bt_login);
		this.input_id = (EditText) this.findViewById(R.id.input_id);
		this.input_password = (EditText) this.findViewById(R.id.input_password);
		this.tv_forgetpassword = (TextView) this
				.findViewById(R.id.tv_forgetpassword);
	}
	
	private void setListeners(){
		/*
		 * 
		 */
		bt_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		/*
		 * 
		 */
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				login();

			}
		});
		/*
		 * 
		 */
		tv_forgetpassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				findpassword();
			}
		});
	}
	/*
	 * 
	 */
	private void findpassword() {
		Intent intent = new Intent();
		intent.setClass(this, FindpasswordActivity.class);
		startActivity(intent);
		finish();
	}
	/*
	 * 
	 */
	public void login() {
		String name = input_id.getText().toString();
		String password = PermissionTokenGenerator.Encrypt(input_password.getText().toString());
		LoginUserInfo user = new LoginUserInfo(name, password);
		Login login = new Login(user, this, this.lastIntent);
		login.doLogin();
	}

}
