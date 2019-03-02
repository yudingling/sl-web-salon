package com.sl.web.model;

import java.io.Serializable;

public class BarberProjectInfo implements Serializable {
	private static final long serialVersionUID = -3832082063912665375L;
	
	private Long pjId;
	private String pjNm;
	private String bdId;
	private Integer pjEnable;
	private Double pjPrice;
	private Double pjHour;
	private Long crtTs;
	private Long uptTs;
	
	private Long bbpId;
	private Double bbpPrice;
	private Long uId;
	private String uNm;
	
	public Long getPjId() {
		return pjId;
	}
	public void setPjId(Long pjId) {
		this.pjId = pjId;
	}
	public String getPjNm() {
		return pjNm;
	}
	public void setPjNm(String pjNm) {
		this.pjNm = pjNm;
	}
	public String getBdId() {
		return bdId;
	}
	public void setBdId(String bdId) {
		this.bdId = bdId;
	}
	public Integer getPjEnable() {
		return pjEnable;
	}
	public void setPjEnable(Integer pjEnable) {
		this.pjEnable = pjEnable;
	}
	public Double getPjPrice() {
		return pjPrice;
	}
	public void setPjPrice(Double pjPrice) {
		this.pjPrice = pjPrice;
	}
	public Double getPjHour() {
		return pjHour;
	}
	public void setPjHour(Double pjHour) {
		this.pjHour = pjHour;
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
	
	public Double getBbpPrice() {
		return bbpPrice;
	}
	public void setBbpPrice(Double bbpPrice) {
		this.bbpPrice = bbpPrice;
	}
	public Long getuId() {
		return uId;
	}
	public void setuId(Long uId) {
		this.uId = uId;
	}
	public Long getBbpId() {
		return bbpId;
	}
	public void setBbpId(Long bbpId) {
		this.bbpId = bbpId;
	}
	public String getuNm() {
		return uNm;
	}
	public void setuNm(String uNm) {
		this.uNm = uNm;
	}
	
}
