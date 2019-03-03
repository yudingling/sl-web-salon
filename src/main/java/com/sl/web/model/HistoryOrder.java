package com.sl.web.model;

import java.io.Serializable;

public class HistoryOrder implements Serializable {
	private static final long serialVersionUID = 4774303199208014058L;
	
	private Long odId;
	private Long shopId;
	private String shopNm;
	private Long odUid;
	private String odUnm;
	private String odUphone;
	private Long odBarberUid;
	private String odBarberUnm;
	private Long odStm;
	private Long pjId;
	private String pjNm;
	private Double odTotalPrice;
	private Double odDiscount;
	private Double odOfferPrice;
	private Double odVoucherPrice;
	private Double odPayPrice;
	private Integer odPaied;
	private Long odPaiedTs;
	private String odPaiedTp;
	private Integer odEva;
	private String odEvaDesc;
	private Integer odConfirm;
	
	public Long getOdId() {
		return odId;
	}
	public void setOdId(Long odId) {
		this.odId = odId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getShopNm() {
		return shopNm;
	}
	public void setShopNm(String shopNm) {
		this.shopNm = shopNm;
	}
	public Long getOdUid() {
		return odUid;
	}
	public void setOdUid(Long odUid) {
		this.odUid = odUid;
	}
	public String getOdUnm() {
		return odUnm;
	}
	public void setOdUnm(String odUnm) {
		this.odUnm = odUnm;
	}
	public String getOdUphone() {
		return odUphone;
	}
	public void setOdUphone(String odUphone) {
		this.odUphone = odUphone;
	}
	public Long getOdBarberUid() {
		return odBarberUid;
	}
	public void setOdBarberUid(Long odBarberUid) {
		this.odBarberUid = odBarberUid;
	}
	public String getOdBarberUnm() {
		return odBarberUnm;
	}
	public void setOdBarberUnm(String odBarberUnm) {
		this.odBarberUnm = odBarberUnm;
	}
	public Long getOdStm() {
		return odStm;
	}
	public void setOdStm(Long odStm) {
		this.odStm = odStm;
	}
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
	public Double getOdTotalPrice() {
		return odTotalPrice;
	}
	public void setOdTotalPrice(Double odTotalPrice) {
		this.odTotalPrice = odTotalPrice;
	}
	public Double getOdDiscount() {
		return odDiscount;
	}
	public void setOdDiscount(Double odDiscount) {
		this.odDiscount = odDiscount;
	}
	public Double getOdOfferPrice() {
		return odOfferPrice;
	}
	public void setOdOfferPrice(Double odOfferPrice) {
		this.odOfferPrice = odOfferPrice;
	}
	public Double getOdVoucherPrice() {
		return odVoucherPrice;
	}
	public void setOdVoucherPrice(Double odVoucherPrice) {
		this.odVoucherPrice = odVoucherPrice;
	}
	public Double getOdPayPrice() {
		return odPayPrice;
	}
	public void setOdPayPrice(Double odPayPrice) {
		this.odPayPrice = odPayPrice;
	}
	public Integer getOdPaied() {
		return odPaied;
	}
	public void setOdPaied(Integer odPaied) {
		this.odPaied = odPaied;
	}
	public Long getOdPaiedTs() {
		return odPaiedTs;
	}
	public void setOdPaiedTs(Long odPaiedTs) {
		this.odPaiedTs = odPaiedTs;
	}
	public String getOdPaiedTp() {
		return odPaiedTp;
	}
	public void setOdPaiedTp(String odPaiedTp) {
		this.odPaiedTp = odPaiedTp;
	}
	public Integer getOdEva() {
		return odEva;
	}
	public void setOdEva(Integer odEva) {
		this.odEva = odEva;
	}
	public String getOdEvaDesc() {
		return odEvaDesc;
	}
	public void setOdEvaDesc(String odEvaDesc) {
		this.odEvaDesc = odEvaDesc;
	}
	public Integer getOdConfirm() {
		return odConfirm;
	}
	public void setOdConfirm(Integer odConfirm) {
		this.odConfirm = odConfirm;
	}
	
}
