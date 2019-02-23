package com.sl.web.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlUserBrand implements Serializable {
	private static final long serialVersionUID = -6085244412273784337L;
	@Id
	private Long ubdId;
	private String bdId;
	private Long uId;
	private String roleId;
	private Long crtTs;
	private Long uptTs;
	
	public Long getUbdId() {
		return ubdId;
	}
	public void setUbdId(Long ubdId) {
		this.ubdId = ubdId;
	}
	public String getBdId() {
		return bdId;
	}
	public void setBdId(String bdId) {
		this.bdId = bdId;
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
	
	public SlUserBrand(){
		super();
	}
	
	public SlUserBrand(Long ubdId, String bdId, Long uId, String roleId,
			Long crtTs, Long uptTs) {
		super();
		this.ubdId = ubdId;
		this.bdId = bdId;
		this.uId = uId;
		this.roleId = roleId;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
}
