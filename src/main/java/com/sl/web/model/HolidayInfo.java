package com.sl.web.model;

import java.io.Serializable;

public class HolidayInfo implements Serializable {
	private static final long serialVersionUID = 2965217137698637385L;
	
	private Long sofId;
	private String bdId;
	private Long shopId;
	private String sofDesc;
	private Long sofStm;
	private Long sofEtm;
	private Long crtTs;
	private Long uptTs;
	
	private String sofStmStr;
	private String sofEtmStr;
	
	public Long getSofId() {
		return sofId;
	}
	public void setSofId(Long sofId) {
		this.sofId = sofId;
	}
	public String getBdId() {
		return bdId;
	}
	public void setBdId(String bdId) {
		this.bdId = bdId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getSofDesc() {
		return sofDesc;
	}
	public void setSofDesc(String sofDesc) {
		this.sofDesc = sofDesc;
	}
	public Long getSofStm() {
		return sofStm;
	}
	public void setSofStm(Long sofStm) {
		this.sofStm = sofStm;
	}
	public Long getSofEtm() {
		return sofEtm;
	}
	public void setSofEtm(Long sofEtm) {
		this.sofEtm = sofEtm;
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
	public String getSofStmStr() {
		return sofStmStr;
	}
	public void setSofStmStr(String sofStmStr) {
		this.sofStmStr = sofStmStr;
	}
	public String getSofEtmStr() {
		return sofEtmStr;
	}
	public void setSofEtmStr(String sofEtmStr) {
		this.sofEtmStr = sofEtmStr;
	}
	
}
