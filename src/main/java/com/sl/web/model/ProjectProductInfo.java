package com.sl.web.model;

import java.io.Serializable;

public class ProjectProductInfo implements Serializable {
	private static final long serialVersionUID = 8462343747975113795L;
	
	private Long pjpId;
	private Long pjId;
	private Long pdtpId;
	private Long pdId;
	private Long crtTs;
	private Long uptTs;
	
	private String pdtpNm;
	private String pdNm;
	private Double pdPrice;
	private Long pdIcon;
	private String pdIconUrl;
	private String filePfx;
	private String fileNm;
	
	public Long getPjpId() {
		return pjpId;
	}

	public void setPjpId(Long pjpId) {
		this.pjpId = pjpId;
	}

	public Long getPjId() {
		return pjId;
	}

	public void setPjId(Long pjId) {
		this.pjId = pjId;
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

	public String getPdNm() {
		return pdNm;
	}

	public void setPdNm(String pdNm) {
		this.pdNm = pdNm;
	}

	public String getPdIconUrl() {
		return pdIconUrl;
	}

	public void setPdIconUrl(String pdIconUrl) {
		this.pdIconUrl = pdIconUrl;
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
