package com.deeal.tools.portrait;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deeal.model.login.LoginResult;
import com.deeal.model.login.LoginToken;
import com.deeal.model.login.UserInfo;

public class UserInfoParser {
	private String jsonUserInfo;
	
	public UserInfoParser(String json) {
		// TODO Auto-generated constructor stub
		this.jsonUserInfo = json;
	}
	
	public String getJsonUserInfo() {
		return jsonUserInfo;
	}

	public void setJsonUserInfo(String jsonUserInfo) {
		this.jsonUserInfo = jsonUserInfo;
	}
	
	public UserInfo getUserInfo(){
		UserInfo userInfo = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			userInfo = objectMapper.readValue(this.jsonUserInfo, UserInfo.class);
		}catch(JsonGenerationException e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return userInfo;
	}
}
