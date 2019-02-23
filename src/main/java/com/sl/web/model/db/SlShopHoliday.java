package com.sl.web.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlShopHoliday implements Serializable {
	private static final long serialVersionUID = -526298778037502548L;
	@Id
	private Long sofId;
	private String bdId;
	private Long shopId;
	private String sofDesc;
	private Long sofStm;
	private Long sofEtm;
	private Long crtTs;
	private Long uptTs;
	
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
	
	public SlShopHoliday(){
		super();
	}
	
	public SlShopHoliday(Long sofId, String bdId, Long shopId, String sofDesc, 
			Long sofStm, Long sofEtm, Long crtTs, Long uptTs) {
		super();
		this.sofId = sofId;
		this.bdId = bdId;
		this.shopId = shopId;
		this.sofDesc = sofDesc;
		this.sofStm = sofStm;
		this.sofEtm = sofEtm;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
}
