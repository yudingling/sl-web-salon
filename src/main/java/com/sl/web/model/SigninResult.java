package com.sl.web.model;

import java.io.Serializable;

import com.sl.web.model.db.SlUser;

public class SigninResult implements Serializable {
	private static final long serialVersionUID = -2789000813310427409L;
	
	private String brandId;
	private Long userId;
	private String roleId;
	private String token;
	
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public SigninResult(){
		super();
	}
	
	public SigninResult(String brandId, Long userId, String roleId, String token) {
		super();
		this.brandId = brandId;
		this.userId = userId;
		this.roleId = roleId;
		this.token = token;
	}
	
	public SigninResult(SlUser user, String token){
		super();
		this.brandId = user.getBdId();
		this.userId = user.getuId();
		this.roleId = user.getRoleId();
		this.token = token;
	}
}
