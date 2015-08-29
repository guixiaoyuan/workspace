package com.deeal.tools;


public class URL {
	public static int port=173;
	public static String URL_LOGIN = "http://112.74.80.5/shop/interfaces/common.php";
	// public static String
	// URL_MYCENTER="http://112.74.80.5/shop/interfaces/common.php?method=getUserInfo";
	public static String URL_MyCenter = "http://192.168.1."+port+":5000/userinfo/";
	//public static String URL_GetFriendCount = "http://112.74.80.5/shop/interfaces/common.php?method=getFriendCount";
	public static String URL_GetFriendCount="http://192.168.1."+port+":5000/get_friend_count/";
	public static String URL_GetFansCount = "http://112.74.80.5/shop/interfaces/common.php?method=getFansCount";
	//个人中心的动态
	public static String URL_GetShowList="http://192.168.1."+port+":5000/get_show_list/";
	//个人中心的消息
	public static String URL_GetTalkList="http://192.168.1."+port+":5000/get_talk_list/";
	//个人中心中的好友列表
	public static String URL_GetFriendsList="http://192.168.1."+port+":5000/get_friend_list/";
	//个人中心的粉丝列表
	public static String URL_GetFansList="http://192.168.1."+port+":5000/get_fans_list/";
	public static String URL_CLOTHSE_TYPE="http://192.168.1."+port+":5000/get_clothse_list/";
	
}
