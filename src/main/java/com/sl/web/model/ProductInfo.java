package com.sl.web.model;

import java.io.Serializable;

public class ProductInfo implements Serializable {
	private static final long serialVersionUID = 6886103257807617819L;
	
	private Long pdId;
	private String pdNm;
	private String bdId;
	private Long pdtpId;
	private String pdDesc;
	private Long pdIcon;
	private Double pdPrice;
	private Integer pdEnable;
	private Long crtTs;
	private Long uptTs;
	
	private String pdtpNm;
	private String pdIconUrl;
	private String filePfx;
	private String fileNm;
	
	public Long getPdId() {
		return pdId;
	}
	public void setPdId(Long pdId) {
		this.pdId = pdId;
	}
	public String getPdNm() {
		return pdNm;
	}
	public void setPdNm(String pdNm) {
		this.pdNm = pdNm;
	}
	public String getBdId() {
		return bdId;
	}
	public void setBdId(String bdId) {
		this.bdId = bdId;
	}
	public Long getPdtpId() {
		return pdtpId;
	}
	public void setPdtpId(Long pdtpId) {
		this.pdtpId = pdtpId;
	}
	public String getPdDesc() {
		return pdDesc;
	}
	public void setPdDesc(String pdDesc) {
		this.pdDesc = pdDesc;
	}
	public Long getPdIcon() {
		return pdIcon;
	}
	public void setPdIcon(Long pdIcon) {
		this.pdIcon = pdIcon;
	}
	public Double getPdPrice() {
		return pdPrice;
	}
	public void setPdPrice(Double pdPrice) {
		this.pdPrice = pdPrice;
	}
	public Integer getPdEnable() {
		return pdEnable;
	}
	public void setPdEnable(Integer pdEnable) {
		this.pdEnable = pdEnable;
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
	public String getPdtpNm() {
		return pdtpNm;
	}
	public void setPdtpNm(String pdtpNm) {
		this.pdtpNm = pdtpNm;
	}
	
}
