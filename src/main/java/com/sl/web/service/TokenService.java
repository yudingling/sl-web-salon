package com.sl.web.service;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import tk.mybatis.mapper.entity.Example;

import com.alibaba.fastjson.JSON;
import com.sl.web.mapper.SlUserMapper;
import com.sl.web.model.ApiTokenResult;
import com.sl.web.model.SigninResult;
import com.sl.web.model.db.SlUser;
import com.sl.web.util.Common;
import com.sl.web.util.HttpClientUtil;

@Configuration
@Service
public class TokenService {
	private static final String sessionIdAttr = "id";
	
	@Value("${token.url}")
	private String tokenUrl;
	@Autowired
	private SlUserMapper slUserMapper;
	
	public static SigninResult getSession(HttpServletRequest request){
		return (SigninResult) request.getSession().getAttribute(sessionIdAttr);
	}
	
	public SlUser getNormalUser(String phone){
		Example example = new Example(SlUser.class);
	    example.createCriteria().andEqualTo("uThirdid", phone).andEqualTo("uActive", 1).andEqualTo("uDisabled", 0);
	    
	    List<SlUser> users = this.slUserMapper.selectByExample(example);
	    return CollectionUtils.isEmpty(users) ? null : users.get(0);
	}
	
	public SigninResult signin(SlUser user, String phoneNumber, String password, boolean keep, HttpServletRequest request, HttpServletResponse response){
		if(Common.md5(password).equals(user.getuPwd())){
			String token = this.getToken(phoneNumber, password);
			
			SigninResult reuslt = new SigninResult(user, token);
			
			request.getSession().setAttribute(sessionIdAttr, reuslt);
			request.getSession().setMaxInactiveInterval(86400);
			
			if(keep){
				Cookie cookie = new Cookie("SESSION", request.getSession().getId());
			    cookie.setPath("/");
			    cookie.setHttpOnly(true);
			    cookie.setMaxAge(Integer.MAX_VALUE);
			    response.addCookie(cookie);
			}
			
			return reuslt;
			
		}else{
			return null;
		}
	}
	
	private String getToken(String phoneNumber, String password){
		try {
			String url = String.format("%s?phoneNumber=%s&password=%s", this.tokenUrl, phoneNumber, password);
			
			String result = HttpClientUtil.httpGet(url);
			
			ApiTokenResult token = JSON.parseObject(result, ApiTokenResult.class);
			return token.getData();
			
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public void signout(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		
		//should clear the cookie if 'keep' was set when sign in
		Cookie cookie = new Cookie("SESSION", session.getId());
	    cookie.setPath("/");
	    cookie.setHttpOnly(true);
	    cookie.setMaxAge(0);
	    response.addCookie(cookie);
		
		session.removeAttribute(sessionIdAttr);
		session.invalidate();
	}
}
