package com.sl.web.model;

import java.io.Serializable;

public class EventInfo implements Serializable {
	private static final long serialVersionUID = -5334188150221408817L;
	
	private Long eventId;
	private String eventNm;
	private String bdId;
	private Long shopId;
	private Long eventImg;
	private String eventUrl;
	private Long eventStm;
	private Long eventEtm;
	private Integer eventAvailable;
	private Long crtTs;
	private Long uptTs;
	
	private String eventImgUrl;
	private String eventStmStr;
	private String eventEtmStr;
	
	private String filePfx;
	private String fileNm;
	
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public String getEventNm() {
		return eventNm;
	}
	public void setEventNm(String eventNm) {
		this.eventNm = eventNm;
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
	public Long getEventImg() {
		return eventImg;
	}
	public void setEventImg(Long eventImg) {
		this.eventImg = eventImg;
	}
	public String getEventUrl() {
		return eventUrl;
	}
	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}
	public Long getEventStm() {
		return eventStm;
	}
	public void setEventStm(Long eventStm) {
		this.eventStm = eventStm;
	}
	public Long getEventEtm() {
		return eventEtm;
	}
	public void setEventEtm(Long eventEtm) {
		this.eventEtm = eventEtm;
	}
	public Integer getEventAvailable() {
		return eventAvailable;
	}
	public void setEventAvailable(Integer eventAvailable) {
		this.eventAvailable = eventAvailable;
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
	public String getEventImgUrl() {
		return eventImgUrl;
	}
	public void setEventImgUrl(String eventImgUrl) {
		this.eventImgUrl = eventImgUrl;
	}
	public String getEventStmStr() {
		return eventStmStr;
	}
	public void setEventStmStr(String eventStmStr) {
		this.eventStmStr = eventStmStr;
	}
	public String getEventEtmStr() {
		return eventEtmStr;
	}
	public void setEventEtmStr(String eventEtmStr) {
		this.eventEtmStr = eventEtmStr;
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
	
}
