package com.sl.web.model;

import java.io.Serializable;
import com.sl.web.util.Common;
import com.sl.web.util.DateUtil;

public class ShopInfo implements Serializable {
	private static final long serialVersionUID = -2761746572241916571L;
	
	private Long shopId;
	private String shopNm;
	private String bdId;
	private Integer shopEnable;
	private Double shopLgtd;
	private Double shopLttd;
	private String shopLocation;
	private String shopPhone;
	private String shopWechatpayId;
	private String shopStm;
	private String shopEtm;
	private Long crtTs;
	private Long uptTs;
	
	private String spsStm;
	private String spsEtm;
	
	private String shopUserPhone;
	private String shopUserPwd;
	
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
	public String getShopUserPhone() {
		return shopUserPhone;
	}
	public void setShopUserPhone(String shopUserPhone) {
		this.shopUserPhone = shopUserPhone;
	}
	public String getShopUserPwd() {
		return shopUserPwd;
	}
	public void setShopUserPwd(String shopUserPwd) {
		this.shopUserPwd = shopUserPwd;
	}
	public Integer getShopEnable() {
		return shopEnable;
	}
	public void setShopEnable(Integer shopEnable) {
		this.shopEnable = shopEnable;
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
	
	public ShopInfo(){
		super();
	}
	
	public void setServiceTime(Long spsStm, Long spsEtm){
		this.spsStm = DateUtil.toString(spsStm, "yyyy-MM-dd");
		this.spsEtm = DateUtil.toString(spsEtm, "yyyy-MM-dd");
	}
}
