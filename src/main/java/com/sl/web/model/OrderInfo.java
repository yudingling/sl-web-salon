package com.sl.web.model;

import java.io.Serializable;
import java.util.List;

public class OrderInfo implements Serializable {
	private static final long serialVersionUID = 180540958951435782L;
	
	private Long odId;
	private Long rvId;
	private Long shopId;
	private Long odUid;
	private Long odBarberUid;
	private Long pjId;
	private Long odStm;
	private Long odEtm;
	private Double odPjPrice;
	private Double odPdPrice;
	private Double odTotalPrice;
	private Double odDiscount;
	private Double odOfferPrice;
	private Double odVoucherPrice;
	private Double odPayPrice;
	private Integer odPaied;
	private Long odPaiedTs;
	private String odPaiedTp;
	private Integer odComplaint;
	private Integer odConfirm;
	private Long crtTs;
	private Long uptTs;
	
	private List<OrderProductInfo> products;
	
	private String odUnm;
	private String odUphone;
	private String odBarberUnm;
	private String pjNm;
	
	public Long getOdId() {
		return odId;
	}
	public void setOdId(Long odId) {
		this.odId = odId;
	}
	public Long getRvId() {
		return rvId;
	}
	public void setRvId(Long rvId) {
		this.rvId = rvId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getOdUid() {
		return odUid;
	}
	public void setOdUid(Long odUid) {
		this.odUid = odUid;
	}
	public Long getOdBarberUid() {
		return odBarberUid;
	}
	public void setOdBarberUid(Long odBarberUid) {
		this.odBarberUid = odBarberUid;
	}
	public Long getPjId() {
		return pjId;
	}
	public void setPjId(Long pjId) {
		this.pjId = pjId;
	}
	public Long getOdStm() {
		return odStm;
	}
	public void setOdStm(Long odStm) {
		this.odStm = odStm;
	}
	public Long getOdEtm() {
		return odEtm;
	}
	public void setOdEtm(Long odEtm) {
		this.odEtm = odEtm;
	}
	public Double getOdPjPrice() {
		return odPjPrice;
	}
	public void setOdPjPrice(Double odPjPrice) {
		this.odPjPrice = odPjPrice;
	}
	public Double getOdPdPrice() {
		return odPdPrice;
	}
	public void setOdPdPrice(Double odPdPrice) {
		this.odPdPrice = odPdPrice;
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
	public Integer getOdComplaint() {
		return odComplaint;
	}
	public void setOdComplaint(Integer odComplaint) {
		this.odComplaint = odComplaint;
	}
	public Integer getOdConfirm() {
		return odConfirm;
	}
	public void setOdConfirm(Integer odConfirm) {
		this.odConfirm = odConfirm;
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
	public List<OrderProductInfo> getProducts() {
		return products;
	}
	public void setProducts(List<OrderProductInfo> products) {
		this.products = products;
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
	public String getOdBarberUnm() {
		return odBarberUnm;
	}
	public void setOdBarberUnm(String odBarberUnm) {
		this.odBarberUnm = odBarberUnm;
	}
	public String getPjNm() {
		return pjNm;
	}
	public void setPjNm(String pjNm) {
		this.pjNm = pjNm;
	}
}
