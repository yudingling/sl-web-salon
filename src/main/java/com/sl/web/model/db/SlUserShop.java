package com.sl.web.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlUserShop implements Serializable {
	private static final long serialVersionUID = -8992714596220967499L;
	@Id
	private Long uspId;
	private Long shopId;
	private Long uId;
	private String roleId;
	private Long crtTs;
	private Long uptTs;
	
	public Long getUspId() {
		return uspId;
	}
	public void setUspId(Long uspId) {
		this.uspId = uspId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getuId() {
		return uId;
	}
	public void setuId(Long uId) {
		this.uId = uId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public Long getCrtTs() {
		return crtTs;
	}
	public void setCrtTs(Long crtTs) {
		this.crtTs = crtTs;
	}
	public Long getUptTs() {
		return uptTs;
	}
	public void setUptTs(Long uptTs) {
		this.uptTs = uptTs;
	}
	
	public SlUserShop(){
		super();
	}
	
	public SlUserShop(Long uspId, Long shopId, Long uId, String roleId,
			Long crtTs, Long uptTs) {
		super();
		this.uspId = uspId;
		this.shopId = shopId;
		this.uId = uId;
		this.roleId = roleId;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
}
