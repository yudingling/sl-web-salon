package com.sl.web.model;

import java.io.Serializable;

public class BarberShieldInfo implements Serializable {
	private static final long serialVersionUID = 7400627198470696673L;
	
	private Long bbtId;
	private Long uId;
	private Long bbtStm;
	private Long bbtEtm;
	private String bbtDesc;
	private Long crtTs;
	private Long uptTs;
	
	private String uNm;
	private String bbtStmStr;
	private String bbtEtmStr;
	
	public Long getBbtId() {
		return bbtId;
	}
	public void setBbtId(Long bbtId) {
		this.bbtId = bbtId;
	}
	public Long getuId() {
		return uId;
	}
	public void setuId(Long uId) {
		this.uId = uId;
	}
	public Long getBbtStm() {
		return bbtStm;
	}
	public void setBbtStm(Long bbtStm) {
		this.bbtStm = bbtStm;
	}
	public Long getBbtEtm() {
		return bbtEtm;
	}
	public void setBbtEtm(Long bbtEtm) {
		this.bbtEtm = bbtEtm;
	}
	public String getBbtDesc() {
		return bbtDesc;
	}
	public void setBbtDesc(String bbtDesc) {
		this.bbtDesc = bbtDesc;
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
	public String getuNm() {
		return uNm;
	}
	public void setuNm(String uNm) {
		this.uNm = uNm;
	}
	public String getBbtStmStr() {
		return bbtStmStr;
	}
	public void setBbtStmStr(String bbtStmStr) {
		this.bbtStmStr = bbtStmStr;
	}
	public String getBbtEtmStr() {
		return bbtEtmStr;
	}
	public void setBbtEtmStr(String bbtEtmStr) {
		this.bbtEtmStr = bbtEtmStr;
	}
	
	
}
