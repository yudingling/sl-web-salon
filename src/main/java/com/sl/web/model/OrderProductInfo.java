package com.sl.web.model;

import java.io.Serializable;

public class OrderProductInfo implements Serializable {
	private static final long serialVersionUID = -6681997116040710408L;
	
	private Long odpId;
	private Long odId;
	private Long pdtpId;
	private Long pdId;
	private Double odpPrice;
	private Long crtTs;
	private Long uptTs;
	
	private String pdNm;
	private String pdtpNm;
	private Long pdIcon;
	private String pdIconUrl;
	private String filePfx;
	private String fileNm;
	
	public Long getOdpId() {
		return odpId;
	}
	public void setOdpId(Long odpId) {
		this.odpId = odpId;
	}
	public Long getOdId() {
		return odId;
	}
	public void setOdId(Long odId) {
		this.odId = odId;
	}
	public Long getPdtpId() {
		return pdtpId;
	}
	public void setPdtpId(Long pdtpId) {
		this.pdtpId = pdtpId;
	}
	public Long getPdId() {
		return pdId;
	}
	public void setPdId(Long pdId) {
		this.pdId = pdId;
	}
	public Double getOdpPrice() {
		return odpPrice;
	}
	public void setOdpPrice(Double odpPrice) {
		this.odpPrice = odpPrice;
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
	public String getPdtpNm() {
		return pdtpNm;
	}
	public void setPdtpNm(String pdtpNm) {
		this.pdtpNm = pdtpNm;
	}
	public String getPdIconUrl() {
		return pdIconUrl;
	}
	public void setPdIconUrl(String pdIconUrl) {
		this.pdIconUrl = pdIconUrl;
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
	public String getPdNm() {
		return pdNm;
	}
	public void setPdNm(String pdNm) {
		this.pdNm = pdNm;
	}
	public Long getPdIcon() {
		return pdIcon;
	}
	public void setPdIcon(Long pdIcon) {
		this.pdIcon = pdIcon;
	}
	
}
