package com.sl.web.model.db;

import java.io.Serializable;

import javax.persistence.Id;

public class SlFile implements Serializable {
	private static final long serialVersionUID = -4591731939978994699L;
	@Id
	private Long fileId;
	private String filePfx;
	private String fileNm;
	private Integer filePrivate;
	private Long uId;
	private Long crtTs;
	private Long uptTs;
	
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
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
	public Integer getFilePrivate() {
		return filePrivate;
	}
	public void setFilePrivate(Integer filePrivate) {
		this.filePrivate = filePrivate;
	}
	public Long getuId() {
		return uId;
	}
	public void setuId(Long uId) {
		this.uId = uId;
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
	
	public SlFile(){
		super();
	}
	
	public SlFile(Long fileId, String filePfx, String fileNm,
			Integer filePrivate, Long uId, Long crtTs, Long uptTs) {
		super();
		this.fileId = fileId;
		this.filePfx = filePfx;
		this.fileNm = fileNm;
		this.filePrivate = filePrivate;
		this.uId = uId;
		this.crtTs = crtTs;
		this.uptTs = uptTs;
	}
	
}
