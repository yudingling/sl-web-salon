package com.sl.web.model;

import java.io.Serializable;

import com.sl.web.model.db.SlShop;
import com.sl.web.util.Common;
import com.sl.web.util.DateUtil;

public class ShopInfo implements Serializable {
	private static final long serialVersionUID = -2761746572241916571L;
	
	private Long shopId;
	private String shopNm;
	private String bdId;
	private Double shopLgtd;
	private Double shopLttd;
	private String shopLocation;
	private String shopPhone;
	private String shopWechatpayId;
	private String shopStm;
	private String shopEtm;
	
	private String spsStm;
	private String spsEtm;
	
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
		this.shopNm = Common.valString(shopNm);
	}
	public String getBdId() {
		return bdId;
	}
	public void setBdId(String bdId) {
		this.bdId = Common.valString(bdId);
	}
	public Double getShopLgtd() {
		return shopLgtd;
	}
	public void setShopLgtd(Double shopLgtd) {
		this.shopLgtd = shopLgtd;
	}
	public Double getShopLttd() {
		return shopLttd;
	}
	public void setShopLttd(Double shopLttd) {
		this.shopLttd = shopLttd;
	}
	public String getShopLocation() {
		return shopLocation;
	}
	public void setShopLocation(String shopLocation) {
		this.shopLocation = Common.valString(shopLocation);
	}
	public String getShopPhone() {
		return shopPhone;
	}
	public void setShopPhone(String shopPhone) {
		this.shopPhone = Common.valString(shopPhone);
	}
	public String getShopWechatpayId() {
		return shopWechatpayId;
	}
	public void setShopWechatpayId(String shopWechatpayId) {
		this.shopWechatpayId = Common.valString(shopWechatpayId);
	}
	public String getShopStm() {
		return shopStm;
	}
	public void setShopStm(String shopStm) {
		this.shopStm = Common.valString(shopStm);
	}
	public String getShopEtm() {
		return shopEtm;
	}
	public void setShopEtm(String shopEtm) {
		this.shopEtm = Common.valString(shopEtm);
	}
	public String getSpsStm() {
		return spsStm;
	}
	public void setSpsStm(String spsStm) {
		this.spsStm = Common.valString(spsStm);
	}
	public String getSpsEtm() {
		return spsEtm;
	}
	public void setSpsEtm(String spsEtm) {
		this.spsEtm = Common.valString(spsEtm);
	}
	
	public ShopInfo(){
		super();
	}
	
	public ShopInfo(SlShop shop){
		super();
		
		this.shopId = shop.getShopId();
		this.shopNm = shop.getShopNm();
		this.bdId = shop.getBdId();
		this.shopLgtd = shop.getShopLgtd();
		this.shopLttd = shop.getShopLttd();
		this.shopLocation = shop.getShopLocation();
		this.shopPhone = shop.getShopPhone();
		this.shopWechatpayId = shop.getShopWechatpayId();
		this.shopStm = shop.getShopStm();
		this.shopEtm = shop.getShopEtm();
	}
	
	public void setServiceTime(Long spsStm, Long spsEtm){
		this.spsStm = DateUtil.toString(spsStm, "yyyy-MM-dd");
		this.spsEtm = DateUtil.toString(spsEtm, "yyyy-MM-dd");
	}
}
