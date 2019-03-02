package com.sl.web.model;

import java.io.Serializable;

public class UserInfo implements Serializable {
	private static final long serialVersionUID = 2616492310072689825L;
	
	private Long uId;
	private String bdId;
	private String uNm;
	private String uThirdid;
	private String uPhone;
	private String uEmail;
	private String uPwd;
	private String roleId;
	private Integer uActive;
	private Integer uDisabled;
	private Long uIcon;
	private String uAvatar;
	private Long crtTs;
	private Long uptTs;
	
	private String uIconUrl;
	private String filePfx;
	private String fileNm;
	private String newPwd;
	private String newPwd2;
	
	public Long getuId() {
		return uId;
	}

	public void setuId(Long uId) {
		this.uId = uId;
	}

	public String getBdId() {
		return bdId;
	}

	public void setBdId(String bdId) {
		this.bdId = bdId;
	}

	public String getuNm() {
		return uNm;
	}

	public void setuNm(String uNm) {
		this.uNm = uNm;
	}

	public String getuThirdid() {
		return uThirdid;
	}

	public void setuThirdid(String uThirdid) {
		this.uThirdid = uThirdid;
	}

	public String getuPhone() {
		return uPhone;
	}

	public void setuPhone(String uPhone) {
		this.uPhone = uPhone;
	}

	public String getuEmail() {
		return uEmail;
	}

	public void setuEmail(String uEmail) {
		this.uEmail = uEmail;
	}

	public String getuPwd() {
		return uPwd;
	}

	public void setuPwd(String uPwd) {
		this.uPwd = uPwd;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Integer getuActive() {
		return uActive;
	}

	public void setuActive(Integer uActive) {
		this.uActive = uActive;
	}

	public Integer getuDisabled() {
		return uDisabled;
	}

	public void setuDisabled(Integer uDisabled) {
		this.uDisabled = uDisabled;
	}

	public Long getuIcon() {
		return uIcon;
	}

	public void setuIcon(Long uIcon) {
		this.uIcon = uIcon;
	}

	public String getuAvatar() {
		return uAvatar;
	}

	public void setuAvatar(String uAvatar) {
		this.uAvatar = uAvatar;
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

	public String getuIconUrl() {
		return uIconUrl;
	}

	public void setuIconUrl(String uIconUrl) {
		this.uIconUrl = uIconUrl;
	}

	public String getFilePfx() {
		return filePfx;
	}

	public void setFilePfx(String filePfx) {
		this.filePfx = filePfx;
	}

	public String getFileNm() {
		return fileNm;
	}

	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getNewPwd2() {
		return newPwd2;
	}

	public void setNewPwd2(String newPwd2) {
		this.newPwd2 = newPwd2;
	}
	
}
