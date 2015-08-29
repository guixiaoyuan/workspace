package com.deeal.tools.mycenter;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.deeal.view.RoundImageView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class UserInfoCallback extends RequestCallBack {
	private Activity activity;
	private Map<String, Object> dataMap = new HashMap<String, Object>();
	private TextView tv;
	private RoundImageView img_user_head;
	public UserInfoCallback(Activity activity, TextView tv,RoundImageView img_user_head) {
		super();
		this.activity = activity;
		this.tv = tv;
		this.img_user_head=img_user_head;
	}

	@Override
	public void onFailure(HttpException arg0, String arg1) {

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

	public void doGetDataSuccess(String string) {
		try {

			// 将返回的结果解析
			JSONObject allJson = new JSONObject(string);
			// 得到状态
			String result = allJson.getString("status");
			// 状态成功才会继续解析
			if (result.equals("success")) {
				// 得到具体的值----->也是一个json集合
				// 同样解析这个data的json集合
				JSONObject dataJson = allJson.getJSONObject("data");
				// 获取data下的数据
				// age(年龄) conste(星座) id(用户ID) img(头像相对路径) job(工作) pinfo(简介)
				// sex(性别) user_name(用户昵称（也是用户登录名）)
				int age = dataJson.getInt("age");
				// 这个不确定 枚举类
				int conste = dataJson.getInt("conste");
				int sex = dataJson.getInt("sex");
				int id = dataJson.getInt("id");
				String img = dataJson.getString("img");
				String job = dataJson.getString("job");
				String pinfo = dataJson.getString("pinfo");
				String user_name = dataJson.getString("user_name");
				System.out.println("!!!!!!!!!!!!!!"+img);
				
				tv.setText(tv.getText().toString() + user_name);
				
				BitmapUtils bitmapUtils = new BitmapUtils(activity);
				bitmapUtils.display(img_user_head, img);
				
				
			/*	// 加入Map集合中
				dataMap.put("age", age);
				dataMap.put("conste", conste);
				dataMap.put("sex", sex);
				dataMap.put("id", id);
				dataMap.put("img", img);
				dataMap.put("job", job);
				dataMap.put("pinfo", pinfo);
				dataMap.put("user_name", user_name);*/
				System.out.println("user_name:" + user_name + "\nage:" + age
						+ "\nconste:" + conste + "\nsex:" + sex + "\nid:" + id
						+ "\nimg:" + img + "\njob:" + job + "\npinfo:" + pinfo);
			}else{
				Toast.makeText(activity, "获取信息出错", Toast.LENGTH_SHORT).show();
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
