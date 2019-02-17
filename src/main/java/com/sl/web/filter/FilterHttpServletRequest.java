package com.sl.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import com.sl.web.model.SigninResult;

public class FilterHttpServletRequest extends HttpServletRequestWrapper {
	private SigninResult auth;
	
	public FilterHttpServletRequest(HttpServletRequest request, SigninResult auth) {
		super(request);
		
		this.auth = auth;
	}

	public SigninResult getAuth() {
		return auth;
	}

	public void setAuth(SigninResult auth) {
		this.auth = auth;
	}
	
}
