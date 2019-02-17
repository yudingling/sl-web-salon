package com.sl.web.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlBrandWechat implements Serializable {
	private static final long serialVersionUID = -136911237509855299L;
	@Id
	private Long bdwId;
	private String bdId;
	private String bdwAppid;
	private String bdwSecret;
	private String bdwWechatpayId;
	private Long crtTs;
	private Long uptTs;
	
	public Long getBdwId() {
		return bdwId;
	}
	public void setBdwId(Long bdwId) {
		this.bdwId = bdwId;
	}
	public String getBdId() {
		return bdId;
	}
	public void setBdId(String bdId) {
		this.bdId = bdId;
	}
	public String getBdwAppid() {
		return bdwAppid;
	}
	public void setBdwAppid(String bdwAppid) {
		this.bdwAppid = bdwAppid;
	}
	public String getBdwSecret() {
		return bdwSecret;
	}
	public void setBdwSecret(String bdwSecret) {
		this.bdwSecret = bdwSecret;
	}
	public String getBdwWechatpayId() {
		return bdwWechatpayId;
	}
	public void setBdwWechatpayId(String bdwWechatpayId) {
		this.bdwWechatpayId = bdwWechatpayId;
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
	
	public SlBrandWechat(){
		super();
	}
	
	public SlBrandWechat(Long bdwId, String bdId, String bdwAppid,
			String bdwSecret, String bdwWechatpayId, Long crtTs, Long uptTs) {
		super();
		this.bdwId = bdwId;
		this.bdId = bdId;
		this.bdwAppid = bdwAppid;
		this.bdwSecret = bdwSecret;
		this.bdwWechatpayId = bdwWechatpayId;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
}
