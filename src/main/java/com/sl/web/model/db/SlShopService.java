package com.sl.web.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlShopService implements Serializable {
	private static final long serialVersionUID = 2886641845911122657L;
	@Id
	private Long spsId;
	private Long shopId;
	private Long spsStm;
	private Long spsEtm;
	private Integer spsAvailable;
	private Long crtTs;
	private Long uptTs;
	
	public Long getSpsId() {
		return spsId;
	}
	public void setSpsId(Long spsId) {
		this.spsId = spsId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getSpsStm() {
		return spsStm;
	}
	public void setSpsStm(Long spsStm) {
		this.spsStm = spsStm;
	}
	public Long getSpsEtm() {
		return spsEtm;
	}
	public void setSpsEtm(Long spsEtm) {
		this.spsEtm = spsEtm;
	}
	public Integer getSpsAvailable() {
		return spsAvailable;
	}
	public void setSpsAvailable(Integer spsAvailable) {
		this.spsAvailable = spsAvailable;
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
	
	public SlShopService(){
		super();
	}
	
	public SlShopService(Long spsId, Long shopId, Long spsStm, Long spsEtm,
			Integer spsAvailable, Long crtTs, Long uptTs) {
		super();
		this.spsId = spsId;
		this.shopId = shopId;
		this.spsStm = spsStm;
		this.spsEtm = spsEtm;
		this.spsAvailable = spsAvailable;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
}
