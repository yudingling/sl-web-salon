package com.sl.web.model;

import java.io.Serializable;

import com.sl.web.util.Common;

public class BrandInfo implements Serializable {
	private static final long serialVersionUID = -171021534960825801L;
	
	private String bdId;
	private String bdNm;
	private Long bdLogo;
	private String bdUrl;
	
	private String filePfx;
	private String fileNm;
	
	private String bdwAppid;
	private String bdwSecret;
	private String bdwWechatpayId;
	
	private String bdLogoUrl;
	
	public String getBdId() {
		return bdId;
	}
	public void setBdId(String bdId) {
		this.bdId = Common.valString(bdId);
	}
	public String getBdNm() {
		return bdNm;
	}
	public void setBdNm(String bdNm) {
		this.bdNm = Common.valString(bdNm);
	}
	public Long getBdLogo() {
		return bdLogo;
	}
	public void setBdLogo(Long bdLogo) {
		this.bdLogo = bdLogo;
	}
	public String getBdLogoUrl() {
		return bdLogoUrl;
	}
	public void setBdLogoUrl(String bdLogoUrl) {
		this.bdLogoUrl = bdLogoUrl;
	}
	public String getBdUrl() {
		return bdUrl;
	}
	public void setBdUrl(String bdUrl) {
		this.bdUrl = Common.valString(bdUrl);
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
	public String getBdwAppid() {
		return bdwAppid;
	}
	public void setBdwAppid(String bdwAppid) {
		this.bdwAppid = Common.valString(bdwAppid);
	}
	public String getBdwSecret() {
		return bdwSecret;
	}
	public void setBdwSecret(String bdwSecret) {
		this.bdwSecret = Common.valString(bdwSecret);
	}
	public String getBdwWechatpayId() {
		return bdwWechatpayId;
	}
	public void setBdwWechatpayId(String bdwWechatpayId) {
		this.bdwWechatpayId = Common.valString(bdwWechatpayId);
	}
}
