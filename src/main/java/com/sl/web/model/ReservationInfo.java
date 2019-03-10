package com.sl.web.model;

import java.io.Serializable;

public class ReservationInfo implements Serializable {
	private static final long serialVersionUID = -8466213948563479459L;
	
	private Long rvId;
	private Long shopId;
	private Long rvUid;
	private Long rvBarberUid;
	private Long pjId;
	private Long rvStm;
	private Long rvEtm;
	private Integer rvAvailable;
	private Integer rvActive;
	private Integer rvIssystem;
	private Long crtTs;
	private Long uptTs;
	
	private String rvUnm;
	private String rvUphone;
	private String rvBarberUnm;
	private String pjNm;
	
	public Long getRvId() {
		return rvId;
	}
	public void setRvId(Long rvId) {
		this.rvId = rvId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getRvUid() {
		return rvUid;
	}
	public void setRvUid(Long rvUid) {
		this.rvUid = rvUid;
	}
	public Long getRvBarberUid() {
		return rvBarberUid;
	}
	public void setRvBarberUid(Long rvBarberUid) {
		this.rvBarberUid = rvBarberUid;
	}
	public Long getPjId() {
		return pjId;
	}
	public void setPjId(Long pjId) {
		this.pjId = pjId;
	}
	public Long getRvStm() {
		return rvStm;
	}
	public void setRvStm(Long rvStm) {
		this.rvStm = rvStm;
	}
	public Long getRvEtm() {
		return rvEtm;
	}
	public void setRvEtm(Long rvEtm) {
		this.rvEtm = rvEtm;
	}
	public Integer getRvAvailable() {
		return rvAvailable;
	}
	public void setRvAvailable(Integer rvAvailable) {
		this.rvAvailable = rvAvailable;
	}
	public Integer getRvActive() {
		return rvActive;
	}
	public void setRvActive(Integer rvActive) {
		this.rvActive = rvActive;
	}
	public Integer getRvIssystem() {
		return rvIssystem;
	}
	public void setRvIssystem(Integer rvIssystem) {
		this.rvIssystem = rvIssystem;
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
	public String getRvUnm() {
		return rvUnm;
	}
	public void setRvUnm(String rvUnm) {
		this.rvUnm = rvUnm;
	}
	public String getRvUphone() {
		return rvUphone;
	}
	public void setRvUphone(String rvUphone) {
		this.rvUphone = rvUphone;
	}
	public String getRvBarberUnm() {
		return rvBarberUnm;
	}
	public void setRvBarberUnm(String rvBarberUnm) {
		this.rvBarberUnm = rvBarberUnm;
	}
	public String getPjNm() {
		return pjNm;
	}
	public void setPjNm(String pjNm) {
		this.pjNm = pjNm;
	}
	
}
