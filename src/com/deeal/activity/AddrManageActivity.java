/**
 * 
 */
package com.deeal.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deeal.model.Address;
import com.example.deeal.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * @author Administrator
 * 
 */
public class AddrManageActivity extends Activity {
	private Button bt_addaddr;
	private ImageButton bt_return;
	private ListView lv_addr;
	private Address address = new Address();
	private List<Address> addresses = new ArrayList<Address>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addrmanage);
		Address a = new Address();
		a.setAddress("JiangSu");
		a.setCity(1);
		a.setName("����ʤ");
		//a.setPhonenumber("18362972599");
		a.setTel("15062225371");
		a.setDistrict(1);
		addresses.add(a);
		Inititems();
		setListeners();
	}

	private void Inititems() {
		this.bt_addaddr = (Button) findViewById(R.id.bt_addr_add);
		this.bt_return = (ImageButton) findViewById(R.id.btn_addrmanage_return);
		this.lv_addr = (ListView) findViewById(R.id.addr_listview);
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		// addresses.add((Address) getIntent().getSerializableExtra("newaddr"));

		for (int i = 0; i < addresses.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			address = addresses.get(i);
			map.put("name", address.getName());
			map.put("phonenumber", address.getTel());
			map.put("addr", address.getCity() + "_" + address.getDistrict() + "_"
					+ address.getAddress());
		}
		String[] from = { "name", "phonenumber", "addr" };
		int[] to = { R.id.addr_name, R.id.addr_phonenumber, R.id.addr_addr };
		ListAdapter adapter = new SimpleAdapter(this, data,
				R.layout.addr_list_item, from, to);
		lv_addr.setAdapter(adapter);

	}

	private void setListeners() {
		bt_addaddr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				newAddress();
			}
		});

		bt_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		lv_addr.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

			}
		});
	}

	public void newAddress() {
		Intent intent = new Intent();
		intent.setClass(this, NewaddressActivity.class);
		startActivity(intent);
	}
}
