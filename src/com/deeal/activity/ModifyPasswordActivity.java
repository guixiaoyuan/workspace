package com.deeal.activity;

import com.deeal.view.CodeBP;
import com.example.deeal.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class ModifyPasswordActivity extends Activity {
	private EditText et_id, et_code, et_newpassword;
	private Button btn_relogin;
	private ImageButton btn_return;
	private ImageView img_code;
	private String input_code, needcode, ID, newpassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modifypassword);
		inititems();

	}

	private void inititems() {
		this.et_id = (EditText) findViewById(R.id.et_id);
		this.et_code = (EditText) findViewById(R.id.et_code);
		this.et_newpassword = (EditText) findViewById(R.id.et_newpassword);
		this.btn_relogin = (Button) findViewById(R.id.bt_relogin);
		this.btn_return = (ImageButton) findViewById(R.id.btn_findpassword_return);
		this.img_code = (ImageView) findViewById(R.id.img_code);
		img_code.setImageBitmap(CodeBP.getInstance().createBitmap());

		/*
		 * first check input_infos then match if match right then login
		 */
		btn_relogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				check();

			}
		});
		/*
		 * when click the img_codereturn new code
		 */
		img_code.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				changeImgCode();
			}
		});
		/*
		 * return main
		 */
		btn_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	/*
	 * check user's input
	 */
	public void check() {
		needcode = CodeBP.getInstance().getCode().toString();
		input_code = et_code.getText().toString();
		if (needcode.matches(input_code)) {
			Toast.makeText(this, "", 0).show();
		} else {
			Toast.makeText(this, "", 0).show();
		}
	}

	/*
	 * change into a new bitmap_code
	 */
	public void changeImgCode() {
		img_code.setImageBitmap(CodeBP.getInstance().createBitmap());
	}

}
