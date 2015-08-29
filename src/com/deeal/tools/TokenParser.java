package com.deeal.tools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.JsonNodeFactory;

import android.util.Log;

import com.deeal.model.login.LoginResult;
import com.deeal.model.login.LoginToken;

public class TokenParser {
	private LoginToken token;
	private LoginResult result;
	private String jsonString;
	private Map<String, Object> m;
	
	public TokenParser(String jStr) {
		// TODO Auto-generated constructor stub
		this.jsonString = jStr;
	}
	
	public LoginResult getLoginResult(){
		ObjectMapper objectMapper = new ObjectMapper();
		try {
//			this.token = objectMapper.readValue(this.jsonString, LoginToken.class);
//			if(null == token){
//				System.out.println("xxxxxxxxxxxxxxx token is null xxxxxxxxxxx");	
//			}
			m = objectMapper.readValue(this.jsonString, Map.class);
			String status = m.get("status").toString();
			if ("success" != status){
				return null;
			}
			HashMap<String, String> hm = (HashMap<String, String>) m.get("data");
			String userId = hm.get("user_id");
			String t = hm.get("token");
			token = new LoginToken(t, userId);
			result = new LoginResult();
			result.setStatus(status);
			result.setToken(token);
		}catch(JsonGenerationException e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return this.result;
	}
}
