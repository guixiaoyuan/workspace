package com.deeal.tools.mycenter;

import org.json.JSONObject;

import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class CountCallback extends RequestCallBack {
	private TextView tv;
	private Activity activity;

	public CountCallback(Activity activity, TextView tv) {
		this.activity = activity;
		this.tv = tv;
	}

	@Override
	public void onFailure(HttpException arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(ResponseInfo serverResult) {
		if (null == serverResult || null == serverResult.result
				|| "" == serverResult.result) {
			doGetDataFailure();
		} else {
			doGetDataSuccess(serverResult.result.toString());
		}

	}

	private void doGetDataSuccess(String string) {
		try {
			// 将返回的结果解析
			JSONObject allJson = new JSONObject(string);
			// 得到状态
			String result = allJson.getString("status");
			// 状态成功才会继续解析
			if(result.equals("success")){
				//JSONObject dataJson = allJson.getJSONObject("data");
				//这个不确定待改正(等待服务器的返回)
				//int count=dataJson.getInt("count");
				int count=allJson.getInt("data");
				//修改view
				tv.setText(tv.getText().toString()+count);
			}else{
				Toast.makeText(activity, "获取CountCallback信息出错", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			System.out
					.println("~~~~~~~~~~~~~~~~~~~~~~~~json err~~~~~~~~~~~~~~~");
			e.printStackTrace();
		}

	}

	private void doGetDataFailure() {
		Toast.makeText(this.activity, "getInfo err", Toast.LENGTH_SHORT).show();

	}

}
