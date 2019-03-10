package com.sl.web.model;

import java.io.Serializable;

public class BarberWorkInfo implements Serializable {
	private static final long serialVersionUID = 75510971713239346L;
	
	private Long bbwId;
	private Long uId;
	private String bdId;
	private String bbwTitle;
	private Long bbwImg;
	private Long bbwLike;
	private Long crtTs;
	private Long uptTs;
	
	private String uNm;
	private String bbwImgUrl;
	private String filePfx;
	private String fileNm;
	
	public Long getBbwId() {
		return bbwId;
	}
	public void setBbwId(Long bbwId) {
		this.bbwId = bbwId;
	}
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
	public String getBbwTitle() {
		return bbwTitle;
	}
	public void setBbwTitle(String bbwTitle) {
		this.bbwTitle = bbwTitle;
	}
	public Long getBbwImg() {
		return bbwImg;
	}
	public void setBbwImg(Long bbwImg) {
		this.bbwImg = bbwImg;
	}
	public Long getBbwLike() {
		return bbwLike;
	}
	public void setBbwLike(Long bbwLike) {
		this.bbwLike = bbwLike;
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
	public String getBbwImgUrl() {
		return bbwImgUrl;
	}
	public void setBbwImgUrl(String bbwImgUrl) {
		this.bbwImgUrl = bbwImgUrl;
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
