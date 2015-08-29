package com.deeal.tools.mycenter;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.conn.params.ConnManagerParams;

import android.app.Activity;
import android.widget.TextView;

import com.deeal.model.login.LoginToken;
import com.deeal.model.mycenter.MyCenterInfo;
import com.deeal.tools.URL;
import com.deeal.view.RoundImageView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class MyCenterManager {
	private MyCenterInfo myCenterInfo;
	private LoginToken loginToken;
	// 构造函数里的参数
	private Activity activity;
	private RoundImageView img_user_head;
	private String user_name;
	private String friends_number;
	private String fans_number;
	private String imgUrl;
	private TextView tv_user_name, tv_friend_count, tv_fans_count;
	// 用户信息的Map集合
	private Map<String, Object> dataMap = new HashMap<String, Object>();

	public MyCenterManager(LoginToken loginToken,
			Activity activity) {
		this.loginToken = loginToken;
		this.activity = activity;
		/*
		 * this.tv_user_name=tv_user_name; this.tv_friend_count=tv_friend_count;
		 * this.tv_fans_count=tv_fans_count;
		 */

	}

	// 自己本地的测试数据
	public MyCenterInfo setMyCenterInfo() {
		user_name = "anthow";
		friends_number = "好友数" + 100;
		fans_number = "粉丝数" + 100;
		myCenterInfo = new MyCenterInfo(img_user_head, user_name,
				friends_number, fans_number, imgUrl, null);
		return myCenterInfo;
	}

	/*
	 * 异步从网络获取数据 return UserInfo，即先前文档中提供的UserInfo结构
	 */
	// 获取个人信息
	@SuppressWarnings("unchecked")
	public void getUserInfo(TextView tv_user_name,RoundImageView img_user_head) {
		this.tv_user_name=tv_user_name;
		this.img_user_head=img_user_head;
		UserInfoCallback callback = new UserInfoCallback(activity, tv_user_name,img_user_head);
		// RequestParams params = new RequestParams();

		// params.addBodyParameter("method", MethodSet.getUserInfo);
		// 这个参数还不确定~~~~~~~~~~
		// params.addBodyParameter("arg0", loginToken.getToken());
		HttpUtils httpUtils = new HttpUtils();
		/* httpUtils.configTimeout(50000); */

		httpUtils.send(HttpMethod.POST, URL.URL_MyCenter, callback);
	}

	// 获取好友数量
	public void getFriendCount(TextView tv_friend_count) {
		this.tv_friend_count=tv_friend_count;
		CountCallback callBack = new CountCallback(activity, tv_friend_count);

		/*
		 * RequestParams params=new RequestParams();
		 * params.addBodyParameter("arg0",loginToken.getToken());
		 */

		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.POST, URL.URL_GetFriendCount, callBack);
	}

	// 获取粉丝数量
	public void getFansCount(TextView tv_fans_count) {
		this.tv_fans_count=tv_fans_count;
		CountCallback callBack = new CountCallback(activity, tv_fans_count);

		/*
		 * RequestParams params=new RequestParams();
		 * params.addBodyParameter("arg0", loginToken.getToken());
		 */

		HttpUtils httpUtils = new HttpUtils();
		// ///////////////////这个到时候要修改的
		httpUtils.send(HttpMethod.POST, URL.URL_GetFriendCount, callBack);

	}
}
